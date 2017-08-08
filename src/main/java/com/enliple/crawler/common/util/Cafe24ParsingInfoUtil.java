package com.enliple.crawler.common.util;

import com.enliple.crawler.parse.domain.ParsingInfo;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public class Cafe24ParsingInfoUtil {
    public static boolean isCafe24Platform(ParsingInfo parsingInfo){
        boolean result = false;
        if(parsingInfo.getCategoryCode().contains("cate_no") && parsingInfo.getpCode().equals("product_no"))
            result = true;

        return result;
    }
}
