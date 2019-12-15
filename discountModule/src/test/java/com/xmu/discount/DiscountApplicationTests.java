package com.xmu.discount;

import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.service.impl.CouponRuleServiceImpl;
import com.xmu.discount.service.impl.CouponServiceImpl;
import com.xmu.discount.service.impl.PromotionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DiscountApplicationTests {

    @Autowired
    @Qualifier("promotionServiceImpl")
    private PromotionServiceImpl discountService;

    @Autowired
    private CouponServiceImpl couponService;

    @Autowired
    private CouponRuleServiceImpl couponRuleService;

    @Autowired
    @Qualifier("grouponServiceImpl")
    private PromotionServiceImpl grouponService;

    @Autowired
    @Qualifier("presaleServiceImpl")
    private PromotionServiceImpl presaleService;

    @Test
    void contextLoads() {
        List<Coupon> coupons=couponService.getCoupons();
        for(Coupon c:coupons) System.out.println(c);

    }

}
