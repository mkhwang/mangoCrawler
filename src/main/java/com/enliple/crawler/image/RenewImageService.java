package com.enliple.crawler.image;

import com.enliple.crawler.common.util.LoadProperties;
import com.enliple.crawler.common.util.StringUtil;
import com.enliple.crawler.image.crop.ImageCropService;
import com.enliple.crawler.image.crop.impl.ImageCropLeftServiceImpl;
import com.enliple.crawler.image.crop.impl.ImageCropMidServiceImpl;
import com.enliple.crawler.image.crop.impl.ImageCropRightServiceImpl;
import com.enliple.crawler.image.customizeImage.Image;
import com.enliple.crawler.image.customizeImage.ImageLoader;
import com.enliple.crawler.parse.domain.Product;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by MinKi Hwang on 2017-08-08.
 */
public class RenewImageService {
    private Logger logger = Logger.getLogger(RenewImageService.class);

    /**
     * saveMode : big / small
     * transformType : 1 left / 2 mid / 3 right
     * address : image location
     * path : shop ENG name
     * @param product
     * @param saveMode
     * @param transformType
     * @param path
     * @param address
     */
    public void saveImage(Product product, String saveMode, String transformType, String path, String address) {
        Image resultImage = this.transformImage(saveMode, transformType, address);
        File resultFile = this.getFile(product, saveMode, path, address);

        try {
            resultImage.soften(0.0f).writeToJPG(resultFile, 1.00f);
            resultImage.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!"big".equals(saveMode)){
            product.setWidth(resultImage.getWidth());
            product.setHeight(resultImage.getHeight());
            //System.out.println("[2] : "+String.valueOf(resultImage.getWidth())+ ", " +String.valueOf(resultImage.getHeight()));
        }
    }

    private Image transformImage(String saveMode, String type, String address) {
        Image resultImage = this.getImage(address);
        ImageCropService imageCropService = null;
        if(!"big".equals(saveMode)){
            if ("1" == type) {
                imageCropService = new ImageCropLeftServiceImpl();
            } else if ("2" == type) {
                imageCropService = new ImageCropMidServiceImpl();
            } else if ("3" == type) {
                imageCropService = new ImageCropRightServiceImpl();
            }
            try {
                resultImage = imageCropService.crop(resultImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            resultImage = this.getResizeImage(resultImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultImage;
    }

    private Image getImage(String address) {
        Image tempImage = null;
        try {
            if (address.startsWith("http")) {
                address = address.replace(" ", "%20");
                tempImage = ImageLoader.fromUrl(address);
            } else {
                File targetFile = new File(address);
                if (!targetFile.exists() || !targetFile.isFile()) {
                    throw new IllegalArgumentException("Invalid path to image");
                } else {
                    tempImage = ImageLoader.fromFile(targetFile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return tempImage;
    }

    private Image getResizeImage(Image targetImage){
        Image resizedImage = null;

        int width = targetImage.getWidth();
        int height = targetImage.getHeight();
        int minSize = 0;

        if(width < height){
            minSize = width;
        }else{
            minSize = height;
        }

        if (minSize > 720){
            float scale = 720f / (float) (width > height ? width : height);
            width = (int) (width * scale);
            height = (int) (height * scale);
            resizedImage = targetImage.getResize(width, height);
            int big = resizedImage.getWidth() > resizedImage.getHeight() ? resizedImage.getWidth() : resizedImage.getHeight();
            if (big > 720 * 3){
                resizedImage = null;
            }
        } else {
            resizedImage = targetImage.getResize(targetImage.getWidth(), targetImage.getHeight());

            int big = 0;
            int small = 0;

            if(resizedImage.getWidth() > resizedImage.getHeight()){
                big = resizedImage.getWidth();
                small = resizedImage.getHeight();
            }else{
                big = resizedImage.getHeight();
                small = resizedImage.getWidth();
            }

            if (big > small * 3 ){
                resizedImage = null;
            }
        }
        return resizedImage;
    }

    private File getFile(Product product, String saveMode, String path, String address){
        String name = "";
        File resultFile = null;

        if(address.contains("/EL.JPG") || address.contains("/SO.JPG")){
            name = address.substring(address.indexOf("goods/")+6, address.lastIndexOf("/")) + ".jpg";
        }else{
            name = address.substring(address.lastIndexOf("/") + 1, address.length()); // 20160215_225924_h.jpg
        }

        if(name.contains("?")){
            name = name.substring(0, name.lastIndexOf("?"));
        }

        if (StringUtil.isHaveHangul(name)) {
            try {
                address = address.replace(name, URLEncoder.encode(name, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String path1 = "";
        if (!name.substring(1, 2).equals("."))
            path1 = "/" + name.substring(0, 1) + "/" + name.substring(1, 2) + "/" + name.substring(2, 3) + "/";
        else
            path1 = "/" + name.substring(0, 1) + "/";

        // imgfile
        String bigPath = LoadProperties.getImageSaveDir()+File.separator+"big"+File.separator;
        String smallPath = LoadProperties.getImageSaveDir()+File.separator+"small"+File.separator;

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
        if (name.contains(".")){
            file_ext = name.substring(name.lastIndexOf(".") + 1, name.length());// jpg
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

        if(name.contains(".gif")||name.contains(".png")||name.contains(".bmp")){
            name = name.replaceAll(".gif", ".jpg").replaceAll(".png", ".jpg").replaceAll(".bmp", ".jpg");
        }

        if ((product.getImage1().contains("dsalon1.diskn.com") || StringUtil.isNullEmpty(file_ext)) && !name.contains(".jpg")){
            name = name + ".jpg";
        }

        if("big".equals(saveMode)){
            product.setImage1(ip + path + path1 + name);
            resultFile = new File(bigPath + path + path1 + name);
        } else if("small".equals(saveMode)){
            resultFile = new File(smallPath + path + path1 + name);
        }
        return resultFile;
    }
}
