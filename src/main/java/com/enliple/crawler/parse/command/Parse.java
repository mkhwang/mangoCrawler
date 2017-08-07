package com.enliple.crawler.parse.command;

import com.enliple.crawler.common.util.LoadProperties;
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
    private static Logger logger = Logger.getLogger(Parse.class);

    public static void main(String[] args){
        System.out.println("parse Start");
        logger.debug("parse Start");

        ParseExecutor parseExecutor = new ParseExecutor();
        TaskService taskService = new TaskServiceImpl();
        List<ParseTask> parseTasks;
        while (true){
            try{
                Thread.sleep(LoadProperties.getJobSearchPeriod());
                if(taskService.getCurrentWorkingTaskCount() < LoadProperties.getMaxWaitTask()){
                    parseTasks = taskService.selectTaskList();

                    for(ParseTask task : parseTasks){
                        System.out.println("ParseExecutor add : " + task.getScCode() + "("+task.getState()+")");
                        logger.debug("ParseExecutor add : " + task.getScCode() + "("+task.getState()+")");
                        parseExecutor.add(task);
                    }
                    parseTasks.clear();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
