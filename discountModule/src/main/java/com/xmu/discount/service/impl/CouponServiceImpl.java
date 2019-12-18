package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.CartItem;
import com.xmu.discount.exception.*;
import com.xmu.discount.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description:优惠卷服务的实现
 * @Date: Created in 11:03 2019/11/19
 * @Modified By:
 **/
@Service
public class CouponServiceImpl {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CouponRuleDao couponRuleDao;

    public Coupon findCouponById(Integer id) {

        return couponDao.getCouponById(id);
    }

    /**
     * 查看不同状态的优惠券（未使用、已使用、已失效、已过期、）
     * (0,1,2,3)
     *
     * @param type
     * @return
     */
    public List<Coupon> getCoupons(Integer type) {
        List<Coupon> coupons = couponDao.listCoupons();
        List<Coupon> res = new ArrayList<Coupon>();
        if (type == 0)
            for (Coupon c : coupons) {
                if (c.getStatusCode() == 0) res.add(c);
            }
        else if (type == 1) {
            for (Coupon c : coupons) {
                if (c.getStatusCode() == 1) res.add(c);
            }
        } else if (type == 2) {
            for (Coupon c : coupons) {
                if (c.getStatusCode() == 2) res.add(c);
            }
        } else if (type == 3) {
            for (Coupon c : coupons) {
                if (c.getEndTime().isBefore(LocalDateTime.now())) res.add(c);
            }
        } else res.addAll(coupons);
        return res;

    }

    /**
     * 获取某种优惠券规则的全部优惠券
     *
     * @param couponRule
     * @return
     */
    public List<Coupon> listCouponByCouponRuleId(CouponRule couponRule) throws SeriousException {
        if (couponRule == null) {
            throw new SeriousException();
        } else {
            return couponDao.listCouponByCouponRuleId(couponRule.getId());
        }
    }

    /**
     * 获取某种优惠券规则的某种类型的优惠券
     *
     * @param couponRule
     * @return
     */
    public List<Coupon> listCouponByCouponRuleIdAndStatus(CouponRule couponRule, Coupon.Status status) throws SeriousException {
        List<Coupon> coupons = this.listCouponByCouponRuleId(couponRule);

        List<Coupon> res = new ArrayList<>();
        for (Coupon coupon : coupons) {
            if (coupon.getStatus().equals(status)) {
                res.add(coupon);
            }
        }
        return res;
    }


    /**
     * 用户领取优惠券
     *
     * @param coupon
     * @return
     */
    public Coupon addCoupon(Coupon coupon) throws CouponNotFoundException, UnsupportException, CouponRuleNotFoundException, UpdatedDataFailedException, PromotionNotFoundException {
        List<Coupon> coupons = couponDao.listCouponByCouponRuleIdAndUserId(coupon.getCouponRuleId(), coupon.getUserId());
        CouponRule couponRule = (CouponRule) couponRuleDao.getPromotionRuleById(coupon.getCouponRuleId());
        System.out.println(couponRule);
        //找不到couponRule
        if (couponRule == null) {
            throw new CouponRuleNotFoundException();
        }
        else {
            //用户还没领取过
            if (coupons.size() == 0) {
                couponDao.addCoupon(coupon);
                couponRuleDao.updatePromotionRuleById(couponRule);
                return coupon;
            }
            //用户领取过了
            else {
                return null;
            }
        }

    }




    /**
     * 用户获取失效的优惠券
     * @param userId
     * @return
     */
    public List<Coupon> listDisabledCouponOfUser(Integer userId) {

        return this.listCouponOfUserByStatus(userId, Coupon.Status.DISABLED);
    }



    /**
     * 用户获取过期的优惠券
     * @param userId
     * @return
     */
    public List<Coupon> listOverDueCouponOfUser(Integer userId) {

        return this.listCouponOfUserByStatus(userId, Coupon.Status.EXPIRED);
    }

    /**
     * 用户获取已使用的优惠券
     * @param userId
     * @return
     */
    public List<Coupon> listUsedCouponOfUser(Integer userId) {

        return this.listCouponOfUserByStatus(userId, Coupon.Status.USED);
    }

    /**
     * 用户获取未使用的优惠券
     * @param userId
     * @return
     */
    public List<Coupon> listUnUsedCouponOfUser(Integer userId) {
        return this.listCouponOfUserByStatus(userId, Coupon.Status.NOT_USED);
    }

    /**
     * 查看订单可用优惠券
     * @param cartItems
     * @return
     */
    public List<Coupon> listAvailableCoupons(List<CartItem> cartItems,Integer userId ) throws PromotionNotFoundException {
        List<Coupon>  coupons=couponDao.listCouponOfUser(userId);
        List<Coupon> coupons1=new ArrayList<Coupon>();
        for(Coupon c:coupons)
        {
            c.setCouponRule((CouponRule) couponRuleDao.getPromotionRuleById(c.getCouponRuleId()));
            if(c.isOkToUse(cartItems))
            {
                coupons1.add(c);
            }

        }
        return coupons1;
    }

    /**
     * 获得用户某种状态的优惠券
     * @param userId 用户ID
     * @param status 优惠券状态
     * @return  带CouponRule对象
     */
    public List<Coupon> listCouponOfUserByStatus(Integer userId, Coupon.Status status){
        List<Coupon> coupons=couponDao.listCouponOfUser(userId);
        List<Coupon> res=new ArrayList<>();
        for(Coupon coupon:coupons){
            if(coupon.getStatus().equals(status)){
                res.add(coupon);
            }


        }
        return res;
    }


//    public List<Coupon> listCouponByCou

    /**
     * 获得此用户购物车所有可用优惠券
     * @param userId
     * @param cartItemList
     * @return
     */
    public List<Coupon> listCouponOfCartItems(Integer userId, List<CartItem> cartItemList){
        List<Coupon> couponList=couponDao.listCouponOfUser(userId);
        List<Coupon> res=new ArrayList<Coupon>();
        for(Coupon coupon:couponList){
            if(coupon.isOkToUse(cartItemList)){
                res.add(coupon);
            }
        }
        return res;
    }


}
