package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.PresaleRuleDto;
import com.xmu.discount.domain.discount.Promotion;
import com.xmu.discount.mapper.PresaleRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PresaleRuleDao {

    @Autowired
    public  PresaleRuleMapper presaleRuleMapper;

    /**
     * 用id找预售规则
     * @param id
     * @return
     */
    public  PresaleRuleDto getPresaleRuleById(Integer id){
        return presaleRuleMapper.getPresaleRuleById(id);
    }

    /**
     * 添加预售规则
     * @param presaleRuleDto
     * @return
     */
    public  int addPresaleRuleDto(PresaleRuleDto presaleRuleDto){
        return presaleRuleMapper.addPresaleRuleDto(presaleRuleDto);
    }

    /**
     * 查询一个商品的预售规则
     * @param goodsId
     * @return
     */
    public List<Promotion> listPresaleRuleByGoodsId(Integer goodsId){
        List<Promotion>promotions=new ArrayList<>();
        List<PresaleRuleDto> presaleRuleDtos=presaleRuleMapper.listPresaleRuleByGoodsId(goodsId);
        for(Promotion promotionItem:presaleRuleDtos){
            promotions.add(promotionItem);
        }
        return promotions;
    }

    /**
     * 修改预售规则
     * @return
     */
    public int updatePresaleRuleById(PresaleRuleDto presaleRuleDto){
        return presaleRuleMapper.updatePresaleRuleById(presaleRuleDto);
    }
}
