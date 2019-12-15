package com.xmu.footprint.service.serviceImpl;

import com.xmu.footprint.dao.FootPrintDao;
import com.xmu.footprint.domain.FootPrint;
import com.xmu.footprint.service.FootPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootPrintServiceImpl implements FootPrintService {
    @Autowired
    FootPrintDao footPrintDao;

    /*
     *新增足迹
     *@param userId
     * @return 新增条数
     */
    @Override
    public int addFootPrint(FootPrint footPrint) {
        return footPrintDao.addFootPrint(footPrint);
    }

    /*
     *删除足迹
     *@param footPrintid
     * @return 删除行数 1
     */
    @Override
    public int deleteFootPrint(FootPrint footPrint) {
        return footPrintDao.deleteFootPrint(footPrint);
    }

    /*
     *获得用户足迹列表
     *@param
     *@return List<>
     */
    @Override
    public List<FootPrint> findListByUserId(Integer id) {
        return footPrintDao.findListByUserId(id);
    }

    /*
     *根据id获得Footprint
     * @oaram footprintId
     * @return FootPrint
     */
    @Override
    public FootPrint findFootPrintById(Integer id) {
        return footPrintDao.findFootPrintById(id);
    }
}
