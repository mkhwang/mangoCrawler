package com.enliple.crawler.parse.maker;

import com.enliple.crawler.common.util.Cafe24ParsingInfoUtil;
import com.enliple.crawler.parse.domain.ParsingInfo;
import com.enliple.crawler.parse.maker.url.UrlMaker;
import com.enliple.crawler.parse.maker.url.impl.Cafe24UrlMaker;

/**
 * Created by MinKi Hwang on 2017-08-08.
 */
public class UrlMakerFactory {
    public static UrlMaker getUrlMaker(ParsingInfo parsingInfo){
        UrlMaker maker = null;

        if("cafe24".equals(parsingInfo.getShopType()) || Cafe24ParsingInfoUtil.isCafe24Platform(parsingInfo))
            maker = new Cafe24UrlMaker();


        return maker;
    }
}
