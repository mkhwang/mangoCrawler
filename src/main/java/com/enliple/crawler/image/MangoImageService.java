package com.enliple.crawler.image;

import com.enliple.crawler.common.util.LoadProperties;
import com.enliple.crawler.common.util.StringUtil;
import com.enliple.crawler.image.crop.ImageCropService;
import com.enliple.crawler.image.customizeImage.Image;
import com.enliple.crawler.image.customizeImage.ImageLoader;
import com.enliple.crawler.parse.domain.ParsingInfo;
import com.enliple.crawler.parse.domain.Product;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by MinKi Hwang on 2017-08-04.
 */
public class MangoImageService {
    private RenewImageService renewImageService;

    public void setImageInformation(Product product, ParsingInfo parsingInfo) {
        if(LoadProperties.getOriginalImageSave())
            product.setImage2(product.getImage1());

        if ("".equals(parsingInfo.getImageDir()) || parsingInfo.getImageDir() == null){
            parsingInfo.setImageDir();
        }

        try {
            if ("0".equals(parsingInfo.getImageTransform()))
                this.imageTransform(product, parsingInfo);
            else if ("1".equals(parsingInfo.getImageTransform()))
                this.setImageInformationFromUrl(product);
            else if("2".equals(parsingInfo.getImageTransform())){
                renewImageService = new RenewImageService();
                renewImageService.saveImage(product, "big", "2", parsingInfo.getImageDir(), product.getImage1());
                //renewImageService.saveImage(product, "small", "2", parsingInfo.getImageDir(), product.getImage1());
            } else {
                product.setWidth(parsingInfo.getImageWidth());
                product.setHeight(parsingInfo.getImageHeight());
            }
        } catch (Exception e) {
            product.setWidth(parsingInfo.getImageWidth());
            product.setHeight(parsingInfo.getImageHeight());
        }
    }

    private void imageTransform(Product product, ParsingInfo parsingInfo) throws Exception {
        String name = "";
        String imgURL = product.getImage1();
        String path = parsingInfo.getImageDir();
        int type = 1;

        if (imgURL.contains("/EL.JPG") || imgURL.contains("/SO.JPG")) {
            name = imgURL.substring(imgURL.indexOf("goods/") + 6, imgURL.lastIndexOf("/")) + ".jpg";
        } else if (path.contains("biny")) {
            name = imgURL.substring(imgURL.lastIndexOf("/") - 8, imgURL.length()).replaceAll("/", "");
        } else if (path.contains("staytender") || path.contains("time-too")) {
            name = imgURL.substring(imgURL.lastIndexOf("/") - 5, imgURL.length()).replaceAll("/", "");
        } else {
            name = imgURL.substring(imgURL.lastIndexOf("/") + 1, imgURL.length()); // 20160215_225924_h.jpg
        }

        if (name.contains("?")) {
            name = name.substring(0, name.lastIndexOf("?"));
        }

        if (StringUtil.isHaveHangul(name)) {
            imgURL = imgURL.replace(name, URLEncoder.encode(name, "UTF-8"));
        }

        String path1 = "";
        if (!name.substring(1, 2).equals(".")) {
            path1 = "/" + name.substring(0, 1) + "/" + name.substring(1, 2) + "/";
        } else {
            path1 = "/" + name.substring(0, 1) + "/";

        }

        String bigPath = LoadProperties.getImageSaveDir() + File.separator + "big" + File.separator;
        String smallPath = LoadProperties.getImageSaveDir() + File.separator + "small" + File.separator;

        // 이미지 명이 동일하여 덮어씌어지는 경우가 있으므로, 고유코드값을 디렉토리로 만들어 구분.
        if (path.contains("lavisione")
                || path.contains("styleberry")
                || path.contains("build")
                || path.contains("shoesmong")) {
            if (!name.substring(1, 2).equals("."))
                path1 = "/" + product.getpCode() + "/" + name.substring(0, 1) + "/" + name.substring(1, 2) + "/";
            else
                path1 = "/" + product.getpCode() + "/" + name.substring(0, 1) + "/";
        }
        String ip = "http://img.mango6.co.kr/imgfile/small/";
        String file_ext = "";
        if (name.contains(".")) {
            file_ext = name.substring(name.lastIndexOf(".") + 1, name.length());// jpg
        }

        try {
            Image img = null;
            if (imgURL.startsWith("http")) {
                imgURL = imgURL.replaceAll(" ", "%20");
                img = ImageLoader.fromUrl(new URL(imgURL));
            } else {
                File f = new File(imgURL);
                if (!f.exists() || !f.isFile())
                    throw new IllegalArgumentException("Invalid path to image");
                else {
                    img = ImageLoader.fromFile(f);
                }
            }

            /** 빅 이미지 1080 x 1080 **/
            File dirBig = new File(bigPath + path + path1);
            if (!dirBig.exists()) { // 폴더 없으면 폴더 생성
                dirBig.mkdirs();
            }

            /** 스몰 이미지 360 x 360 **/
            File dirSmall = new File(smallPath + path + path1);
            if (!dirSmall.exists()) { // 폴더 없으면 폴더 생성
                dirSmall.mkdirs();
            }

            if (name.contains(".gif") || name.contains(".png") || name.contains(".bmp")) {
                name = name.replaceAll(".gif", ".jpg").replaceAll(".png", ".jpg").replaceAll(".bmp", ".jpg");
            }

            if ((imgURL.contains("dsalon1.diskn.com") || StringUtil.isNullEmpty(file_ext)) && !name.contains(".jpg")) {
                name = name + ".jpg";
            }

            dirBig = new File(bigPath + path + path1 + name);
            dirSmall = new File(smallPath + path + path1 + name);

            int width = img.getWidth();
            int height = img.getHeight();

            int minSize = 0;

            if (width < height) {
                minSize = width;
            } else {
                minSize = height;
            }

            Image resizedImage = null;

            if (minSize > 720) {

                float scale = 720f / (float) (width > height ? width : height);

                width = (int) (width * scale);
                height = (int) (height * scale);
                resizedImage = img.getResize(width, height);

                int big = resizedImage.getWidth() > resizedImage.getHeight() ? resizedImage.getWidth() : resizedImage.getHeight();

                if (big > 720 * 3) {
                    product.setDisplay("0");
                }
            } else {
                resizedImage = img.getResize(img.getWidth(), img.getHeight());

                int big = 0;
                int small = 0;

                if (resizedImage.getWidth() > resizedImage.getHeight()) {
                    big = resizedImage.getWidth();
                    small = resizedImage.getHeight();
                } else {
                    big = resizedImage.getHeight();
                    small = resizedImage.getWidth();
                }

                if (big > small * 3 && !imgURL.contains("dresden.")) {
                    product.setDisplay("0");
                }
            }

            if (type == 1) {
                product.setWidth(resizedImage.getWidth());
                product.setHeight(resizedImage.getHeight());
            }

            Image cropped = null;
            if (minSize < 240) {
                cropped = img.crop(minSize, minSize);
            } else {
                cropped = img.getCropResize(minSize, minSize);
            }

            if (type == 1) {
                downloadImg(dirBig, dirSmall, resizedImage, cropped);
                product.setImage1(ip + path + path1 + name);
                System.out.println(product.getImage1());
            }

            /*
            if (type == 2 && !product.getImage1().equals(ip + path + path1 + name)){
                downloadImg(dirBig, dirSmall, resizedImage, cropped);
                product.setImage2(ip + path + path1 + name);
            }
            else product.setImage2("");

            if (type == 3 && !product.getImage1().equals(ip + path + path1 + name)){
                downloadImg(dirBig, dirSmall, resizedImage, cropped);
                product.setImage3(ip + path + path1 + name);
            }
            else product.setImage4("");

            if (type == 4 && !product.getImage1().equals(ip + path + path1 + name)){
                downloadImg(dirBig, dirSmall, resizedImage, cropped);
                product.setImage4(ip + path + path1 + name);
            }
            else product.setImage4("");

            if (type == 5 && !product.getImage1().equals(ip + path + path1 + name)){
                downloadImg(dirBig, dirSmall, resizedImage, cropped);
                product.setImage5(ip + path + path1 + name);
            }
            else product.setImage6("");

            if (type == 6 && !product.getImage1().equals(ip + path + path1 + name)){
                downloadImg(dirBig, dirSmall, resizedImage, cropped);
                product.setImage6(ip + path + path1 + name);
            }
            else product.setImage6("");
            */
        } catch (Exception e) {
            product.setDisplay("0");
            product.setWidth(0);
            product.setHeight(0);
            e.printStackTrace();
        }
    }

    private void setImageInformationFromUrl(Product product) throws Exception {
        try {
            BufferedImage image = ImageIO.read(new URL(product.getImage1()));
            product.setWidth(image.getWidth());
            product.setHeight(image.getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadImg(File dirBig, File dirSmall, Image resized, Image cropped) throws IOException {
        resized.soften(0.0f).writeToJPG(dirBig, 1.00f);
        resized.dispose();
        cropped.soften(0.0f).writeToJPG(dirSmall, 1.00f);
        cropped.dispose();
    }
}
