package com.xmu.log.dao;

import com.xmu.log.domain.Log;
import com.xmu.log.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LogDao {

    @Autowired
    LogMapper logMapper;

    public List<Log> listLog(String name) {
        return logMapper;
    }

    public Log addLog(Log log) {
        return null;
    }
}
