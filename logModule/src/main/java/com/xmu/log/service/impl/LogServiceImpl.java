package com.xmu.log.service.impl;

import com.xmu.log.dao.LogDao;
import com.xmu.log.domain.OLog;
import com.xmu.log.service.LogService;
import com.xmu.log.standard.Log;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LogServiceImpl implements LogService {

    @Autowired
    LogDao logDao;

    @Override
    public List<OLog> listLog(String name) {
        return logDao.listLog(name);
    }

    @Override
    public OLog addLog(OLog oLog) {
        return logDao.addLog(oLog);
    }
}
