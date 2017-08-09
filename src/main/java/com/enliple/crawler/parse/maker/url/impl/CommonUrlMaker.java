package com.enliple.crawler.parse.maker.url.impl;

import com.enliple.crawler.parse.domain.ParsingInfo;
import com.enliple.crawler.parse.maker.url.UrlMaker;

/**
 * Created by MinKi Hwang on 2017-08-09.
 */
public class CommonUrlMaker implements UrlMaker {
    @Override
    public String getUrl(ParsingInfo parsingInfo) {
        String address;
        String parameter = parsingInfo.getCategoryCode();

        if(!"".equals(parsingInfo.getMobileUrl()) || parsingInfo.getMobileUrl() != null)
            address = parsingInfo.getMobileUrl();
        else
            address = parsingInfo.getUrl();

        return address + parameter;
    }
}
