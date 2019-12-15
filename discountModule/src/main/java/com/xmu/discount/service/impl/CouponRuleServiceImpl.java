package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.discount.PromotionRule;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CouponRuleServiceImpl extends PromotionServiceImpl {


    @Autowired
    CouponDao couponDao;

    /**
     * 优惠券活动实效后的行为
     * @param promotionRule
     */
    @Override
    public void toDoSomthing(PromotionRule promotionRule) {
        //把所有已经领取过但是用户未使用的优惠券设置为实效
        //优惠券规则的id
        Integer couponRuleId=promotionRule.getId();
        //找到优惠券（couponRuleId,未使用，）---》已失效
        List<Coupon> couponList=couponDao.listCouponByCouponRuleId(couponRuleId);
        for(Coupon coupon:couponList){
            //未使用的优惠券 置为失效
            if(coupon.getStatus().equals(Coupon.Status.NOT_USED)){
                Coupon newCoupon=new Coupon();
                newCoupon.setId(coupon.getId());
                newCoupon.setStatusCode(3);//TODO:不知道失效的code标准组更新了没
                //更新状态为已失效
                couponDao.updateCouponById(newCoupon);
            }
        }
    }
}
