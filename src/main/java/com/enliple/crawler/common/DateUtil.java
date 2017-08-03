package com.enliple.crawler.common;

import java.text.SimpleDateFormat;

/**
 * Created by mkhwang on 2017-06-26.
 */
public class DateUtil {
    /**
     * Get current time string.
     *
     * @param timeFormat the time format
     * @return the string
     */
    public static String getCurrentTime(String timeFormat){
        /**
         * YYYY-MM-dd HH:mm:ss:SSS
         */
        return new SimpleDateFormat(timeFormat).format(System.currentTimeMillis());
    }
}
