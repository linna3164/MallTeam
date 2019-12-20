package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.exception.*;

import java.util.List;

/**
 * @author  ln
 * @Description 促销活动相关的dao
 * @ate 2019/12/10
 */
public interface PromotionRuleDao {


    public void  setDisable(PromotionRule promotionRule) throws PresaleRuleSetDisableFailException, CouponRuleSetDisableFailException, GrouponRuleUpdateFailException;

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
    List<? extends PromotionRule> listPromotions();
    /**
     * 添加促销规则
     * @param promotionRule
     * @return
     */
    boolean addPromotionRule(PromotionRule promotionRule) throws PresaleRuleAddFailException, CouponRuleAddFailException, GrouponRuleAddFailException;

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
    boolean updatePromotionRuleById(PromotionRule promotionRule) throws PresaleRuleUpdateFailException, CouponRuleUpdateFailException, GrouponRuleUpdateFailException;


    /**
     * 删除促销活动
     * @param id
     */
    boolean deletePromotionRuleById(Integer id) throws PresaleRuleDeleteFailException, CouponRuleDeleteFailException, GrouponRuleDeleteFailException;
}
