package com.enliple.crawler.task.domain;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public class ParseTask {
    private String scCode;
    private String state;
    private String regDate;

    public String getScCode() {
        return scCode;
    }

    public void setScCode(String scCode) {
        this.scCode = scCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
