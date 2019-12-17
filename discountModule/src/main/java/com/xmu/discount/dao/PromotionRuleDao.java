package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.exception.PromotionNotFoundException;
import com.xmu.discount.exception.SeriousException;
import com.xmu.discount.exception.UpdatedDataFailedException;

import java.util.List;

public interface PromotionRuleDao {


    public void  setDisable(PromotionRule promotionRule) throws UpdatedDataFailedException;

    /**
     * 用id找促销规则
     * @param id
     * @return
     */
    PromotionRule getPromotionRuleById(Integer id) throws PromotionNotFoundException;

    /**
     * 查看所有促销规则
     * @return
     */
    List<? extends PromotionRule> listPromotions();
    /**
     * 添加促销规则
     * @param promotionRule
     * @return
     */
    int addPromotionRule(PromotionRule promotionRule) throws SeriousException;

    /**
     * 查询一个商品的促销规则
     * @param goodsId
     * @return
     */
    List<? extends PromotionRule> listPromotionRuleByGoodsId(Integer goodsId);

    /**
     * 修改促销规则
     * @return
     */
    boolean updatePromotionRuleById(PromotionRule promotionRule) throws UpdatedDataFailedException;


    /**
     * 删除促销活动
     * @param id
     */
    void deletePromotionRuleById(Integer id) throws UpdatedDataFailedException;
}
