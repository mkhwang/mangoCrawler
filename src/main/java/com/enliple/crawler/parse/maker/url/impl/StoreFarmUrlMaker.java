package com.enliple.crawler.parse.maker.url.impl;

import com.enliple.crawler.parse.domain.ParsingInfo;
import com.enliple.crawler.parse.maker.url.UrlMaker;
import org.apache.log4j.Logger;

/**
 * Created by MinKi Hwang on 2017-08-25.
 */
public class StoreFarmUrlMaker implements UrlMaker{
    private Logger logger = Logger.getLogger(StoreFarmUrlMaker.class);

    @Override
    public String getUrl(ParsingInfo parsingInfo) {
        String address;
        String parameter = parsingInfo.getCategoryCode().replaceAll("category=", "category/");

        if(!"".equals(parsingInfo.getMobileUrl()) || parsingInfo.getMobileUrl() != null)
            address = parsingInfo.getMobileUrl();
        else
            address = parsingInfo.getUrl();

        return address + "/" + parameter;
    }
}
