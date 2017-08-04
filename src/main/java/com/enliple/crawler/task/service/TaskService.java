package com.enliple.crawler.task.service;

import com.enliple.crawler.task.domain.ParseTask;

import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public interface TaskService {

    List<ParseTask> selectTaskList();

    void updateParseTask(ParseTask parseTask);

    void deleteParseTask(ParseTask parseTask);

    int getCurrentWorkingTaskCount();
}
