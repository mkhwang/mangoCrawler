package com.enliple.crawler.common.util;

import com.enliple.crawler.parse.domain.ParsingInfo;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public class Cafe24ParsingInfoUtil {
    public static boolean isCafe24Flattform(ParsingInfo parsingInfo){
        boolean result = false;
        if(parsingInfo.getCategoryCode().contains("cate_no") && parsingInfo.getpCode().equals("product_no"))
            result = true;

        return result;
    }

    public static boolean isCafe24MobileUrl(String mobileUrl){
        boolean result = false;
        if(mobileUrl.contains("/exec/front/Product/ApiProductNormal?"))
            result = true;

        return result;
    }

    public static boolean isCafe24MobileCategory(String category){
        boolean result = false;
        if(category.contains("count=1000"))
            result = true;

        return result;
    }

    public static ParsingInfo refineCafe24ParsingInfo(ParsingInfo parsingInfo){
        if(!isCafe24MobileUrl(parsingInfo.getMobileUrl()) || "".equals(parsingInfo.getMobileUrl()))
            parsingInfo.setMobileUrl( getMobileMainUrl(parsingInfo.getUrl()) + "exec/front/Product/ApiProductNormal?" );

        if(!isCafe24MobileCategory(parsingInfo.getCategoryCode()))
            parsingInfo.setCategoryCode("count=1000&"+parsingInfo.getCategoryCode());

        return parsingInfo;
    }

    public static String getMobileMainUrl(String url){
        url = getMainUrl(url);
        String mobileMainUrl = "";
        if(url.contains("www."))
            mobileMainUrl = url.replace("http://www.", "http://m.");
        else if(!url.contains("www."))
            mobileMainUrl = url.replace("http://", "http://m.");

        return mobileMainUrl;
    }

    public static String getMainUrl(String url){
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
