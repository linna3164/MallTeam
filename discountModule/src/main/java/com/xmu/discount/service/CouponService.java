package com.xmu.discount.service;

import com.xmu.discount.domain.coupon.Coupon;
import org.springframework.stereotype.Service;


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
}
