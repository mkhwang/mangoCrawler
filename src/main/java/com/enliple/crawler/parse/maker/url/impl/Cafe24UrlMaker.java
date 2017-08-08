package com.enliple.crawler.parse.maker.url.impl;

import com.enliple.crawler.parse.domain.ParsingInfo;
import com.enliple.crawler.parse.maker.url.UrlMaker;

/**
 * Created by MinKi Hwang on 2017-08-08.
 */
public class Cafe24UrlMaker implements UrlMaker {
    @Override
    public String getUrl(ParsingInfo parsingInfo) {
        return getCafe24Url(parsingInfo);
    }

    private boolean isCafe24MobileUrl(String mobileUrl){
        boolean result = false;
        if(mobileUrl.contains("/exec/front/Product/ApiProductNormal?"))
            result = true;

        return result;
    }

    private boolean isCafe24MobileCategory(String category){
        boolean result = false;
        if(category.contains("count=1000"))
            result = true;

        return result;
    }

    private String getCafe24Url(ParsingInfo parsingInfo){
        String address;
        String parameter;
        if(!isCafe24MobileUrl(parsingInfo.getMobileUrl()) || "".equals(parsingInfo.getMobileUrl()))
            address = getMobileMainUrl(parsingInfo.getUrl()) + "exec/front/Product/ApiProductNormal?";
        else
            address = parsingInfo.getMobileUrl();

        if(!isCafe24MobileCategory(parsingInfo.getCategoryCode()))
            parameter = "count=1000&"+parsingInfo.getCategoryCode();
        else
            parameter = parsingInfo.getCategoryCode();

        return address + parameter;
    }

    public String getMobileMainUrl(String url){
        url = getMainUrl(url);
        String mobileMainUrl = "";
        if(url.contains("www."))
            mobileMainUrl = url.replace("http://www.", "http://m.");
        else if(!url.contains("www."))
            mobileMainUrl = url.replace("http://", "http://m.");

        return mobileMainUrl;
    }

    public String getMainUrl(String url){
        String result="";

        if(url.contains("product"))
            result =  url.split("product")[0];
        else if(url.contains("front"))
            result =  url.split("front")[0];
        else if(url.contains("category"))
            result =  url.split("category")[0];

        return result;
    }
}
