package com.xmu.discount.service;

import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiscountService {


    void deletePromotionById(PromotionRule promotion);


    Payment getPayment(Order order);

    List<PromotionRule> listProimotionByGoodsId(Integer goodsId);

    /**
     * 判断这个促销活动是否可添加
     * @param promotion
     * @return
     */
    Boolean isValid(PromotionRule promotion);

    /**
     * 获得商品当前的促销活动
     * @param goodsId
     * @return
     */
    PromotionRule getCurrentPromotionByGoodsId(Integer goodsId);

    /**
     * 添加优惠活动
     * @param promotion
     * @return
     */
    PromotionRule addPromotion(PromotionRule promotion);

     /** 用id找团购规则
     * @param id
     * @return
      */
    PromotionRule getGrouponRuleById(Integer id);


    /**
     * 添加团购规则
     * @param grouponRule
     * @return
     */
    PromotionRule addGrouponRule(GrouponRule grouponRule);

    /**
     * 查询一个商品的团购规则
     * @param goodsId
     * @return
     */
    List<PromotionRule> listGrouponRuleByGoodsId(Integer goodsId);

    /**
     * 获得所有团购规则
     * @return
     */
    List<PromotionRule> getGrouponRules();

    /**
     * 修改团购规则
     * @return
     */
    PromotionRule updateGrouponRuleById(GrouponRule grouponRule);

    PromotionRule deleteGroupRuleById(Integer id);

    /**
     * 用id找预售规则
     * @param id
     * @return
     */
    PromotionRule getPresaleRuleById(Integer id);

    /**
     * 添加预售规则
     * @param int
     * @return
     */
    PromotionRule addPresaleRule(PresaleRule presaleRule);

    /**
     * 查询一个商品的预售规则
     * @param goodsId
     * @return
     */
    List<PromotionRule> listPresaleRuleByGoodsId(Integer goodsId);

    /**
     * 修改预售规则
     * @return
     */
    PromotionRule updatePresaleRuleById(PresaleRule presaleRule);

    PromotionRule deletePresaleRuleById(Integer id);

}
