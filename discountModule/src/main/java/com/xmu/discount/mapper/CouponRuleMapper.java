package com.xmu.discount.mapper;

import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRulePo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CouponRuleMapper {
    /**
     * 用id找优惠卷规则
     *
     * @param id 优惠卷规则id
     * @return 优惠
     */
    CouponRulePo getCouponRuleById(Integer id);

    /**
     * 查看所有类型的优惠券规则
     *
     * @return
     */
    List<CouponRulePo> listCouponRules();

    /**
     * 新增优惠券规则
     *
     * @param couponRule
     * @return
     */
    int addCouponRule(CouponRulePo couponRule);

    /**
     * 修改优惠券规则
     *
     * @param couponRule
     * @return
     */
    int updateCouponRuleById(CouponRulePo couponRule);

}
