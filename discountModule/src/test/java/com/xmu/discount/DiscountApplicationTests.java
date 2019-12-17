package com.xmu.discount;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRulePo;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.CartItem;
import com.xmu.discount.domain.others.domain.GoodsPo;
import com.xmu.discount.domain.others.domain.Product;
import com.xmu.discount.exception.*;
import com.xmu.discount.service.CouponService;
import com.xmu.discount.service.impl.CouponRuleServiceImpl;
import com.xmu.discount.service.impl.CouponServiceImpl;
import com.xmu.discount.service.impl.GrouponServiceImpl;
import com.xmu.discount.service.impl.PromotionServiceImpl;
import com.xmu.discount.util.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
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

    @Autowired
    private PresaleRuleDao presaleRuleDao;

    @Autowired
    private CouponServiceImpl couponService;

@Test
void hah(){
    String jsonString1="{\"goodsIds\":[1,2,3]}";
    List<Integer> goodsIds1= JacksonUtil.parseIntegerList(jsonString1, "goodsIds");
    System.out.println(goodsIds1.get(1));
    }
    @Test
    void tes() throws PromotionNotFoundException {
//           List<CartItem> cartItems=new ArrayList<CartItem>();
//           CartItem cartItem=new CartItem();
//           cartItems.add(cartItem);
//           GoodsPo goodsPo=new GoodsPo(); goodsPo.setId(2);  goodsPo.setName("hah");
//        Product product=new Product();  product.setGoodsPo(goodsPo);
//           cartItem.setProduct(product);
//           List<Coupon> coupons=couponService.listAvailableCoupons(cartItems,2);
//           for(Coupon c:coupons) System.out.println(c);
          List<Coupon> coupons=couponService.listCouponOfUserByStatus(1, Coupon.Status.NOT_USED);
          for(Coupon c:coupons) System.out.println(c);
    }


    @Test
    void contextLoads() throws PromotionNotFoundException, UpdatedDataFailedException, SeriousException, UnsupportException, CouponRuleNotFoundException, CouponNotFoundException {


         List<PromotionRule> presaleRules= (List<PromotionRule>) presaleRuleDao.listPromotions();
         for(PromotionRule p:presaleRules) System.out.println(p);
         PresaleRule presaleRule= (PresaleRule) presaleRuleDao.getPromotionRuleById(1);
         System.out.println(presaleRule);
         presaleRuleDao.deletePromotionRuleById(1);
         presaleRule.setBeDeleted(false);
         presaleRuleDao.updatePromotionRuleById(presaleRule);
         presaleRule.setGoodsId(100);
         presaleRuleDao.addPromotionRule(presaleRule);


        List<Coupon> coupons=couponDao.listCoupons();
        for(Coupon c:coupons) System.out.println(c);
        Coupon coupon=couponDao.getCouponById(1);
        System.out.println(coupon);
        int i=couponDao.deleteCouponById(1);
        if(i==0) System.out.println("删除失败");
        coupon.setBeDeleted(false);
        couponDao.updateCouponById(coupon);
        coupon.setName("新加的");
        couponDao.addCoupon(coupon);
        coupon.setBeDeleted(false);
        couponDao.updateCouponById(coupon);

        List<CouponRule> couponRules= (List<CouponRule>) couponRuleDao.listPromotions();
        for(CouponRule c:couponRules) System.out.println(c);
        CouponRule couponRule=(CouponRule) couponRuleDao.getPromotionRuleById(1);
        System.out.println(couponRule);
        couponRuleDao.deletePromotionRuleById(1);
        couponRule.setBeDeleted(false);
        couponRuleDao.updatePromotionRuleById(couponRule);
        couponRule.setBrief("new");
        couponRuleDao.addPromotionRule(couponRule);



        List<PromotionRule> grouponRulePos= (List<PromotionRule>) grouponRuleDao.listPromotions();
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
