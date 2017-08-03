package com.enliple.crawler.parse.command;

import com.enliple.crawler.common.util.LoadProperties;
import com.enliple.crawler.parse.service.ParseService;
import com.enliple.crawler.parse.service.impl.ParseServiceImpl;
import com.enliple.crawler.parse.thread.ParseExecutor;
import com.enliple.crawler.task.domain.ParseTask;
import com.enliple.crawler.task.service.TaskService;
import com.enliple.crawler.task.service.impl.TaskServiceImpl;
import org.apache.log4j.Logger;

import java.util.List;


/**
 * Created by MinKi Hwang on 2017-08-02.
 */
public class Parse {
    private Logger logger = Logger.getLogger(Parse.class);

    public static void main(String[] args){
        ParseExecutor parseExecutor = new ParseExecutor();
        TaskService taskService = new TaskServiceImpl();
        List<ParseTask> parseTasks;
        while (true){
            try{
                Thread.sleep(new LoadProperties().getJobSearchPeriod());
                parseTasks = taskService.selectJobList();

                for(ParseTask task : parseTasks)
                    parseExecutor.add(task);

                parseTasks.clear();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
