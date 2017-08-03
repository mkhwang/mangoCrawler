package com.enliple.crawler.parse.maker.product.impl;

import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.Product;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import com.enliple.crawler.parse.maker.productList.impl.Cafe24ProductListMaker;
import org.apache.log4j.Logger;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public class Cafe24ProductMaker implements ProductMaker{
    private Logger logger = Logger.getLogger(Cafe24ProductListMaker.class);

    @Override
    public Product getProduct(Object data, ParsePattern parsePattern) {
        return null;
    }
}
