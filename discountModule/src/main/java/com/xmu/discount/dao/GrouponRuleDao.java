package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.Promotion;
import com.xmu.discount.mapper.GrouponRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GrouponRuleDao implements PromotionDao{

    @Autowired
    public GrouponRuleMapper grouponRuleMapper;

    /**
     * 用id找团购规则
     * @param id
     * @return
     */
    @Override
    public Promotion getPromotionById(Integer id) {
        return grouponRuleMapper.getGrouponRuleById(id);
    }

    /**
     * 查看所有的团购规则
     * @return
     */
    @Override
    public List<Promotion> getPromotionRules() {
        List<GrouponRule> grouponRules=grouponRuleMapper.getGrouponRules();
        List<Promotion> promotions=new ArrayList<Promotion>();
        for(GrouponRule grouponRule:grouponRules)
        {
            promotions.add(grouponRule);
        }
        return promotions;
    }

    /**
     * 添加团购规则
     * @param promotionRule
     * @return
     */
    @Override
    public int addPromotionRule(Promotion promotionRule) {
        return grouponRuleMapper.addGrouponRule((GrouponRule)promotionRule);
    }

    /**
     * 查询一个商品的团购规则
     * @param goodsId
     * @return
     */
    @Override
    public List<Promotion> listPromotionByGoodsId(Integer goodsId) {
        List<Promotion>promotions=new ArrayList<>();
        List<GrouponRule> grouponRules=grouponRuleMapper.listGrouponRuleByGoodsId(goodsId);
        promotions.addAll(grouponRules);
        return promotions;
    }

    /**
     * 修改团购规则
     * @return
     */
    @Override
    public int updatePromotionRuleById(Promotion promotionRule) {
        return 0;
    }







}
