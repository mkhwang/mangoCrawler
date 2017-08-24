package com.enliple.crawler.parse.domain.cafe24;

import com.enliple.crawler.parse.connect.PageConnection;
import com.enliple.crawler.parse.connect.impl.UrlStreamConnection;
import com.enliple.crawler.parse.domain.ParsePattern;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-24.
 */
public class TestCafe24GSON {

    @Test
    public void testCate24GSONImplementation(){
        List<Object> productList = new ArrayList<>();
        Gson gson = new Gson();
        PageConnection pageConnection;
        try {
            pageConnection = new UrlStreamConnection();
            ParsePattern parsePattern = new ParsePattern();
            parsePattern.setShopType("cafe24");
            String url ="http://m.66girls.co.kr/exec/front/Product/ApiProductNormal?count=1000&cate_no=70";
            Object pageData = pageConnection.getPageData(url);
            String encodedData = (String)pageData;
            Cafe24GSON cafe24GSON = gson.fromJson(encodedData, Cafe24GSON.class);
            productList.addAll(cafe24GSON.getData());
        } catch (NullPointerException e){

        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Object object : productList){
            Cafe24GSONProduct product = (Cafe24GSONProduct) object;
            System.out.println(product.getProduct_name());
        }

        Assert.assertTrue(!productList.isEmpty());
    }
}
