package com.xmu.discount;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRulePo;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.exception.PromotionNotFoundException;
import com.xmu.discount.exception.SeriousException;
import com.xmu.discount.exception.UpdatedDataFailedException;
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

    @Autowired
    private GrouponRuleDao grouponRuleDao;
    @Test
    void contextLoads() throws PromotionNotFoundException, UpdatedDataFailedException, SeriousException {
        List<Coupon> coupons=couponDao.listCoupons();
        for(Coupon c:coupons) System.out.println(c);


        List<PromotionRule> grouponRulePos=grouponRuleDao.listPromotions();
        for(PromotionRule p: grouponRulePos)
            System.out.println(p);
        PromotionRule promotionRule=grouponRuleDao.getPromotionRuleById(1);
        System.out.println(promotionRule);
         GrouponRule grouponRule=(GrouponRule) promotionRule;
         grouponRuleDao.deletePromotionRuleById(1);
        promotionRule = grouponRuleDao.getPromotionRuleById(1);
        System.out.println("删除了：  " + promotionRule);
         grouponRule.setBeDeleted(false);
         boolean success=grouponRuleDao.updatePromotionRuleById((PromotionRule) grouponRule);
         if(success) {
             promotionRule = grouponRuleDao.getPromotionRuleById(1);
             System.out.println("更新了：  " + promotionRule);
         }
         else System.out.println("失败了O");
         grouponRuleDao.addPromotionRule(promotionRule);

    }

}
