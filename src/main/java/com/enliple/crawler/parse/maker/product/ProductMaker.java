package com.enliple.crawler.parse.maker.product;

import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.Product;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public interface ProductMaker {
    Product getProduct(Object data, ParsePattern parsePattern);
}
