package com.xmu.log.service;


import com.xmu.log.domain.OLog;
import com.xmu.log.standard.Log;

import java.util.List;

public interface LogService {

    List<OLog> listLog(String name);

    OLog addLog(OLog olog);

//    boolean deleteLog(Log log);

}
