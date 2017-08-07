package com.enliple.crawler.image;

import com.enliple.crawler.parse.domain.Product;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by MinKi Hwang on 2017-08-04.
 */
public class MangoImageService {
    public void imageTransform(Product product) throws Exception{
        /**
         * Todo transform Image
         */
    }

    public void setImageInformationFromUrl(Product product) throws Exception{
        try {
            BufferedImage image = ImageIO.read(new URL(product.getImage1()));
            product.setWidth(image.getWidth());
            product.setHeight(image.getHeight());
        } catch (IOException e) {
            throw new Exception();
        }
    }
}
