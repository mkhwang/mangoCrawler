package com.enliple.crawler.common.exception;

import org.apache.log4j.Logger;


/**
 * Created by MinKi Hwang on 2017-08-07.
 */
public class FilterStringException extends Exception {
    private Logger logger = Logger.getLogger(FilterStringException.class);

    String message;

    public FilterStringException(){
        super();
        message = "FilterStringException";
        logger.debug(message);
    }

    public FilterStringException(String message){
        super(message);
        this.message = message;
    }

    public String getError(){
        return message;
    }
}
