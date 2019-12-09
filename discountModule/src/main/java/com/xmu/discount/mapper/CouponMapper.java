package com.xmu.discount.mapper;

import com.xmu.discount.domain.CouponDto;
import com.xmu.discount.domain.CouponRuleDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

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


}
