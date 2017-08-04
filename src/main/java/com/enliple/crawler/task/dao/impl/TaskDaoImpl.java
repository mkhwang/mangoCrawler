package com.enliple.crawler.task.dao.impl;

import com.enliple.crawler.task.dao.TaskDao;
import com.enliple.crawler.task.domain.ParseTask;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-04.
 */
public class TaskDaoImpl implements TaskDao {
    private Logger logger = Logger.getLogger(TaskDaoImpl.class);

    @Override
    public List<ParseTask> selectParseTask(SqlSession session){
        return session.selectList("task.selectParseTask");
    }

    @Override
    public void updateParseTask(ParseTask parseTask, SqlSession session){
        session.update("task.updateParseTask", parseTask);
    }

    @Override
    public void deleteParseTask(ParseTask parseTask, SqlSession session){
        session.delete("task.deleteParseTask", parseTask);
    }

    @Override
    public String getCurrentWorkingTaskCount(SqlSession session) {
        return session.selectOne("task.getCurrentWorkingTaskCount");
    }
}
