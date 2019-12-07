package com.xmu.log.service;

import com.xmu.log.domain.Log;

import java.util.List;

public interface LogService {

    List<Log> listLog(String name);

    Log addLog(Log log);

//    boolean deleteLog(Log log);

}
