package com.enliple.crawler.task.dao;

import com.enliple.crawler.task.domain.ParseTask;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by MinKi Hwang on 2017-08-01.
 */
public interface TaskDao {
    List<ParseTask> selectParseTask(SqlSession session);

    void updateParseTask(ParseTask parseTask, SqlSession session);

    void deleteParseTask(ParseTask parseTask, SqlSession session);

    String getCurrentWorkingTaskCount(SqlSession session);
}
