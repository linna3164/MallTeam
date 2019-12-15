package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.others.domain.CartItem;
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

    public Coupon findCouponById(Integer id) {

        return couponDao.getCouponById(id);
    }

    public List<Coupon> getCoupons() {
        return couponDao.listCoupons();
    }


    /**
     * 用户领取优惠券
     * @param coupon
     * @return
     */
    public Coupon addCoupon(Coupon coupon) {
        List<Coupon> coupons=couponDao.listCouponByCouponRuleIdAndUserId(coupon.getCouponRuleId(),coupon.getUserId());
        CouponRule couponRule=coupon.getCouponRule();
        if(couponRule==null){
            //TODO:优惠券规则不存在,报错
        }
        coupon.setCouponRule(couponRule);
        //用户还没领取过
        if(coupons.size()>0){
            //优惠券规则可被领取
            if (couponRule.canGet()){
                couponDao.addCoupon(coupon);
                return coupon;
            }
        }
        return null;
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
    public List<Coupon> listAvailableCoupons(List<CartItem> cartItems) {
        return null;
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
