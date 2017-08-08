package com.enliple.crawler.image.crop.impl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.enliple.crawler.image.crop.ImageCropService;
import com.enliple.crawler.image.customizeImage.Image;
import com.enliple.crawler.image.customizeImage.ImageType;

public class ImageCropMidServiceImpl implements ImageCropService {
    @Override
    public Image crop(Image targetImage) throws Exception {
    	double widthRate = 1.0;
        double heightRate = 1.65;

        double cropImageHeight = targetImage.getHeight();
        double cropImageWidth = Math.ceil(cropImageHeight*widthRate/heightRate);
        

        BufferedImage croppedBufferImage = new BufferedImage((int)cropImageWidth, (int)cropImageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = croppedBufferImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setComposite(AlphaComposite.Src);
        
        /**
         * 1:1.55 비율로 가운데 기준으로 자름. 배경은 흰색으로 고정
         */
        g.drawImage(targetImage.getBufferedImage(), 0, 0, croppedBufferImage.getWidth(), croppedBufferImage.getHeight(),
        		(int)Math.ceil(targetImage.getWidth()/2 - cropImageWidth/2), 0, 
        		(int)Math.ceil(targetImage.getWidth()/2 + cropImageWidth/2), (int)cropImageHeight, Color.white, null);
        g.dispose();
        //System.out.println("[1] : " + croppedBufferImage.getWidth() + ", " + croppedBufferImage.getHeight());
        return new Image(croppedBufferImage, ImageType.JPG);
    }
}
