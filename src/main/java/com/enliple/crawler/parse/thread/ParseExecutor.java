package com.enliple.crawler.parse.thread;

import com.enliple.crawler.common.util.LoadProperties;
import com.enliple.crawler.task.domain.ParseTask;
import com.enliple.crawler.task.service.TaskService;
import com.enliple.crawler.task.service.impl.TaskServiceImpl;
import org.apache.log4j.Logger;

import java.util.concurrent.ForkJoinPool;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class ParseExecutor {
    private Logger logger = Logger.getLogger(ParseExecutor.class);
    private static ForkJoinPool forkJoinPool;
    private TaskService taskService;

    static {
        try {
            forkJoinPool = new ForkJoinPool(LoadProperties.getMaxThreadCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ParseExecutor(){
        taskService = new TaskServiceImpl();
    }

    public void add(ParseTask parseTask){
        Runnable runnable;
        try{
            parseTask.setState("1");
            taskService.updateParseTask(parseTask);
            System.out.println(parseTask.getScCode() + " is ready to parse..");
            logger.debug(parseTask.getScCode() + " is ready to parse..");
            runnable = new RunnableParseShop(parseTask);
            forkJoinPool.submit(runnable);
            System.out.println(parseTask.getScCode() + " RUN()");
            logger.debug(parseTask.getScCode() + " RUN()");
        }catch(Exception e){
            logger.debug(e);
        }
    }

}
