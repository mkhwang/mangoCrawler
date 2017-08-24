package com.enliple.crawler.parse.domain.godo;

import com.enliple.crawler.parse.connect.PageConnection;
import com.enliple.crawler.parse.connect.impl.UrlStreamPostConnection;
import com.enliple.crawler.parse.domain.ParsePattern;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-24.
 */
public class TestGodoGSON {

    @Test
    public void TestGodoGSONImplementation(){
        List<Object> productList = new ArrayList<>();
        Gson gson = new Gson();
        PageConnection pageConnection;
        try {
            pageConnection = new UrlStreamPostConnection();
            ParsePattern parsePattern = new ParsePattern();
            parsePattern.setShopType("godo");
            //String url ="http://banhala.com/m2/proc/mAjaxAction.php?mode=get_goods&view_type=list&category=010&item_cnt=0&sort_type=regdt";
            String url ="http://www.yuhaksang.com/m2/proc/mAjaxAction.php?mode=get_goods&view_type=list&category=015001&item_cnt=0";
            Object pageData = pageConnection.getPageData(url);
            String encodedData = (String)pageData;
            GodoGSON godoGSON = gson.fromJson(encodedData, GodoGSON.class);
            productList.addAll(godoGSON.getGoods_data());
        } catch (NullPointerException e){

        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Object object : productList){
            GodoGSONProduct product = (GodoGSONProduct) object;
            System.out.println(product.getGoodsnm());
        }

        Assert.assertTrue(!productList.isEmpty());
    }
}
