package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.PromotionRule;

import java.util.List;

public interface PromotionRuleDao {

    /**
     * 用id找促销规则
     * @param id
     * @return
     */
    PromotionRule getPromotionRuleById(Integer id);

    /**
     * 查看所有促销规则
     * @return
     */
    List<PromotionRule> getPromotionRules();
    /**
     * 添加促销规则
     * @param promotionRule
     * @return
     */
    int addPromotionRule(PromotionRule promotionRule);

    /**
     * 查询一个商品的促销规则
     * @param goodsId
     * @return
     */
    List<PromotionRule> listPromotionRuleByGoodsId(Integer goodsId);

    /**
     * 修改促销规则
     * @return
     */
    int updatePromotionRuleById(PromotionRule promotionRule);


    void deletePromotionRuleById(PromotionRule promotionRule);
}
