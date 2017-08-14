package com.enliple.crawler.parse.connect;

import com.enliple.crawler.parse.connect.impl.JSoupConnection;
import com.enliple.crawler.parse.connect.impl.UrlStreamConnection;
import com.enliple.crawler.parse.domain.ParsePattern;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class PageConnectionFactory {
    public static PageConnection getPageConnection(ParsePattern parsePattern){
        PageConnection pageConnection;
        if("cafe24".equals(parsePattern.getShopType()) || "makeshop".equals(parsePattern.getShopType()))
            pageConnection = new UrlStreamConnection();
        else
            pageConnection = new JSoupConnection();

        return pageConnection;
    }
}
