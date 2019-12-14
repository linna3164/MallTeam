package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.mapper.GrouponRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GrouponRuleDao implements PromotionRuleDao {

    @Autowired
    public GrouponRuleMapper grouponRuleMapper;

    /**
     * 用id找团购规则
     * @param id
     * @return
     */
    @Override
    public PromotionRule getPromotionById(Integer id) {
        return grouponRuleMapper.getGrouponRuleById(id);
    }

    /**
     * 查看所有的团购规则
     * @return
     */
    @Override
    public List<PromotionRule> getPromotionRules() {
        List<GrouponRule> grouponRules=grouponRuleMapper.getGrouponRules();
        List<PromotionRule> promotionRules=new ArrayList<PromotionRule>();
        for(GrouponRule grouponRule:grouponRules)
        {
            promotionRules.add(grouponRule);
        }
        return promotionRules;
    }

    /**
     * 添加团购规则
     * @param promotionRule
     * @return
     */
    @Override
    public int addPromotionRule(PromotionRule promotionRule) {
        return grouponRuleMapper.addGrouponRule((GrouponRule)promotionRule);
    }

    /**
     * 查询一个商品的团购规则
     * @param goodsId
     * @return
     */
    @Override
    public List<PromotionRule> listPromotionByGoodsId(Integer goodsId) {
        List<PromotionRule>promotionRules=new ArrayList<>();
        List<GrouponRule> grouponRules=grouponRuleMapper.listGrouponRuleByGoodsId(goodsId);
        promotionRules.addAll(grouponRules);
        return promotionRules;
    }

    /**
     * 修改团购规则
     * @return
     */
    @Override
    public int updatePromotionRuleById(PromotionRule promotionRule) {
        return 0;
    }


    /**
     * 删除团购规则
     * @param promotionRule
     */
    @Override
    public void deletePromotionRuleById(PromotionRule promotionRule) {

    }
}
