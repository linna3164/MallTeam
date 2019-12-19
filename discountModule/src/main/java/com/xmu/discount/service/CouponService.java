package com.xmu.discount.service;

import com.xmu.discount.domain.coupon.Coupon;

import com.xmu.discount.domain.others.domain.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;


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

    List<Coupon> getCoupons();
    /**
     * 用户领取优惠券
     * @param coupon
     * @return
     */
    Coupon addCoupon(Coupon coupon);

    /**
     * 用户查找已经过期的优惠券
     * @param userId
     * @return
     */
    List<Coupon> listOverDueCouponOfUser(Integer userId);

    /**
     * 用户查找已使用的优惠券
     */
    List<Coupon> listUsedCouponOfUser(Integer userId);

    /**
     * 用户查找未使用的优惠券
     * @param userId
     * @return
     */
    List<Coupon> listUnUsedCouponOfUser(Integer userId);

    List<Coupon> listAvailableCoupons(List<CartItem> cartItems);
}
