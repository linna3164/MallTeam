package com.xmu.discount.service;

import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  ln
 * @Description 促销活动相关的service
 * @ate 2019/12/10
 */
@Service
public interface PromotionService {

    /**
     * 设置活动实效
     * @param promotionRule
     */
    void setUnValid(PromotionRule promotionRule);

    /**
     * 删除促销活动
     * @param promotionRule
     * @return
     */
    void deletePromotionById(PromotionRule promotionRule);

    /**
     * 修改促销活动
     * @param promotionRule
     * @return
     */
    PromotionRule updatepromotionRule(PromotionRule promotionRule);

    /**
     * 添加优惠活动
     * @param promotionRule
     * @return
     */
    PromotionRule addPromotion(PromotionRule promotionRule);

    /**
     * 根据id获取促销规则(controller 传优惠券名)
     * @param id
     * @return
     */
    PromotionRule getPromotionById(Integer id,String promotionName);


}
