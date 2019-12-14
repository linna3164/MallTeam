package com.xmu.discount.service;

import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.Promotion;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiscountService {


    void deletePromotionById(Promotion promotion);


    Payment getPayment(Order order);

    List<Promotion> listProimotionByGoodsId(Integer goodsId);

    /**
     * 判断这个促销活动是否可添加
     * @param promotion
     * @return
     */
    Boolean isValid(Promotion promotion);

    /**
     * 获得商品当前的促销活动
     * @param goodsId
     * @return
     */
    Promotion getCurrentPromotionByGoodsId(Integer goodsId);

    /**
     * 添加优惠活动
     * @param promotion
     * @return
     */
    Promotion addPromotion(Promotion promotion);

     /** 用id找团购规则
     * @param id
     * @return
      */
    Promotion getGrouponRuleById(Integer id);


    /**
     * 添加团购规则
     * @param grouponRule
     * @return
     */
    Promotion addGrouponRule(GrouponRule grouponRule);

    /**
     * 查询一个商品的团购规则
     * @param goodsId
     * @return
     */
    List<Promotion> listGrouponRuleByGoodsId(Integer goodsId);

    /**
     * 获得所有团购规则
     * @return
     */
    List<Promotion> getGrouponRules();

    /**
     * 修改团购规则
     * @return
     */
    Promotion updateGrouponRuleById(GrouponRule grouponRule);

    Promotion deleteGroupRuleById(Integer id);

    /**
     * 用id找预售规则
     * @param id
     * @return
     */
    Promotion getPresaleRuleById(Integer id);

    /**
     * 添加预售规则
     * @param int
     * @return
     */
    Promotion addPresaleRule(PresaleRule presaleRule);

    /**
     * 查询一个商品的预售规则
     * @param goodsId
     * @return
     */
    List<Promotion> listPresaleRuleByGoodsId(Integer goodsId);

    /**
     * 修改预售规则
     * @return
     */
    Promotion updatePresaleRuleById(PresaleRule presaleRule);

    Promotion deletePresaleRuleById(Integer id);

}
