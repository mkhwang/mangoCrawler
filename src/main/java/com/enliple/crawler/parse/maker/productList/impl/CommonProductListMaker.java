package com.enliple.crawler.parse.maker.productList.impl;

import com.enliple.crawler.common.util.JSoupUtil;
import com.enliple.crawler.parse.maker.productList.ProductListMaker;
import org.apache.log4j.Logger;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public class CommonProductListMaker implements ProductListMaker{
    private Logger logger = Logger.getLogger(CommonProductListMaker.class);

    @Override
    public List<Object> getProductList(Object pageData, String productListPattern) throws NullPointerException {
        List<Object> productList;
        Elements tempProductList = JSoupUtil.getElements(pageData, productListPattern);
        if(tempProductList.size() > 0){
            productList = new ArrayList<>();
            productList.addAll(tempProductList);

            /*
            for(Element product : tempProductList)
                productList.add(product);
            */

        } else {
           throw new NullPointerException();
        }
        return productList;
    }
}
