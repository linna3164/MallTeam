package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.dao.CouponDao;
import xmu.oomall.domain.coupon.Coupon;
import xmu.oomall.service.CouponService;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description:优惠卷服务的实现
 * @Date: Created in 11:03 2019/11/19
 * @Modified By:
 **/
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    @Override
    public Coupon findCouponById(Integer id) {
        return couponDao.getCouponById(id);
    }


    @Override
    public Coupon addCoupon(Coupon coupon) {
        List<Coupon> coupons=couponDao.listCouponByCouponRuleIdAndUserId(coupon.getCouponRuleId(),coupon.getUserId());
        CouponRule couponRule=couponDao.getCouponRuleById(coupon.getCouponRuleId());
        //用户已经领取过了
        if(coupons.size()>0){
            //TODO:不能领取
        }
        else if()
        return null;
    }

    @Override
    public CouponRule deleteCouponRuleById(Integer id) {
        return null;
    }

    @Override
    public CouponRule addCouponRule(CouponRule couponRule) {
        return null;
    }

    @Override
    public CouponRule updateCouponRule(CouponRule couponRule) {
        return null;
    }
}
