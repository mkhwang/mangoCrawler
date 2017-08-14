package com.enliple.crawler.common;

import com.enliple.crawler.common.util.StringUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by MinKi Hwang on 2017-08-08.
 */
public class TestStringUtil {
    @Test
    public void testConnection() throws Exception {
        String url = "http://m.dalakbangcat.com";
        System.out.println(StringUtil.getDomainAuthority(url));
    }
}
