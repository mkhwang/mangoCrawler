package com.enliple.crawler.parse.maker.productList.impl;

import com.enliple.crawler.common.util.JsoupUtil;
import com.enliple.crawler.parse.maker.productList.ProductListMaker;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public class MakeShopProductListMaker implements ProductListMaker{

    @Override
    public List<Object> getProductList(Object pageData, String productListPattern) throws NullPointerException {
        JSONObject page = (JSONObject) pageData;
        String productString = page.get("html").toString();
        Document productListHtml= Jsoup.parse(productString);
        Elements tempProductList = JsoupUtil.getElements(productListHtml, productListPattern);
        if(tempProductList.size() <= 0)
            throw new NullPointerException();

        List<Object> productList = new ArrayList<>();
        for(Element tempProduct : tempProductList){
            productList.add(tempProduct);
        }

        return productList;
    }
}
