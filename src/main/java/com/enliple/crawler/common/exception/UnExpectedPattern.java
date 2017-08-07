package com.enliple.crawler.common.exception;

import org.apache.log4j.Logger;

import java.rmi.UnexpectedException;

/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public class UnExpectedPattern extends Exception {
    private Logger logger = Logger.getLogger(UnexpectedException.class);

    String message;

    public UnExpectedPattern(){
        super();
        message = "UnexpectedPattern";
        logger.debug(message);
    }

    public UnExpectedPattern(String message){
        super(message);
        this.message = message;
    }

    public String getError(){
        return message;
    }

}
