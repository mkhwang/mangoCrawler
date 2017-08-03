package com.enliple.crawler.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by mkhwang on 2017-06-26.
 */
public class LoadProperties {
    private FileInputStream fileInputStream = null;
    private Properties properties = null;

    /**
     * Instantiates a new Load properties.
     */
    public LoadProperties() {
        try {
            File conf = new File(System.getProperty("user.dir")+"/mangoCrawler/conf/parser.properties");
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
    public String getDbConfig(){
        System.out.println(properties.getProperty("dbconfig"));
        return properties.getProperty("dbconfig");
    }

    /**
     * Get db props string.
     *
     * @return the string
     */
    public String getDbProps(){
        System.out.println(properties.getProperty("dbprops"));
        return properties.getProperty("dbprops");
    }

    /**
     * Gets schema.
     *
     * @return the schema
     */
    public String getSchema() { return properties.getProperty("schema"); }

    /**
     * Get max thread count int.
     *
     * @return the int
     */
    public int getMaxThreadCount(){
        return Integer.parseInt(properties.getProperty("maxThreadCount"));
    }

    /**
     * Gets job search period.
     *
     * @return the job search period
     */
    public int getJobSearchPeriod() {
        return Integer.parseInt(properties.getProperty("jobSearchPeriod"));
    }

    public int getGlobalTimeout(){
        return Integer.parseInt(properties.getProperty("globalTimeout"));
    }

    public boolean getGlobalColorParseFlag(){
        if("true".equals(properties.getProperty("globalColorParse")))
            return true;
        else
            return false;
    }
}
