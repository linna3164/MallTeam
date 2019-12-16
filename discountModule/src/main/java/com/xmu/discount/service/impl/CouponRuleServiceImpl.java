package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.dao.PromotionRuleDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.exception.SeriousException;
import com.xmu.discount.exception.UpdatedDataFailedException;
import com.xmu.discount.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public void toDoSomthingAfterDisable(PromotionRule promotionRule) throws SeriousException {
        //找到该优惠券规则未使用的优惠券
        List<Coupon> couponList=couponService.listCouponByCouponRuleIdAndStatus((CouponRule) promotionRule,Coupon.Status.NOT_USED);

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
     * @throws UpdatedDataFailedException
     */
    @Override
    public PromotionRule addPromotion(PromotionRule promotionRule) throws UpdatedDataFailedException, SeriousException {
        if(promotionRule.isOkToAdd(null)){
            //调用DAO层的add方法。
            String daoName=getDaoClassName(promotionRule);
            ((PromotionRuleDao) SpringContextUtil.getBean(daoName)).addPromotionRule(promotionRule);
        }
        return super.addPromotion(promotionRule);
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
                newCoupon.setStatusCode(3);//TODO:不知道失效的code标准组更新了没
                //更新状态为已失效
                couponDao.updateCouponById(newCoupon);
            }
        }
    }



    /**
     * 管理员查看所有优惠券规则
     * @return
     */
    public List<CouponRule> listCouponRule(){
        return couponRuleDao.listCouponRule();
    }



}
