package com.enliple.crawler.task.service;

import com.enliple.crawler.task.domain.ParseTask;

import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public interface TaskService {

    List<ParseTask> selectJobList();

    void updateParseJob(ParseTask parseTask);

    void deleteParseJob(ParseTask parseTask);
}
