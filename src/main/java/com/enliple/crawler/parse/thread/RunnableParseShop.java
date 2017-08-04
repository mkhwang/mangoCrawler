package com.enliple.crawler.parse.thread;

import com.enliple.crawler.parse.service.ParseService;
import com.enliple.crawler.parse.service.impl.ParseServiceImpl;
import com.enliple.crawler.task.domain.ParseTask;
import com.enliple.crawler.task.service.TaskService;
import com.enliple.crawler.task.service.impl.TaskServiceImpl;
import org.apache.log4j.Logger;

/**
 * Created by MinKi Hwang on 2017-08-03.
 */
public class RunnableParseShop implements Runnable {
    private Logger logger = Logger.getLogger(RunnableParseShop.class);

    private ParseTask parseTask;
    private TaskService taskService;
    private ParseService parseService;


    public RunnableParseShop(ParseTask parseTask){
        this.parseTask = parseTask;
        taskService = new TaskServiceImpl();
        parseService = new ParseServiceImpl();
    }

    @Override
    public void run() {
        this.parseShop();
    }

    private void parseShop(){
        parseTask.setState("2");
        taskService.updateParseTask(parseTask);
        parseService.parseShop(parseTask);
        taskService.deleteParseTask(parseTask);
    }
}
