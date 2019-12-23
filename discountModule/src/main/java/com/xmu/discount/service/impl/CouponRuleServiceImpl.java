package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.dao.PromotionRuleDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.discount.PromotionRule;

import com.xmu.discount.exception.*;
import com.xmu.discount.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  ln
 * @Description 优惠券规则活动相关的service
 * @ate 2019/12/10
 */
@Service
public class CouponRuleServiceImpl extends PromotionServiceImpl {


    @Autowired
    CouponRuleDao couponRuleDao;

    @Autowired
    CouponDao couponDao;

    @Autowired
    CouponServiceImpl couponService;

    /**
     * 管理员设置优惠券规则失效后把用户未使用的优惠券设置为失效
     * @param promotionRule
     */
    @Override
    public void toDoSomthingAfterDisable(PromotionRule promotionRule) throws PromotionRuleSetDisableException {
        //找到该优惠券规则未使用的优惠券
        List<Coupon> couponList= null;
        try {
            couponList = couponService.listCouponByCouponRuleIdAndStatus((CouponRule) promotionRule, Coupon.Status.NOT_USED);
        } catch (CouponRuleUnValidException e) {
            throw new PromotionRuleSetDisableException();
        }

        for(Coupon coupon:couponList){
            Coupon cou=new Coupon(coupon.getId());
            cou.setStatusCode(2);
            couponDao.updateCouponById(coupon);
        }

    }

    /**
     * 管理员新增优惠券规则
     * @param promotionRule
     * @return
     */

    @Override
    public PromotionRule addPromotion(PromotionRule promotionRule) throws CouponRuleAddFailException, PresaleRuleAddFailException, GrouponRuleAddFailException {
        if(promotionRule.beOkToAdd(null)){
            //调用DAO层的add方法。
            String daoName=getDaoClassName(promotionRule);
            ((PromotionRuleDao) SpringContextUtil.getBean(daoName)).addPromotionRule(promotionRule);
        }
        return promotionRule;
    }

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
                //不知道失效的code标准组更新了没
                newCoupon.setStatusCode(3);
                //更新状态为已失效
                couponDao.updateCouponById(newCoupon);
            }
        }
    }


//
//    /**
//     * 管理员查看所有优惠券规则
//     * @return
//     */
//    public List<CouponRule> listCouponRule(){
//        return couponRuleDao.listCouponRule();
//    }



}
