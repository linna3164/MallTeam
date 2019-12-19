package com.xmu.discount.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xmu.discount.DiscountApplication;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.discount.*;
import com.xmu.discount.exception.*;
import com.xmu.discount.domain.discount.PromotionRule;
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
     * @throws
     */
    @Test
    void getPromotionById()   {
        PromotionRule promotionRule=presaleService.getPromotionById(1,"presaleRule");
        assertEquals(null,promotionRule);
    }


    /**
     * 正常，通过
     * @throws
     */
    @Test
    void getPromotionById2()   {

        PromotionRule promotionRule=presaleService.getPromotionById(3,"presaleRule");
        System.out.println(promotionRule);
        assertEquals(promotionRule,null);

    }

    /**
     * 正常删除，通过
     * @throws
     * @throws
     */
    @Test
    void deletePromotionById() throws GrouponRuleUpdateFailException, PresaleRuleDeleteFailException, CouponRuleDeleteFailException, GrouponRuleDeleteFailException {
            PromotionRule promotionRule=presaleService.getPromotionById(3,"presaleRule");
            presaleService.deletePromotionById(promotionRule);
    }

    @Test
    void deletePromotionById2() throws GrouponRuleUpdateFailException, PresaleRuleDeleteFailException, CouponRuleDeleteFailException, GrouponRuleDeleteFailException {
        PromotionRule promotionRule=grouponService.getPromotionById(1,"grouponRule");
        grouponService.deletePromotionById(promotionRule);
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
     * 更新存在的。通过
     */
    @Test
    void updatepromotionRule2() throws GrouponRuleUpdateFailException, PresaleRuleUpdateFailException, CouponRuleUpdateFailException, PresaleRuleUnValidException {

//        PromotionRule promotionRule=presaleService.getPromotionById(4,"presaleRule");
//        System.out.println(promotionRule);
        PresaleRule presaleRule=new PresaleRule();
        presaleRule.setId(4);
        presaleRule.setDeposit(new BigDecimal(200));

        presaleService.updatepromotionRule(presaleRule);


    }

    @Test
    void updatepromotionRule4() throws GrouponRuleUpdateFailException, PresaleRuleUpdateFailException, CouponRuleUpdateFailException, PresaleRuleUnValidException {

//        PromotionRule promotionRule=presaleService.getPromotionById(4,"presaleRule");
//        System.out.println(promotionRule);
        PresaleRule presaleRule=new PresaleRule();
        presaleRule.setId(4);
        presaleRule.setDeposit(new BigDecimal(200));

        presaleService.updatepromotionRule(presaleRule);


    }

    /**
     * 更新已经被删除的。通过
     */
    @Test
    void updatepromotionRule3() throws GrouponRuleUpdateFailException, PresaleRuleUpdateFailException, CouponRuleUpdateFailException, PresaleRuleUnValidException {

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
        String string="{\"strategy\":[{\"lowerBound\":10,\"upperBound\":20,\"discountRate\":0.90},]}";
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
    void addPresaleRule() throws CouponRuleAddFailException, GrouponRuleAddFailException, PresaleRuleAddFailException {

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
    void addPresaleRule2() throws CouponRuleAddFailException, GrouponRuleAddFailException, PresaleRuleAddFailException {

        PresaleRule presaleRule=new PresaleRule();
        presaleRule.setDeposit(new BigDecimal(10));
        presaleRule.setFinalPayment(new BigDecimal(100));
        presaleRule.setStartTime(LocalDateTime.now().plusDays(70));
        presaleRule.setAdEndTime(LocalDateTime.now().plusDays(80));
        presaleRule.setFinalStartTime(LocalDateTime.now().plusDays(90));
        presaleRule.setEndTime(LocalDateTime.now().plusDays(100));
        presaleRule.setGoodsId(1);


        PromotionRule promotionRule=presaleService.addPromotion(presaleRule);
        System.out.println(promotionRule);
    }

    @Test
    void addPresaleRule3() throws CouponRuleAddFailException, GrouponRuleAddFailException, PresaleRuleAddFailException {

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
    void addGrouponRule1() throws CouponRuleAddFailException, GrouponRuleAddFailException, PresaleRuleAddFailException {
        GrouponRule grouponRule = new GrouponRule();
        GrouponRulePo grouponRulePo = new GrouponRulePo();
        grouponRule.setRealObj(grouponRulePo);
        grouponRule.setStartTime(LocalDateTime.now().plusDays(100));
        grouponRule.setEndTime(LocalDateTime.now().plusDays(500));
        grouponRule.setStatusCode(true);
        String string="{\"strategy\":[{\"lowerBound\":10,\"upperBound\":20,\"discountRate\":0.90},{\"lowerBound\":21,\"upperBound\":200,\"discountRate\":0.090}]}";
        grouponRule.setGrouponLevelStragety(string);
        grouponRule.setGoodsId(2);
        grouponRule.setGmtCreate(LocalDateTime.now());
        grouponRule.setGmtModified(LocalDateTime.now());
        grouponRule.setBeDeleted(false);
        PromotionRule promotionRule = grouponService.addPromotion(grouponRule);
        System.out.println(promotionRule);

    }


//    /**
//     * 列出商品id 的 所有 优惠券
//     */
//    @Test
//    void listProimotionByGoodsId() {
//
////        System.out.println(presaleService.listProimotionByGoodsId(1));
//        assertEquals(presaleService.listProimotionByGoodsId(1),null);
//
//    }


    /**
     *
     */
    @Test
    void listCurrentPromotionByGoodsId() {

    }

    @Test
    void getCurrentPromotionByGoodsId() {
    }


    /**
     * 获取所有的预售规则列表
     */
    /**
     * 获得团购，通过
     */
    @Test
    void listPromotionRuleOfType() {

        assertEquals(presaleService.listPromotionRuleOfType("presaleRule"),null);
        List<?extends PromotionRule> grouponRuleList = grouponService.listPromotionRuleOfType("grouponRule");
        for (PromotionRule promotionRule:grouponRuleList)
        {
            System.out.println(promotionRule);
        }

    }



    @Test
    void listPromotionRuleOfTypeInprocessWithGoods() {
    }

    /**
     *
     */
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

    /**
     * 通过id获取团购规则
     * @throws
     */
    @Test
    void getGrouponRuleById()   {
        PromotionRule promotionRule=grouponService.getPromotionById(1,"grouponRule");
        assertEquals(promotionRule,null);
    }

    @Test
    void addGrouponRule12() throws CouponRuleAddFailException, GrouponRuleAddFailException, PresaleRuleAddFailException {
        GrouponRule grouponRule = new GrouponRule();
        GrouponRulePo grouponRulePo = new GrouponRulePo();
        grouponRule.setRealObj(grouponRulePo);
        grouponRule.setStartTime(LocalDateTime.now().plusDays(100));
        grouponRule.setEndTime(LocalDateTime.now().plusDays(500));
        grouponRule.setStatusCode(true);
        String string="{\"strategy\":[{\"lowerBound\":10,\"upperBound\":20,\"discountRate\":0.90},{\"lowerBound\":21,\"upperBound\":null,\"discountRate\":0.08}]}";
        grouponRule.setGrouponLevelStragety(string);
        grouponRule.setGoodsId(2);
        grouponRule.setGmtCreate(LocalDateTime.now());
        grouponRule.setGmtModified(LocalDateTime.now());
        grouponRule.setBeDeleted(false);
        PromotionRule promotionRule = grouponService.addPromotion(grouponRule);
        System.out.println(promotionRule);

    }

    /**
     * 通过Id找团购规则，正常，通过
     * @throws
     */
    @Test
    void getGrouponRuleById2()   {
        PromotionRule promotionRule=grouponService.getPromotionById(1,"grouponRule");

        assertEquals(promotionRule,null);
    }

    /**
     * 通过id找优惠券规则，正常，通过
     * @throws
     */
    @Test
    void getGrouponRuleById3()  {
        PromotionRule promotionRule=grouponService.getPromotionById(7,"grouponRule");

        GrouponRule grouponRule=(GrouponRule)promotionRule;
        List<GrouponRuleStrategy> strategies=grouponRule.getStrategyList();
        assertEquals(strategies.get(0).getDiscountRate(),0.08);
    }

    @Test
    void addPromotion() {
    }
}