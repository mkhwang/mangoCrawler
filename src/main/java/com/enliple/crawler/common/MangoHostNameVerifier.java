package com.enliple.crawler.common;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by mkhwang on 2017-06-28.
 */
public class MangoHostNameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session){
        return true;
    }
}
