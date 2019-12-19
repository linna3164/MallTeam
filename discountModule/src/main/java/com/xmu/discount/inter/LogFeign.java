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
@FeignClient(value = "logService")
public interface LogFeign {
    @RequestMapping(value = "/log",method = RequestMethod.POST,consumes = "application/json;charset=UTF-8")
    public Object addLog(@RequestBody Log log);
}
