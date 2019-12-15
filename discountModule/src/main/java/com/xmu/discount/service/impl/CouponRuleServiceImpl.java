package com.xmu.discount.service.impl;

import com.xmu.discount.domain.discount.PromotionRule;

public class CouponRuleServiceImpl extends PromotionServiceImpl {


    /**
     * 活动实效后的行为
     * @param promotionRule
     */
    @Override
    public void toDoSomthing(PromotionRule promotionRule) {
        //把所有已经领取过但是用户未使用的优惠券设置为实效
        //优惠券规则的id
        Integer id=promotionRule.getId();
        //从数据库中找到优惠券（couponRuleId,未使用，）---》已失效

    }
}
