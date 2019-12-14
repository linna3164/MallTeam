package com.xmu.discount.mapper;

import com.xmu.discount.domain.coupon.CouponRule;

import java.util.List;

public interface CouponRuleMapper {
    /**
     * 用id找优惠卷规则
     *
     * @param id 优惠卷规则id
     * @return 优惠
     */
    CouponRule getCouponRuleById(Integer id);

    /**
     * 查看所有类型的优惠券
     *
     * @return
     */
    List<CouponRule> getCouponRules();

    /**
     * 新增优惠券规则
     *
     * @param couponRule
     * @return
     */
    int addCouponRule(CouponRule couponRule);

    /**
     * 修改优惠券规则
     *
     * @param couponRule
     * @return
     */
    int updateCouponRuleById(CouponRule couponRule);
}
