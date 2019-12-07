package com.xmu.log.standard;

//import io.swagger.annotations.ApiOperation;
import com.xmu.log.validator.Order;
import com.xmu.log.validator.Sort;
import org.apache.tomcat.util.http.ResponseUtil;

import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @Author xyt
 * @create 2019/12/4 13:00
 */

@RestController
@RequestMapping("/logs")
public interface LogController{

    @GetMapping("/")
//    @ApiOperation(value="管理员根据条件查看日志 /list")
    public Object list(String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order);

}