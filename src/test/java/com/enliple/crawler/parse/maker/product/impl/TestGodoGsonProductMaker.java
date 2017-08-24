package com.enliple.crawler.parse.maker.product.impl;

import com.enliple.crawler.parse.connect.PageConnection;
import com.enliple.crawler.parse.connect.impl.UrlStreamConnection;
import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.domain.godo.GodoGSON;
import com.enliple.crawler.parse.maker.product.ProductMaker;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-24.
 */
public class TestGodoGsonProductMaker {
    @Test
    public void testGodoGsonProductMakerImplementation(){
        ProductMaker maker = new GodoGsonProductMaker();
        List<Object> productList = new ArrayList<>();
        Gson gson = new Gson();
        PageConnection pageConnection;
        try {
            pageConnection = new UrlStreamConnection();
            ParsePattern parsePattern = new ParsePattern();
            parsePattern.setShopType("godo");
            String url ="http://www.yuhaksang.com/m2/proc/mAjaxAction.php?mode=get_goods&view_type=list&category=015001&item_cnt=0";
            Object pageData = pageConnection.getPageData(url);
            String encodedData = (String)pageData;
            GodoGSON godoGSON = gson.fromJson(encodedData, GodoGSON.class);
            productList.addAll(godoGSON.getGoods_data());
            for(Object object : productList){
                System.out.println(maker.getProduct(object, parsePattern).toString());
            }
        } catch (NullPointerException e){

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
