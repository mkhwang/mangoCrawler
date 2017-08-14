package com.enliple.crawler.parse.maker.productList.impl;

import com.enliple.crawler.common.util.JSoupUtil;
import com.enliple.crawler.parse.maker.productList.ProductListMaker;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-10.
 */
public class ElementsFromJSONProductListMaker implements ProductListMaker {
    @Override
    public List<Object> getProductList(Object pageData, String productListPattern) throws NullPointerException {
        JSONObject jsonPage = (JSONObject) pageData;
        String elementsFromJSONPattern = productListPattern.split("\\$")[0];
        String[] jsonPatterns = elementsFromJSONPattern.split("\\^");
        String productListFromElementsPattern = productListPattern.split("\\$")[1];
        Document tempDocument = null;
        int patternSize = jsonPatterns.length;

        for(int i=0; i<patternSize ; i++){
            if( patternSize == (i+1) ){
                tempDocument = Jsoup.parse(jsonPage.get(jsonPatterns[i]).toString());
            }else{
                jsonPage = (JSONObject) jsonPage.get(jsonPatterns[i]);
            }
        }

        Elements tempProductList = JSoupUtil.getElements(tempDocument, productListFromElementsPattern);

        if(tempProductList.size() <= 0)
            throw new NullPointerException();

        List<Object> productList = new ArrayList<>();
        productList.addAll(tempProductList);
        /*
        for(Element tempProduct : tempProductList){
            productList.add(tempProduct);
        }
         */
        return productList;
    }
}
