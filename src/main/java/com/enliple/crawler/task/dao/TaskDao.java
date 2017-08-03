package com.enliple.crawler.task.dao;

import com.enliple.crawler.task.domain.ParseTask;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public class TaskDao {
    public List<ParseTask> selectParseJob(SqlSession session){
        return session.selectList("job.selectParseJob");
    }

    public void updateParseJob(ParseTask parseTask, SqlSession session){
        session.update("job.updateParseJob", parseTask);
    }

    public void deleteParseJob(ParseTask parseTask, SqlSession session){
        session.delete("job.deleteParseJob", parseTask);
    }
}
