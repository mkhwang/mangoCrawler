package com.enliple.crawler.common.util;

import com.enliple.crawler.parse.domain.ParsingInfo;

/**
 * Created by MinKi Hwang on 2017-08-04.
 */
public class MakeShopParsingInfoUtil {
    public static boolean isMakeShopPlatform(ParsingInfo parsingInfo){
        boolean result = false;
        if(parsingInfo.getCategoryCode().contains("xcode") && parsingInfo.getpCode().equals("branduid"))
            result = true;

        return result;
    }
}
