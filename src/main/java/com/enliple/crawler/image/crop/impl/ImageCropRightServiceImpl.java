package com.enliple.crawler.image.crop.impl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.enliple.crawler.image.crop.ImageCropService;
import com.enliple.crawler.image.customizeImage.Image;
import com.enliple.crawler.image.customizeImage.ImageType;

public class ImageCropRightServiceImpl implements ImageCropService {

	@Override
	public Image crop(Image targetImage) throws Exception {
        int cropImageWidth = targetImage.getWidth()/2;
        int cropImageHeight = targetImage.getHeight();

        BufferedImage croppedBufferImage = new BufferedImage(cropImageWidth, cropImageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = croppedBufferImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setComposite(AlphaComposite.Src);

        /**
         * 원본 이미지 세로 유지, 좌측 기준 가로 50% ~ 100% 지점을 자른다
         */
        g.drawImage(targetImage.getBufferedImage(), 0, 0, croppedBufferImage.getWidth(), croppedBufferImage.getHeight(),
                cropImageWidth, 0, targetImage.getWidth(), cropImageHeight, Color.white,null);
        g.dispose();

        return new Image(croppedBufferImage, ImageType.JPG);		
	}

}
