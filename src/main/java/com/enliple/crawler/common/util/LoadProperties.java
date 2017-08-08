package com.enliple.crawler.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by mkhwang on 2017-06-26.
 */
public class LoadProperties {
    private static FileInputStream fileInputStream = null;
    private static Properties properties = null;

    /**
     * Instantiates a new Load properties.
     */
    static {
        try {
            File conf = new File(System.getProperty("user.dir")+"/conf/parser.properties");
            fileInputStream = new FileInputStream(conf);
            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            System.err.println("error loading conf file...");
            e.printStackTrace();
        }
    }

    /**
     * Get db config string.
     *
     * @return the string
     */
    public static String getDbConfig(){
        return properties.getProperty("dbconfig");
    }

    /**
     * Get db props string.
     *
     * @return the string
     */
    public static String getDbProps(){
        return properties.getProperty("dbprops");
    }

    /**
     * Gets schema.
     *
     * @return the schema
     */
    public static String getSchema() { return properties.getProperty("schema"); }

    /**
     * Get max thread count int.
     *
     * @return the int
     */
    public static int getMaxThreadCount(){
        return Integer.parseInt(properties.getProperty("maxThreadCount"));
    }

    /**
     * Gets job search period.
     *
     * @return the job search period
     */
    public static int getJobSearchPeriod() {
        return Integer.parseInt(properties.getProperty("jobSearchPeriod"));
    }

    public static int getGlobalTimeout(){
        return Integer.parseInt(properties.getProperty("globalTimeout"));
    }

    public static int getMaxWaitTask() {
        return Integer.parseInt(properties.getProperty("maxWaitTask"));
    }

    public static String getSoldOutPeriod() { return properties.getProperty("soldOutPeriod"); }

    public static String getImageSaveDir() { return properties.getProperty("imageSaveDir"); }
}
