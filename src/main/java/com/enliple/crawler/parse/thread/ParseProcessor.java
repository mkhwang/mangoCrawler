package com.enliple.crawler.parse.thread;

import com.enliple.crawler.task.domain.ParseTask;

import java.util.concurrent.RecursiveTask;

/**
 * Created by MinKi Hwang on 2017-08-16.
 */
public class ParseProcessor extends RecursiveTask<ParseTask> {
    private final ParseTask parseTask;

    public ParseProcessor(ParseTask parseTask) {
        this.parseTask = parseTask;
    }

    @Override
    protected ParseTask compute() {
        return this.parseTask;
    }
}
