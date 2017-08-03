package com.enliple.crawler.parse.connect;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public interface PageConnection {
    Object getPageData(String url) throws Exception;
}
