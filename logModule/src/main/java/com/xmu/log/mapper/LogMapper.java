package com.xmu.log.mapper;

import com.xmu.log.domain.OLog;
import com.xmu.log.standard.Admin;
import com.xmu.log.standard.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LogMapper {


    OLog listLog(Admin admin);

    int addLog(OLog oLog);
}
