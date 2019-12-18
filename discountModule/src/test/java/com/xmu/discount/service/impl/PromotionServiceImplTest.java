package com.xmu.discount.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.tools.javac.util.Log;
import com.xmu.discount.DiscountApplication;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.discount.*;
import com.xmu.discount.exception.PromotionNotFoundException;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.exception.SeriousException;
import com.xmu.discount.exception.UpdatedDataFailedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import rx.BackpressureOverflow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest(classes = DiscountApplication.class)
class PromotionServiceImplTest {


//    @Autowired
//    @Qualifier("promotionServiceImpl")
//    private PromotionServiceImpl promotionService;

//    @Autowired
//    @Qualifier("grouponServiceImpl")
//    private GrouponServiceImpl grouponService;
//
//    @Autowired
//    @Qualifier("presaleServiceImpl")
//    private PresaleServiceImpl presaleService;
//
//     @Autowired
//     @Qualifier("couponRuleServiceImpl")
//     private PromotionServiceImpl couponRuleService;
//
//
//     @Test
//     void couponRuleService() throws PromotionNotFoundException, UpdatedDataFailedException {
//          List<CouponRule> couponRules= (List<CouponRule>) couponRuleService.listPromotionRuleOfType("couponRule");
//          for(CouponRule c:couponRules)
//              System.out.println(c);
//          CouponRule couponRule= (CouponRule) couponRuleService.getPromotionById(1,"couponRule");
//
//           couponRule.setBrief("改了");
//
//           couponRuleService.updatepromotionRule((PromotionRule) couponRule);
//           System.out.println(couponRuleService.getPromotionById(1,"couponRule"));
//     }
//
//    @Test
//    void addPromotion1() throws UpdatedDataFailedException, SeriousException {
//        GrouponRule grouponRule=new GrouponRule();
//        GrouponRulePo grouponRulePo=new GrouponRulePo();
//
//        grouponRule.setRealObj(grouponRulePo);
//
//        grouponRule.setGoodsId(1);
//        grouponRule.setStartTime(LocalDateTime.now().plusDays(1));
//        grouponRule.setEndTime(LocalDateTime.now().plusDays(10));
//
//        grouponService.addPromotion(grouponRule);
//
//
//
//    }

//    @Test
//    void addPromotion2() throws UpdatedDataFailedException, SeriousException {
//        GrouponRule grouponRule=new GrouponRule();
//        GrouponRulePo grouponRulePo=new GrouponRulePo();
//
//        grouponRule.setRealObj(grouponRulePo);
//
//        grouponRule.setGoodsId(1);
//        grouponRule.setStartTime(LocalDateTime.now().plusDays(1));
//        grouponRule.setEndTime(LocalDateTime.now().plusDays(10));
//
//        GrouponRuleStrategy strategy=new GrouponRuleStrategy(10,20,new BigDecimal(0.9).setScale(2, RoundingMode.HALF_UP));
//
//        List<GrouponRuleStrategy> strategyList=new ArrayList<>();
//        strategyList.add(strategy);
//
//        grouponRule.setStrategyList(strategyList);
//
//        assertEquals(grouponRule.getRealObj().getGrouponLevelStrategy(),"nullpp");
//
//        grouponService.addPromotion(grouponRule);
//    }


//    /**
//     * 添加预售规则
//     */
//    @Test
//    void addPromotion(){
//        PromotionRule promotionRule
//    }

    @Test
    void getPromotionById() {


    }

    @Test
    void deletePromotionById() {

    }

    @Test
    void updatepromotionRule() {
    }

    @Test
    void getDaoClassName() {
    }

    @Test
    void getPayment() {
        String string="{\"strategy\":[{\"lowerBound\":10,\"upperBound\":20,\"discountRate\":0.90}]}";
        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(string).getAsJsonObject();
        JsonArray messageArray = jo.get("strategy").getAsJsonArray();
//        Object object=JSON.parse(messageArray);
//        System.out.println(object);
        List<GrouponRuleStrategy> strategies=JSON.parseArray(messageArray.toString(),GrouponRuleStrategy.class);

        System.out.println(strategies.get(0).getLowerBound());


    }

    @Test
    void listProimotionByGoodsId() {
    }

    @Test
    void listCurrentPromotionByGoodsId() {
    }

    @Test
    void getCurrentPromotionByGoodsId() {
    }



    @Test
    void listPromotionRuleOfType() {
    }

    @Test
    void listPromotionRuleOfTypeInprocessWithGoods() {
    }

    @Test
    void listPromotionRuleOfTypeWithGoods() {
    }

    @Test
    void setDisabled() {
    }

    @Test
    void getPromotionById1() {
    }

    @Test
    void deletePromotionById1() {
    }

    @Test
    void updatepromotionRule1() {
    }

    @Test
    void getDaoClassName1() {
    }

    @Test
    void getPayment1() {
    }

    @Test
    void listProimotionByGoodsId1() {
    }

    @Test
    void listCurrentPromotionByGoodsId1() {
    }


}