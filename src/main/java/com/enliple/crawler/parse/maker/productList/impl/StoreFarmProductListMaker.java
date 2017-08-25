package com.enliple.crawler.parse.maker.productList.impl;

import com.enliple.crawler.common.util.JSoupUtil;
import com.enliple.crawler.parse.maker.productList.ProductListMaker;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-25.
 */
public class StoreFarmProductListMaker implements ProductListMaker {
    private Logger logger = Logger.getLogger(StoreFarmProductListMaker.class);

    @Override
    public List<Object> getProductList(Object pageData, String productListPattern) throws NullPointerException {
        JSONParser jsonParser = new JSONParser();
        JSONObject page = null;
        try {
            page = (JSONObject) jsonParser.parse((String)pageData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        page = (JSONObject) page.get("htReturnValue");
        String productString = page.get("html").toString();

        if(productString == null || "".equals(productString))
            throw  new NullPointerException();

        Document productListHtml= Jsoup.parse(productString);
        Elements tempProductList = JSoupUtil.getElements(productListHtml, productListPattern);

        if(tempProductList.size() <= 0)
            throw new NullPointerException();

        List<Object> productList = new ArrayList<>();
        productList.addAll(tempProductList);

        return productList;
    }
}