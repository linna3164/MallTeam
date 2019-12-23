package com.xmu.discount.inter;

import com.xmu.discount.domain.others.domain.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author hsx
 * @version 1.0
 * @date 2019/12/16 20:28
 */
@FeignClient(name = "logService",url = "http://101.132.152.28:3410")
public interface LogFeign {

    /**
     * 添加日志
     * @param log
     * @return
     */
    @RequestMapping(value = "/log",method = RequestMethod.POST,consumes = "application/json;charset=UTF-8")
    public Object addLog(@RequestBody Log log);
}
