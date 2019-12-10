package com.xmu.log.mapper;

import com.xmu.log.domain.LogDto;
import com.xmu.log.standard.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LogMapper {


    LogDto listLog(Admin admin);

    int addLog(LogDto oLog);
}
