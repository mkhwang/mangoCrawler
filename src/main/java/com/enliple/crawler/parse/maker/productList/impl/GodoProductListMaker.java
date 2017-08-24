package com.enliple.crawler.parse.maker.productList.impl;

import com.enliple.crawler.parse.domain.godo.GodoGSON;
import com.enliple.crawler.parse.maker.productList.ProductListMaker;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-24.
 */
public class GodoProductListMaker implements ProductListMaker {
    private Logger logger = Logger.getLogger(GodoProductListMaker.class);

    @Override
    public List<Object> getProductList(Object pageData, String productListPattern) throws NullPointerException {
        List<Object> productList = new ArrayList<>();
        String encodedData;
        Gson gson = new Gson();
        try {
            encodedData = (String)pageData;
            GodoGSON godoGSON = gson.fromJson(encodedData, GodoGSON.class);
            productList.addAll(godoGSON.getGoods_data());
        } catch (NullPointerException e){

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(productList.size() <= 0)
            throw new NullPointerException();

        return productList;
    }
}
