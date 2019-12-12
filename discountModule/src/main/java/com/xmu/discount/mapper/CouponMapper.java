package com.xmu.discount.mapper;

import com.xmu.discount.domain.coupon.CouponDto;
import com.xmu.discount.domain.coupon.CouponRuleDto;
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
     * @param id 一张优惠卷id
     * @return 优惠卷
     */
    CouponDto getCouponById(Integer id);

    /**
     * 用id找优惠卷规则
     * @param id 优惠卷规则id
     * @return 优惠
     */
    CouponRuleDto getCouponRuleById(Integer id);

    /**
     * 获得所有优惠券规则
     * @return
     */
    List<CouponRuleDto> getCouponRules();

    /**
     * 获得所有的优惠券列表
     * @return
     */
    List<CouponDto> getCoupons();

    /**
     * 新增优惠券
     * @param couponDto
     * @return
     */
    int addCoupon(CouponDto couponDto);


    /**
     * 新增优惠券规则
     * @param couponRuleDto
     * @return
     */
    int addCouponRule(CouponRuleDto couponRuleDto);

    /**
     * 修改优惠券规则
     * @param couponRuleDto
     * @return
     */
    int updateCouponRuleById(CouponRuleDto couponRuleDto);


    /**
     * 修改优惠券
     * @param couponDto
     * @return
     */
    int updateCouponById(CouponDto couponDto);


    /**
     * 根据优惠券规则id和用户id查找优惠券
     * @param couponRuleDtoId
     * @param userId
     * @return
     */
    List<CouponDto> listCouponByCouponRuleId(Integer couponRuleDtoId, Integer userId);
}
