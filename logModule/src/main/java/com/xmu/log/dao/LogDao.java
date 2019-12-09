package com.xmu.log.dao;


import com.xmu.log.domain.LogDto;
import com.xmu.log.service.adminservice.AdminService;
import com.xmu.log.standard.Admin;
import com.xmu.log.mapper.LogMapper;
import com.xmu.log.standard.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LogDao {

    @Autowired
    LogMapper logMapper;

    @Autowired
    AdminService adminService;

    public List<LogDto> listLog(String name) {
        List<Admin> admins=adminService.listAdminByUserName(name);
        List<Log> logs=new ArrayList<Log>();
        for(Admin admin:admins){
            Log log=logMapper.listLog(admin);
            LogDto olog=new LogDto(log);
            ((LogDto) olog).setAdmin(admin);
        }
        return logs;
    }

    public LogDto addLog(LogDto log) {
        logMapper.addLog(log);
        return log;
    }
}
