package com.enliple.crawler.parse.maker.productList.impl;

import com.enliple.crawler.parse.maker.productList.ProductListMaker;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public class Cafe24ProductListMaker implements ProductListMaker{
    private Logger logger = Logger.getLogger(Cafe24ProductListMaker.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> getProductList(Object pageData, String productListPattern) throws NullPointerException{
        List<Object> productList;
        JSONObject page = (JSONObject) pageData;
        page = (JSONObject) page.get("rtn_data");
        if(page.get("data") != null)
            productList = (JSONArray)page.get("data");
        else {
            throw new NullPointerException();
        }
        return productList;
    }
}
