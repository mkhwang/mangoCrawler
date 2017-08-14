package com.enliple.crawler.parse.maker;

/**
 * Created by MinKi Hwang on 2017-08-14.
 */
public class PcodeMaker {
    public static String getPcodeFromUrl(String url, String pCodePattern) throws NullPointerException{
        String pCode = url;
        String[] pCodePatterns = pCodePattern.split("\\$");
        for(String pattern : pCodePatterns){
            String[] smallPatterns = pattern.split("\\^");
            int seq = Integer.parseInt(smallPatterns[1]);
            pCode = pCode.split(smallPatterns[0])[seq];
        }
        return pCode;
    }
}
