package com.enliple.crawler.common;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.Assert;

/**
 * Created by MinKi Hwang on 2017-08-04.
 */
public class ConnectionFactoryTest {

    @Test
    public void connectionTest() throws Exception {
        System.out.println("Connection Test Start");
        try(SqlSession session = SessionFactory.getSession()){
            String now = session.selectOne("test.connectionTest");
            boolean result = false;
            System.out.println(now);
            if(!"".equals(now))
                result = true;
            Assert.assertTrue(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
