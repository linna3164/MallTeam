package com.xmu.discount;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRulePo;
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

//    @Autowired
//    @Qualifier("promotionServiceImpl")
//    private PromotionServiceImpl discountService;
//
//    @Autowired
//    private CouponServiceImpl couponService;
//
//    @Autowired
//    private CouponRuleServiceImpl couponRuleService;
//
//    @Autowired
//    @Qualifier("grouponServiceImpl")
//    private PromotionServiceImpl grouponService;
//
//    @Autowired
//    @Qualifier("presaleServiceImpl")
//    private PromotionServiceImpl presaleService;


    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CouponRuleDao couponRuleDao;
    @Test
    void contextLoads() {
        List<Coupon> coupons=couponDao.listCoupons();
        for(Coupon c:coupons) System.out.println(c);

        List<CouponRule> couponRules=couponRuleDao.listCouponRule();
        for(CouponRule c:couponRules) System.out.println(c);
    }

}
