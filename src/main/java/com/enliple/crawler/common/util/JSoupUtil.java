/*
 * Copyright (c) 2017. by MangoPlanet All Pictures cannot be copied without permission.
 */

package com.enliple.crawler.common.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by MinKi Hwang on 2017-07-14.
 */
public class JSoupUtil {
    public static Elements getElements(Object data, String pattern) {
        String[] patternDetail = pattern.split("\\^");
        Elements tempElements = null;
        Element tempElement = null;
        for (int i = 0; i < patternDetail.length; i++) {
            if (i == 0 && data instanceof Document) {
                tempElements = ((Document) data).select(patternDetail[i]);
            } else if(i == 0 && data instanceof Element) {
                tempElements = ((Element) data).select(patternDetail[i]);
            } else if (tempElement != null){
                tempElements = tempElement.select(patternDetail[i]);
                tempElement = null;
            } else {
                try {
                    tempElement = tempElements.get(Integer.parseInt(patternDetail[i]));
                } catch (NumberFormatException e) {
                    tempElements = tempElements.select(patternDetail[i]);
                }
            }
        }
        return tempElements;
    }

    public static String getAttribute(Object data, String pattern){
        String result;
        if(pattern.contains("$")){
            try {
                Elements temp = getElements(data, pattern.split("\\$")[0]);
                result = temp.attr(pattern.split("\\$")[1]);
            } catch (Exception e) {
                result = "";
            }
        }else
            result = getElements(data, pattern).html();

        return result;
    }
}
