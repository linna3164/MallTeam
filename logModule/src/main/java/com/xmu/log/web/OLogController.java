package com.xmu.log.web;

import com.github.pagehelper.PageHelper;
import com.xmu.log.domain.LogDto;
import com.xmu.log.service.LogService;
import com.xmu.log.standard.Log;
import org.springframework.beans.factory.annotation.Autowired;

public class OLogController implements com.xmu.log.standard.LogController {

    @Autowired
    LogService logService;


    @Override
    public Object list(String name, Integer page, Integer limit, String sort, String order) {
        PageHelper.startPage(page,limit); //pageNum=2, pageSize=3 ,表示每页的大小为3，查询第二页的结果
        String columnOrder=contactX(sort,order);
        PageHelper.orderBy(columnOrder); //进行分页结果的排序，name为字段名，排序规则DESC/ASC
        String st="d";
        return null;
    }

    public String contactX(String sort,String order){
        return  sort.concat(" ").concat(order);
    }

    public void addLog(Log log){
        LogDto oLog=new LogDto(log);
        logService.addLog(oLog);
    }
}
