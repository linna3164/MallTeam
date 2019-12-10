package com.xmu.footprint.service;

import com.xmu.footprint.domain.FootPrint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FootPrintService {
    /*
     *新增足迹
     *@param userId
     * @return 新增条数
     */
     int addFootPrint(FootPrint footPrint);

    /*
     *删除足迹
     *@param footPrintid
     * @return 删除行数 1
     */
    int deleteFootPrint(FootPrint footPrint);

    /*
     *获得用户足迹列表
     *@param
     *@return List<>
     */
    List<FootPrint> findListByUserId(Integer id);

    /*
    *根据id获得Footprint
    * @oaram footprintId
    * @return FootPrint
     */
    FootPrint findFootPrintById(Integer id);
}
