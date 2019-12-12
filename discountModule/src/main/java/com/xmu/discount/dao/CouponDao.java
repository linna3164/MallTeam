package com.xmu.discount.dao;

import com.xmu.discount.domain.coupon.CouponDto;
import com.xmu.discount.domain.coupon.CouponRuleDto;
import com.xmu.discount.mapper.CouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.coupon.Coupon;
import xmu.oomall.domain.coupon.CouponRule;
import xmu.oomall.domain.coupon.CouponRulePo;
import xmu.oomall.mapper.CouponMapper;

/**
 * @Author: Ming Qiu
 * @Description: 优惠卷的DAO
 * @Date: Created in 17:02 2019/11/5
 * @Modified By:
 **/

@Repository
public class CouponDao {

    @Autowired
    private CouponMapper couponMapper;

    /**
     * 用ID获取优惠券
     * @param id 优惠券id
     * @return 优惠券，带优惠券的优惠券规则一起返回
     */
    public Coupon getCouponById(Integer id) {
        Coupon coupon = couponMapper.getCouponById(id);
        CouponRule couponRulePo = couponMapper.getCouponRuleById(coupon.getCouponRuleId());
        coupon.setCouponRule(couponRule);
        return coupon;
    }

}
