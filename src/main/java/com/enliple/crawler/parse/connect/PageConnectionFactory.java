package com.enliple.crawler.parse.connect;

import com.enliple.crawler.parse.connect.impl.JsoupConnection;
import com.enliple.crawler.parse.connect.impl.UrlStreamConnect;
import com.enliple.crawler.parse.domain.ParsePattern;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class PageConnectionFactory {
    public static PageConnection getPageConnection(ParsePattern parsePattern){
        PageConnection pageConnection = null;
        if("cafe24".equals(parsePattern.getShopType()) || "makeShop".equals(parsePattern.getShopType()))
            pageConnection = new UrlStreamConnect();
        else
            pageConnection = new JsoupConnection();

        return pageConnection;
    }
}
