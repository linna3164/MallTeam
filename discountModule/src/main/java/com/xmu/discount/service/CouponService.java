package com.xmu.discount.service;

import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import org.springframework.stereotype.Service;


/**
 * @Author: Ming Qiu
 * @Description: 优惠卷有关的服务
 * @Date: Created in 15:47 2019/11/5
 * @Modified By:
 **/
@Service
public interface CouponService {

    /**
     * 用id找到ACoupon对象，带对象
     * @param id
     * @return
     */
    Coupon findCouponById(Integer id);

    /**
     * 用户领取优惠券
     * @param coupon
     * @return
     */
    Coupon addCoupon(Coupon coupon);

    /**
     * 管理员删除优惠券规则--用户已经领取的优惠券要怎么办
     * @param id
     * @return
     */
    CouponRule deleteCouponRuleById(Integer id);

    /**
     * 管理员新增优惠券规则--
     * @param couponRule
     * @return
     */
    CouponRule addCouponRule(CouponRule couponRule);

    /**
     * 管理员修改优惠券规则---用户已经领取的优惠券要怎么办
     * @param couponRule
     * @return
     */
    CouponRule updateCouponRule(CouponRule couponRule);
}
