package com.enliple.crawler.parse;

import com.enliple.crawler.common.SessionFactory;
import com.enliple.crawler.parse.dao.ParseDao;
import com.enliple.crawler.parse.dao.impl.ParseDaoImpl;
import com.enliple.crawler.parse.domain.ParsePattern;
import com.enliple.crawler.parse.service.ParseService;
import com.enliple.crawler.parse.service.impl.ParseServiceImpl;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by MinKi Hwang on 2017-08-08.
 */
public class PatternTest {
    @Test
    public void connectionTest() throws Exception {
        System.out.println("Connection Test Start");
        try(SqlSession session = SessionFactory.getSession()){
            ParsePattern parsePattern = new ParsePattern();
            ParseDao parseDao = new ParseDaoImpl();
            parsePattern = parseDao.getParsePattern("167b007a416fdcbf602d78a613c98c44", session);
            System.out.println(parsePattern.getSaleCheckPattern());
            Assert.assertTrue("product_sale_display".equals(parsePattern.getSaleCheckPattern()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
