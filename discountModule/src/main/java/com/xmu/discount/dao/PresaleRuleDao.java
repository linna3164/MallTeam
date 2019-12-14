package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.Promotion;
import com.xmu.discount.mapper.PresaleRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PresaleRuleDao implements PromotionDao{

    @Autowired
    public  PresaleRuleMapper presaleRuleMapper;

    /**
     * 用id找预售规则
     * @param id
     * @return
     */
    @Override
    public Promotion getPromotionById(Integer id) {
        return presaleRuleMapper.getPresaleRuleById(id);
    }

    /**
     * 查看所有预售规则
     * @return
     */
    @Override
    public List<Promotion> getPromotionRules() {
        List<Promotion> promotions=new ArrayList<Promotion>();
        List<PresaleRule> presaleRules=presaleRuleMapper.getPresaleRules();
        for(PresaleRule p:presaleRules){
            promotions.add(p);
        }
        return promotions;
    }

    /**
     * 添加预售规则
     * @param promotionRule
     * @return
     */
    @Override
    public int addPromotionRule(Promotion promotionRule) {
        return presaleRuleMapper.addPresaleRule((PresaleRule) promotionRule);

    }

    /**
     * 查询一个商品的预售规则
     * @param goodsId
     * @return
     */
    @Override
    public List<Promotion> listPromotionByGoodsId(Integer goodsId) {
        List<Promotion>promotions=new ArrayList<>();
        List<PresaleRule> presaleRules=presaleRuleMapper.listPresaleRuleByGoodsId(goodsId);
        promotions.addAll(presaleRules);
        return promotions;
    }

    /**
     * 修改预售规则
     * @return
     */
    @Override
    public int updatePromotionRuleById(Promotion promotionRule) {
        return presaleRuleMapper.updatePresaleRuleById((PresaleRule) promotionRule);
    }



}
