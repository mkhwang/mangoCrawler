package com.enliple.crawler.image.crop;

import com.enliple.crawler.image.customizeImage.Image;

/**
 * Created by MinKi Hwang on 2017-08-07.
 */
public interface ImageCropService {
    Image crop(Image targetImage) throws Exception;
}
