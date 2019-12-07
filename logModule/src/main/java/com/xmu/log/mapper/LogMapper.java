package com.xmu.log.mapper;

import com.xmu.log.domain.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LogMapper {


    List<Log> list(String name);

    Log addLog(Log log);
}
