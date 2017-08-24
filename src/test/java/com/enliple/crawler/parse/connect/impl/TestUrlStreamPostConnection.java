package com.enliple.crawler.parse.connect.impl;

import com.enliple.crawler.parse.connect.PageConnection;
import com.enliple.crawler.parse.domain.ParsePattern;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by MinKi Hwang on 2017-08-24.
 */
public class TestUrlStreamPostConnection {
    @Test
    public void testUrlStreamPostConnectionImplementation(){
        PageConnection connection = new UrlStreamPostConnection();
        ParsePattern parsePattern = new ParsePattern();
        parsePattern.setShopType("godo");
        String url = "http://banhala.com/m2/proc/mAjaxAction.php?mode=get_goods&view_type=list&category=008&item_cnt=50";
        Object pageData = null;
        try {
            pageData = connection.getPageData(url);
            System.out.println((String)pageData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(!"".equals((String)pageData));
    }
}
