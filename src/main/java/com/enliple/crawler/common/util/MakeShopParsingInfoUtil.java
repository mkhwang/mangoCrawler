package com.enliple.crawler.common.util;

import com.enliple.crawler.parse.domain.ParsingInfo;

/**
 * Created by MinKi Hwang on 2017-08-04.
 */
public class MakeShopParsingInfoUtil {
    public static boolean isMakeShopFlattform(ParsingInfo parsingInfo){
        boolean result = false;
        if(parsingInfo.getCategoryCode().contains("xcode") && parsingInfo.getpCode().equals("branduid"))
            result = true;

        return result;
    }

    public static boolean isMakeShopMobileUrl(String mobileUrl){
        boolean result = false;
        if(mobileUrl.contains("m/product_list.action.html?"))
            result = true;

        return result;
    }

    public static boolean isMakeShopMobileCategory(String category){
        boolean result = false;
        if(category.contains("action_mode=get_list&viewtype=gallery&"))
            result = true;

        return result;
    }

    public static ParsingInfo refineCafe24ParsingInfo(ParsingInfo parsingInfo){
        if(!isMakeShopMobileUrl(parsingInfo.getMobileUrl()) || "".equals(parsingInfo.getMobileUrl()))
            parsingInfo.setMobileUrl( getMobileMainUrl(parsingInfo.getUrl()) + "product_list.action.html?" );

        if(!isMakeShopMobileCategory(parsingInfo.getCategoryCode()))
            parsingInfo.setCategoryCode("action_mode=get_list&viewtype=gallery&"+parsingInfo.getCategoryCode());

        return parsingInfo;
    }

    public static String getMobileMainUrl(String url){
        return getMainUrl(url)+"/m/";
    }

    public static String getMainUrl(String url){
        String result="";
        if(url.contains("/product/"))
            result =  url.split("/product/")[0];
        else if(url.contains("/shop/"))
            result =  url.split("/shop/")[0];
        else if(url.contains("/m/"))
            result =  url.split("/m/")[0];

        return result;
    }
}
