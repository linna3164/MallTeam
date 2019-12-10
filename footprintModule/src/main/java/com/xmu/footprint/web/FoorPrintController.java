package com.xmu.footprint.web;


import com.xmu.footprint.domain.FootPrint;
import com.xmu.footprint.service.serviceImpl.FootPrintServiceImpl;
import com.xmu.footprint.standard.FootprintController;
import com.xmu.footprint.util.Common;
import com.xmu.footprint.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoorPrintController implements FootprintController {
    @Autowired
    FootPrintServiceImpl footPrintService;

    /*
     *新增足迹
     *@param userId
     * @return 新增条数
     */
    @PostMapping("/footprint")
    public Object addFootPrint(FootPrint footPrint) {
        footPrint.setGmtCreate(Common.DEFAULT_TIME);
        int status = footPrintService.addFootPrint(footPrint);
        if(status ==0)
        {
            Object retObj = ResponseUtil.fail();
            return retObj;
        }
        else {
            Object retObj = ResponseUtil.ok();
            return retObj;
        }

    }

    /**
     * 删除用户足迹
     *
     * @param id 用户ID
     * @return 删除行数 1
     */
    @Override
    @DeleteMapping("/footprints/{id}")
    public Object delete(@PathVariable Integer id) {
        FootPrint footPrint = footPrintService.findFootPrintById(id);
        Object retObj = null;
        if(footPrint == null)
        {
            retObj = ResponseUtil.fail();
            return retObj;
        }
        int status = footPrintService.deleteFootPrint(footPrint);
        if(status ==0)
        {
            retObj = ResponseUtil.fail();
            return retObj;
        }
        else {
            retObj = ResponseUtil.ok();
            return retObj;
        }
    }

    /**
     * 用户足迹列表
     *
     * @param page 分页页数
     * @param limit 分页大小
     * @return 用户足迹列表
     */
    @Override
    @GetMapping("/footprint")
    public Object findListByUserId(Integer id) {
        List<FootPrint> list = footPrintService.findListByUserId(id);
        Object retObj = null;
        if(list.isEmpty() == true)
        {
            retObj = ResponseUtil.fail();
            return retObj;
        }
        else
        {
            retObj = ResponseUtil.ok(list);
            return retObj;
        }

    }
}
