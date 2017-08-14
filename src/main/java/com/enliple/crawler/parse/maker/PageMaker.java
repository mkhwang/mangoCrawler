package com.enliple.crawler.parse.maker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public class PageMaker {
    public static List<String> getPages(String url, String pagePattern){
        List<String> pages = new ArrayList<>();
        String[] splitPagePattern = pagePattern.split("\\^");
        String pageParameter = splitPagePattern[0];
        int initValue = Integer.parseInt(splitPagePattern[1]);
        int increment = Integer.parseInt(splitPagePattern[2]);
        int maxValue = Integer.parseInt(splitPagePattern[3]);
        StringBuilder stringBuilder = new StringBuilder();
        for(; initValue < maxValue ; initValue += increment){
            stringBuilder.append(url).append(pageParameter).append(initValue);
            pages.add(stringBuilder.toString());
            stringBuilder.setLength(0);
        }
        return pages;
    }
}
