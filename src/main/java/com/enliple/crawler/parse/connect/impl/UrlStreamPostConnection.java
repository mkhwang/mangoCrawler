package com.enliple.crawler.parse.connect.impl;

import com.enliple.crawler.common.util.LoadProperties;
import com.enliple.crawler.parse.connect.PageConnection;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MinKi Hwang on 2017-08-24.
 */
public class UrlStreamPostConnection implements PageConnection {
    private Logger logger = Logger.getLogger(UrlStreamPostConnection.class);

    @Override
    public Object getPageData(String url) throws Exception {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        Object pageData = null;
        OutputStream outputStream = null;
        String mainUrl;
        String parameter;

        try {
            mainUrl = url.split("\\?")[0];
            parameter = url.split("\\?")[1];

            URL urlObject = new URL(mainUrl);
            urlConnection = (HttpURLConnection) urlObject.openConnection();
            urlConnection.setReadTimeout(LoadProperties.getGlobalTimeout());
            urlConnection.setConnectTimeout(LoadProperties.getGlobalTimeout());
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setDefaultUseCaches(false);
            outputStream = urlConnection.getOutputStream();
            outputStream.write(parameter.getBytes());
            outputStream.flush();
            outputStream.close();
            inputStream = urlConnection.getInputStream();
            pageData = IOUtils.toString(inputStream, "EUC-KR");
        } catch (Exception e) {
            logger.debug(e.getMessage());
        } finally {
            try {
                if(outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                logger.debug(e.getMessage());
            }
            try{
                if(urlConnection != null)
                    urlConnection.disconnect();
            }catch(Exception e){
                logger.debug(e.getMessage());
            }
        }
        return pageData;
    }
}
