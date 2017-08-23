package com.enliple.crawler.parse.maker.productList.impl;

import com.enliple.crawler.parse.domain.cafe24.Cafe24GSON;
import com.enliple.crawler.parse.maker.productList.ProductListMaker;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public class Cafe24ProductListMaker implements ProductListMaker{
    private Logger logger = Logger.getLogger(Cafe24ProductListMaker.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> getProductList(Object pageData, String productListPattern) throws NullPointerException{
        List<Object> productList = new ArrayList<>();;
        /*
        JSONParser jsonParser = new JSONParser();
        JSONObject page = new JSONObject();
        try {
            page = (JSONObject) jsonParser.parse((String) pageData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        page = (JSONObject) page.get("rtn_data");
        if(page.get("data") != null)
            productList = (JSONArray)page.get("data");
        else {
            throw new NullPointerException();
        }
        return productList;
        */
        Gson gson = new Gson();
        try {
            Cafe24GSON cafe24GSON = gson.fromJson((String)pageData, Cafe24GSON.class);
            productList.addAll(cafe24GSON.getData());
        } catch (NullPointerException e){

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(productList.size() <= 0)
            throw new NullPointerException();

        return productList;
    }
}
