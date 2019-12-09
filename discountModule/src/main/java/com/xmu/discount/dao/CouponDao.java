package com.xmu.discount.dao;

import com.xmu.discount.domain.CouponDto;
import com.xmu.discount.domain.CouponRuleDto;
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

    public CouponDto getCouponById(Integer id) {
        CouponDto coupon = couponMapper.getCouponById(id);
        CouponRuleDto couponRulePo = couponMapper.getCouponRuleById(coupon.getCouponRuleId());
        coupon.setCouponRule(couponRule);
        return coupon;
    }

}
