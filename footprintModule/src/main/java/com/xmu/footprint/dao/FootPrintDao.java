package com.xmu.footprint.dao;

import com.xmu.footprint.domain.FootPrint;
import com.xmu.footprint.mapper.FootPrintMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FootPrintDao {
    @Autowired
    FootPrintMapper footPrintMapper;
    /*
     *新增足迹
     *@param userId
     * @return 新增条数
     */
    public  int addFootPrint(FootPrint footPrint){
        return footPrintMapper.addFootPrint(footPrint);
    }

    /*
     *删除足迹
     *@param footPrintid
     * @return 删除行数 1
     */
    public int deleteFootPrint(FootPrint footPrint){
        return footPrintMapper.deleteFootPrint(footPrint);
    }

    /*
     *获得用户足迹列表
     *@param
     *@return List<>
     */
    public List<FootPrint> findListByUserId(Integer id){
        return footPrintMapper.findListByUserId(id);
    }

    /*
     *根据id获得Footprint
     * @oaram footprintId
     * @return FootPrint
     */
    public FootPrint findFootPrintById(Integer id){
        return footPrintMapper.findFootPrintById(id);
    }
}
