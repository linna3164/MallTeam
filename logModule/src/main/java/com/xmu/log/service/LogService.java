package com.xmu.log.service;


import com.xmu.log.domain.LogDto;

import java.util.List;

public interface LogService {

    List<LogDto> listLog(String name);

    LogDto addLog(LogDto olog);

//    boolean deleteLog(Log log);

}
