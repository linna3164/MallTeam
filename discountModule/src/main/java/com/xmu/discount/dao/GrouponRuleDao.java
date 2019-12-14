package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.GrouponRule;
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

    public List<Promotion> getGroupRules(){
        List<GrouponRule> grouponRules=grouponRuleMapper.getGrouponRules();
        List<Promotion> promotions=new ArrayList<Promotion>();
        for(GrouponRule g:grouponRules)
        {
            promotions.add(g);
        }
        return promotions;
    }

    /**
     * 添加团购规则
     * @param grouponRule
     * @return
     */
    public int addGrouponRule(GrouponRule grouponRule){
        return grouponRuleMapper.addGrouponRule(grouponRule);
    }

    /**
     * 查询一个商品的团购规则
     * @param goodsId
     * @return
     */
    public List<Promotion> listGrouponRuleByGoodsId(Integer goodsId){
        List<Promotion>promotions=new ArrayList<>();
        List<GrouponRule> grouponRule=grouponRuleMapper.listGrouponRuleByGoodsId(goodsId);
        for(Promotion promotionItem:grouponRule){
            promotions.add(promotionItem);
        }
        return promotions;
    }

    /**
     * 修改团购规则
     * @return
     */
    public int updateGrouponRuleById(GrouponRule grouponRule){
        return grouponRuleMapper.updateGrouponRuleById(grouponRule);
    }

    /**
     * 删除团购规则
     * @return
     */
    public int deleteGrouponRuleById(Integer id){
        GrouponRule grouponRule=new GrouponRule(id,true);
        grouponMapper.updateGrouponRuleById(gouponRule);

    }
}
