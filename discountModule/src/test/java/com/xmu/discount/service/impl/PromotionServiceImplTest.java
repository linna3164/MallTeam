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
import com.xmu.discount.util.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import rx.BackpressureOverflow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DiscountApplication.class)
class PromotionServiceImplTest {


//    @Autowired
//    @Qualifier("promotionServiceImpl")
//    private PromotionServiceImpl promotionService;

    @Autowired
    @Qualifier("grouponServiceImpl")
    private GrouponServiceImpl grouponService;

    @Autowired
    @Qualifier("presaleServiceImpl")
    private PresaleServiceImpl presaleService;

     @Autowired
     @Qualifier("couponRuleServiceImpl")
     private PromotionServiceImpl couponRuleService;
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

    /**
     * 获得预售规则：不存在的id，通过
     * @throws PromotionNotFoundException
     */
    @Test
    void getPromotionById() throws PromotionNotFoundException {
        PromotionRule promotionRule=presaleService.getPromotionById(1,"presaleRule");
        assertEquals(null,promotionRule);
    }


    /**
     * 正常，通过
     * @throws PromotionNotFoundException
     */
    @Test
    void getPromotionById2() throws PromotionNotFoundException {

        PromotionRule promotionRule=presaleService.getPromotionById(3,"presaleRule");
        System.out.println(promotionRule);
        assertEquals(promotionRule,null);

    }

    /**
     * 正常删除，通过
     * @throws PromotionNotFoundException
     * @throws UpdatedDataFailedException
     */
    @Test
    void deletePromotionById() throws PromotionNotFoundException, UpdatedDataFailedException {
            PromotionRule promotionRule=presaleService.getPromotionById(3,"presaleRule");
            presaleService.deletePromotionById(promotionRule);
    }

//    /**
//     * 更新不存在的，
//     */
//    @Test
//    void updatepromotionRule() throws UpdatedDataFailedException {
//
//        presaleService.updatepromotionRule(null);
//    }

    /**
     * 更新存在的。
     */
    @Test
    void updatepromotionRule2() throws UpdatedDataFailedException, PromotionNotFoundException {

//        PromotionRule promotionRule=presaleService.getPromotionById(4,"presaleRule");
//        System.out.println(promotionRule);
        PresaleRule presaleRule=new PresaleRule();
        presaleRule.setId(4);
        presaleRule.setDeposit(new BigDecimal(200));

        presaleService.updatepromotionRule(presaleRule);


    }

    /**
     * 更新已经被删除的。
     */
    @Test
    void updatepromotionRule3() throws UpdatedDataFailedException, PromotionNotFoundException {

        PromotionRule promotionRule=presaleService.getPromotionById(3,"presaleRule");
        PresaleRule presaleRule=new PresaleRule();

        presaleRule.setDeposit(new BigDecimal(3000));

        presaleService.updatepromotionRule(presaleRule);

    }

    @Test
    void getDaoClassName() {
        GrouponRule grouponRule = new GrouponRule();
        GrouponRulePo grouponRulePo = new GrouponRulePo();
        String string="{\"strategy\":[{\"lowerBound\":10,\"upperBound\":20,\"discountRate\":0.90},{\"lowerBound\":100,\"upperBound\":200,\"discountRate\":0.090}]}";
        grouponRulePo.setGrouponLevelStrategy(string);
        grouponRule.setRealObj(grouponRulePo);
        System.out.println(grouponRule.getStrategyList().get(0).getDiscountRate());
        System.out.println(grouponRule.getStrategyList().get(1).getDiscountRate());

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
        System.out.println(strategies);
        Map<String, Object> jsonObj = new HashMap<String, Object>(2);
        jsonObj.put("strategy", strategies);
        String jsonString = JacksonUtil.toJson(jsonObj);
        System.out.println(jsonString);
//        System.out.println(strategies.get(0).getLowerBound());


    }




    @Test
    void addPresaleRule() throws UpdatedDataFailedException, SeriousException {

        PresaleRule presaleRule=new PresaleRule();
        presaleRule.setDeposit(new BigDecimal(10));
        presaleRule.setFinalPayment(new BigDecimal(100));
        presaleRule.setStartTime(LocalDateTime.now().plusDays(1));
        presaleRule.setAdEndTime(LocalDateTime.now().plusDays(2));
        presaleRule.setFinalStartTime(LocalDateTime.now().plusDays(3));
        presaleRule.setEndTime(LocalDateTime.now().plusDays(4));
        presaleRule.setGoodsId(1);


        PromotionRule promotionRule=presaleService.addPromotion(presaleRule);
        System.out.println(promotionRule);
    }

    @Test
    void addPresaleRule2() throws UpdatedDataFailedException, SeriousException {

        PresaleRule presaleRule=new PresaleRule();
        presaleRule.setDeposit(new BigDecimal(10));
        presaleRule.setFinalPayment(new BigDecimal(100));
        presaleRule.setStartTime(LocalDateTime.now().plusDays(7));
        presaleRule.setAdEndTime(LocalDateTime.now().plusDays(8));
        presaleRule.setFinalStartTime(LocalDateTime.now().plusDays(9));
        presaleRule.setEndTime(LocalDateTime.now().plusDays(10));
        presaleRule.setGoodsId(1);


        PromotionRule promotionRule=presaleService.addPromotion(presaleRule);
        System.out.println(promotionRule);
    }

    @Test
    void addPresaleRule3() throws UpdatedDataFailedException, SeriousException {

        PresaleRule presaleRule=new PresaleRule();
        presaleRule.setDeposit(new BigDecimal(10));
        presaleRule.setFinalPayment(new BigDecimal(100));
        presaleRule.setStartTime(LocalDateTime.now().plusDays(10));
        presaleRule.setAdEndTime(LocalDateTime.now().plusDays(11));
        presaleRule.setFinalStartTime(LocalDateTime.now().plusDays(12));
        presaleRule.setEndTime(LocalDateTime.now().plusDays(13));
        presaleRule.setGoodsId(1);


        PromotionRule promotionRule=presaleService.addPromotion(presaleRule);
        System.out.println(promotionRule);
    }

    /**
     * 添加
     */
    @Test
    void addGrouponRule1(){

    }


    /**
     * 列出商品id 的 所有 优惠券
     */
    @Test
    void listProimotionByGoodsId() {

        System.out.println(presaleService.listProimotionByGoodsId(1));


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