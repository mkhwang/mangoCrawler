package com.enliple.crawler.task.service.impl;

import com.enliple.crawler.task.domain.ParseTask;
import com.enliple.crawler.task.service.TaskService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public class TaskServiceImpl implements TaskService {
    private Logger logger = Logger.getLogger(TaskServiceImpl.class);

    @Override
    public List<ParseTask> selectJobList() {
        return null;
    }

    @Override
    public void updateParseJob(ParseTask parseTask) {

    }

    @Override
    public void deleteParseJob(ParseTask parseTask) {

    }
}
