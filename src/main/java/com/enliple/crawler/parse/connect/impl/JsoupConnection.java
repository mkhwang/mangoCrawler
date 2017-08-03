package com.enliple.crawler.parse.connect.impl;

import com.enliple.crawler.common.util.LoadProperties;
import com.enliple.crawler.parse.connect.PageConnection;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public class JSoupConnection implements PageConnection {
    private Logger logger = Logger.getLogger(JSoupConnection.class);

    @Override
    public Object getPageData(String url) throws Exception {
        Object pageData = "";
        try {
            pageData = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0")
                    .timeout(new LoadProperties().getGlobalTimeout())
                    .get();
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        return pageData;
    }
}
