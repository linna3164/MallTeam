package com.xmu.log.service.impl;

import com.xmu.log.dao.LogDao;
import com.xmu.log.domain.Log;
import com.xmu.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LogServiceImpl implements LogService {

    @Autowired
    LogDao logDao;
    @Override
    public List<Log> listLog(String name) {
        return null;
    }

    @Override
    public Log addLog(Log log) {
        return null;
    }
}
