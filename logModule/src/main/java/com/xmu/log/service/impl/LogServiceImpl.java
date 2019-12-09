package com.xmu.log.service.impl;

import com.xmu.log.dao.LogDao;
import com.xmu.log.domain.LogDto;
import com.xmu.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LogServiceImpl implements LogService {

    @Autowired
    LogDao logDao;

    @Override
    public List<LogDto> listLog(String name) {
        return logDao.listLog(name);
    }

    @Override
    public LogDto addLog(LogDto oLog) {
        return logDao.addLog(oLog);
    }
}
