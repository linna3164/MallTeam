package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.discount.PromotionRule;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class CouponRuleServiceImpl {


    @Autowired
    CouponDao couponDao;

    /**
     * 优惠券活动实效后的行为
     * @param promotionRule
     */
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

    /**
     * 管理员删除优惠券规则（活动生效后不能删除和修改）
     * @param id
     * @return
     */
    @Override
    public boolean deleteCouponRuleById(Integer id) {
        CouponRule couponRule=couponDao.getCouponRuleById(id);

        //当前时间
        LocalDateTime now=LocalDateTime.now();
        if(couponRule.getBeginTime().isBefore(now)){

        }
        //判断活动是否开始
        if()
            couponDao.deleteCouponRuleById(id);
        return true;
    }

    /**
     * 管理员查看所有优惠券规则
     * @return
     */
    public List<CouponRule> listAllCoupon(){
        return getCouponRules();
    }

    /**
     * 管理员新增优惠券规则
     * @param couponRule
     * @return
     */
    @Override
    public CouponRule addCouponRule(CouponRule couponRule) {
        couponDao.addCouponRule(couponRule);
        return couponRule;
    }

    /**
     * 管理员修改优惠券规则
     * @param couponRule
     * @return
     */
    @Override
    public CouponRule updateCouponRule(CouponRule couponRule) {
        couponDao.updateCouponRuleById(couponRule);
        return couponRule;
    }
}
