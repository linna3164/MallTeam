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
@Component
@Mapper
public interface CouponMapper {
    /**
     * 用id找一张优惠卷
     *
     * @param id 一张优惠卷id
     * @return 优惠卷
     */
    Coupon getCouponById(Integer id);

    /**
     * 用id找优惠卷规则
     *
     * @param id 优惠卷规则id
     * @return 优惠
     */
    CouponRule getCouponRuleById(Integer id);

    /**
     * 查看所有被领取的优惠券
     *
     * @return
     */
    List<Coupon> getCoupons();

    /**
     * 查看所有类型的优惠券
     *
     * @return
     */
    List<CouponRule> getCouponRules();

    /**
     * 新增优惠券
     *
     * @param coupon
     * @return
     */
    int addCoupon(Coupon coupon);

    /**
     * 新增优惠券规则
     *
     * @param couponRule
     * @return
     */
    int addCouponRule(CouponRule couponRule);

    /**
     * 修改优惠券规则
     *
     * @param couponRule
     * @return
     */
    int updateCouponRuleById(CouponRule couponRule);

    /**
     * 修改优惠券
     *
     * @param coupon
     * @return
     */
    int updateCouponById(Coupon coupon);


    /**
     * 根据优惠券规则id和用户id查找优惠券
     *
     * @param couponRuleId
     * @param userId
     * @return
     */
    List<Coupon> listCouponByCouponRuleId(Integer couponRuleId, Integer userId);


    /**
     * 获取用户的所有优惠券
     * @param userId
     * @return
     */
    List<Coupon> listCouponOfUser(Integer userId);


}
