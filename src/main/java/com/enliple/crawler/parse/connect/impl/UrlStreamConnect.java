package com.enliple.crawler.parse.connect.impl;

import com.enliple.crawler.common.util.LoadProperties;
import com.enliple.crawler.parse.connect.PageConnection;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public class UrlStreamConnect implements PageConnection {
    private Logger logger = Logger.getLogger(UrlStreamConnect.class);

    @Override
    public Object getPageData(String url) throws Exception{
        URLConnection urlConnection;
        InputStream inputStream = null;
        Object pageData = null;
        try {
            urlConnection = new URL(url).openConnection();
            urlConnection.setReadTimeout(LoadProperties.getGlobalTimeout());
            urlConnection.setConnectTimeout(LoadProperties.getGlobalTimeout());
            inputStream = urlConnection.getInputStream();
            pageData = IOUtils.toString(inputStream, "UTF-8");
        } catch (Exception e) {
            logger.debug(e.getMessage());
        } finally {
            try {
                if(inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                logger.debug(e.getMessage());
            }
        }
        return pageData;
    }
}
