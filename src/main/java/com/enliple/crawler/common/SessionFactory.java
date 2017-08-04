/*
 * Copyright (c) 2017. by MangoPlanet All Pictures cannot be copied without permission.
 */

package com.enliple.crawler.common;

import com.enliple.crawler.common.util.LoadProperties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by mkhwang on 2017-06-26.
 */
public class SessionFactory {
    private static SqlSessionFactory factory = null;

    static {
        Properties props = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(LoadProperties.getDbProps());
            props.load(fis);
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            factory = builder.build(Resources.getResourceAsReader(LoadProperties.getDbConfig()), LoadProperties.getSchema(), props);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Gets session.
     *
     * @return the session
     */
    public static SqlSession getSession() {
        SqlSession session = null;
        try {
            session = factory.openSession(false);
        } catch (Exception e) {
            System.out.println("connection error[" + e.toString() + "]");
            e.printStackTrace();
        }
        return session;
    }
}
