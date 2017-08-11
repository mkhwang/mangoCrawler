package com.enliple.crawler.parse.maker.productList.impl;

import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.maker.ProductListMakerFactory;
import com.enliple.crawler.parse.maker.productList.ProductListMaker;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MinKi Hwang on 2017-08-10.
 */
public class ElementsFromJSONProductListMakerTest {

    @Test
    public void getProductListTest(){
        //ProductListMaker maker = new ElementsFromJSONProductListMaker();
        ParsePattern parsePattern = new ParsePattern();
        parsePattern.setShopType("test");
        parsePattern.setProductListPattern("item&ul^li.sct_li");
        ProductListMaker maker = ProductListMakerFactory.getProductListMaker(parsePattern);
        Object pageData = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        URL urlObject = null;
        JSONParser jsonParser = new JSONParser();
        JSONObject json = new JSONObject();
        String jsonText="";
        try{
            urlObject = new URL("http://m.themasil.co.kr/shop/ajax.list.php?ca_id=3010&page=1");
            connection = (HttpURLConnection)urlObject.openConnection();
            inputStream = connection.getInputStream();
            jsonText = IOUtils.toString(inputStream, "UTF-8");
            pageData = (JSONObject) jsonParser.parse(jsonText);
            inputStream.close();
            connection.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(inputStream != null)
                    inputStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                if(connection != null)
                    connection.disconnect();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(maker.getProductList(pageData, parsePattern.getProductListPattern()).toString());
        Assert.assertTrue(true);
    }
}
