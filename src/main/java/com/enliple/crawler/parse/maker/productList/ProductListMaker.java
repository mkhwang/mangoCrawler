package com.enliple.crawler.parse.maker.productList;

import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public interface ProductListMaker {
    List<Object> getProductList(Object pageData, String productListPattern) throws NullPointerException;
}
