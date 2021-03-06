package com.xmu.discount.mapper;

import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description:优惠卷Mapper接口
 * @Date: Created in 11:03 2019/11/19
 * @Modified By:
 **/
@Mapper
@Component
public interface CouponMapper {
    /**
     * 用id找一张优惠卷
     *
     * @param id 一张优惠卷id
     * @return 优惠卷
     */
    Coupon getCouponById(Integer id);

    /**
     * 查看所有被领取的优惠券
     *
     * @return
     */
    List<Coupon> listCoupons();

    /**
     * 新增优惠券
     *
     * @param coupon
     * @return
     */
    boolean addCoupon(Coupon coupon);

    /**
     * 修改优惠券
     *
     * @param coupon
     * @return
     */
    boolean updateCouponById(Coupon coupon);


    /**
     * 根据优惠券规则id和用户id查找优惠券
     *
     * @param couponRuleId
     * @param userId
     * @return
     */
    List<Coupon> listCouponByCouponRuleIdAndUserId(Integer couponRuleId, Integer userId);


    /**
     * 获取用户的所有优惠券
     * @param userId
     * @return
     */
    List<Coupon> listCouponOfUser(Integer userId);


    /**
     * 获取优惠券规则下的所有优惠券
     * @param couponRuleId
     * @return
     */
    List<Coupon> listCouponByCouponRuleId(Integer couponRuleId);

}
