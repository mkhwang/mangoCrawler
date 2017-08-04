package com.enliple.crawler.task.service.impl;

import com.enliple.crawler.common.SessionFactory;
import com.enliple.crawler.task.dao.TaskDao;
import com.enliple.crawler.task.dao.impl.TaskDaoImpl;
import com.enliple.crawler.task.domain.ParseTask;
import com.enliple.crawler.task.service.TaskService;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public class TaskServiceImpl implements TaskService {
    private Logger logger = Logger.getLogger(TaskServiceImpl.class);
    private TaskDao dao;

    public TaskServiceImpl(){
        dao = new TaskDaoImpl();
    }

    @Override
    public List<ParseTask> selectTaskList() {
        List<ParseTask> parseTasks = null;
        try(SqlSession session = SessionFactory.getSession()){
            parseTasks = dao.selectParseTask(session);
        }catch(Exception e){
            e.printStackTrace();
        }
        return parseTasks;
    }

    @Override
    public void updateParseTask(ParseTask parseTask) {
        try(SqlSession session = SessionFactory.getSession()){
            dao.updateParseTask(parseTask, session);
            session.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteParseTask(ParseTask parseTask) {
        try(SqlSession session = SessionFactory.getSession()){
            dao.deleteParseTask(parseTask, session);
            session.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getCurrentWorkingTaskCount() {
        int currentWorkingTaskCount = 0;
        try(SqlSession session = SessionFactory.getSession()){
            currentWorkingTaskCount = Integer.parseInt(dao.getCurrentWorkingTaskCount(session));
        }catch(Exception e){
            e.printStackTrace();
        }
        return currentWorkingTaskCount;
    }
}
