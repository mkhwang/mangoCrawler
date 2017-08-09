package com.enliple.crawler.parse.maker.url.impl;

import com.enliple.crawler.parse.domain.ParsingInfo;
import com.enliple.crawler.parse.maker.url.UrlMaker;

/**
 * Created by MinKi Hwang on 2017-08-09.
 */
public class MakeShopUrlMaker implements UrlMaker {
    @Override
    public String getUrl(ParsingInfo parsingInfo) {
        return getMakeShopUrl(parsingInfo);
    }

    private String getMakeShopUrl(ParsingInfo parsingInfo){
        String address;
        String parameter;
        if(!isMakeShopMobileUrl(parsingInfo.getMobileUrl()) || "".equals(parsingInfo.getMobileUrl()))
            address = getMobileMainUrl(parsingInfo.getUrl()) + "/product_list.action.html?";
        else
            address = parsingInfo.getMobileUrl();

        if(!isMakeShopMobileCategory(parsingInfo.getCategoryCode()))
            parameter = "action_mode=get_list&viewtype=gallery&soldout_sort=Y&" + parsingInfo.getCategoryCode();
        else
            parameter = parsingInfo.getCategoryCode();

        return address + parameter;
    }

    private boolean isMakeShopPlatform(ParsingInfo parsingInfo){
        boolean result = false;
        if(parsingInfo.getCategoryCode().contains("xcode") && parsingInfo.getpCode().toLowerCase().equals("branduid"))
            result = true;

        return result;
    }

    private boolean isMakeShopMobileUrl(String mobileUrl){
        boolean result = false;
        if(mobileUrl.contains("/product_list.action.html?"))
            result = true;

        return result;
    }

    private boolean isMakeShopMobileCategory(String category){
        boolean result = false;
        if(category.contains("action_mode=get_list&viewtype"))
            result = true;

        return result;
    }

    private ParsingInfo refineCafe24ParsingInfo(ParsingInfo parsingInfo){
        if(!isMakeShopMobileUrl(parsingInfo.getMobileUrl()) || "".equals(parsingInfo.getMobileUrl()))
            parsingInfo.setMobileUrl( getMobileMainUrl(parsingInfo.getUrl()) + "/product_list.action.html?" );

        if(!isMakeShopMobileCategory(parsingInfo.getCategoryCode()))
            parsingInfo.setCategoryCode("action_mode=get_list&viewtype=gallery&soldout_sort=Y&"+parsingInfo.getCategoryCode());

        return parsingInfo;
    }

    private String getMobileMainUrl(String url){
        url = getMainUrl(url);
        String mobileMainUrl = url + "/m";
        return mobileMainUrl;
    }

    private String getMainUrl(String url){
        String result="";
        if(url.contains("/shop/"))
            result =  url.split("/shop/")[0];
        else if(url.contains("/product/"))
            result =  url.split("/product/")[0];
        else if(url.contains("/m/product_list.html?"))
            result =  url.split("/m/product_list.html?")[0];
        return result;
    }
}
