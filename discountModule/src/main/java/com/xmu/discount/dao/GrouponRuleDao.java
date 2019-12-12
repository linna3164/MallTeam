package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.GrouponRuleDto;
import com.xmu.discount.domain.discount.Promotion;
import com.xmu.discount.mapper.GrouponRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GrouponRuleDao {

    @Autowired
    public GrouponRuleMapper grouponRuleMapper;

    /**
     * 用id找团购规则
     * @param id
     * @return
     */
    public Promotion getGrouponRuleById(Integer id){
        return grouponRuleMapper.getGrouponRuleById(id);
    }

    /**
     * 添加团购规则
     * @param grouponRuleDto
     * @return
     */
    public int addGrouponRuleDto(GrouponRuleDto grouponRuleDto){
        return grouponRuleMapper.addGrouponRuleDto(grouponRuleDto);
    }

    /**
     * 查询一个商品的团购规则
     * @param goodsId
     * @return
     */
    public List<Promotion> listGrouponRuleByGoodsId(Integer goodsId){
        List<Promotion>promotions=new ArrayList<>();
        List<GrouponRuleDto> grouponRuleDtos=grouponRuleMapper.listGrouponRuleByGoodsId(goodsId);
        for(Promotion promotionItem:grouponRuleDtos){
            promotions.add(promotionItem);
        }
        return promotions;
    }

    /**
     * 修改团购规则
     * @return
     */
    public int updateGrouponRuleById(GrouponRuleDto grouponRuleDto){
        return grouponRuleMapper.updateGrouponRuleById(grouponRuleDto);
    }
}
