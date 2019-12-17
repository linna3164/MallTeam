package com.xmu.discount.service.impl;

import com.alibaba.fastjson.JSON;
import com.sun.tools.javac.util.Log;
import com.xmu.discount.DiscountApplication;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
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
    private PromotionServiceImpl presaleService;

    @Test
    void addPromotion1() throws UpdatedDataFailedException, SeriousException {
        GrouponRule grouponRule=new GrouponRule();
        GrouponRulePo grouponRulePo=new GrouponRulePo();

        grouponRule.setRealObj(grouponRulePo);

        grouponRule.setGoodsId(1);
        grouponRule.setStartTime(LocalDateTime.now().plusDays(1));
        grouponRule.setEndTime(LocalDateTime.now().plusDays(10));

        grouponService.addPromotion(grouponRule);



    }

    @Test
    void addPromotion2() throws UpdatedDataFailedException, SeriousException {
        GrouponRule grouponRule=new GrouponRule();
        GrouponRulePo grouponRulePo=new GrouponRulePo();

        grouponRule.setRealObj(grouponRulePo);

        grouponRule.setGoodsId(1);
        grouponRule.setStartTime(LocalDateTime.now().plusDays(1));
        grouponRule.setEndTime(LocalDateTime.now().plusDays(10));

        GrouponRule.Strategy strategy=grouponRule.new Strategy(10,20,new BigDecimal(0.9).setScale(2, RoundingMode.HALF_UP));

        List<GrouponRule.Strategy> strategyList=new ArrayList<>();
        strategyList.add(strategy);

        grouponRule.setStrategyList(strategyList);

        assertEquals(grouponRule.getRealObj().getGrouponLevelStrategy(),"nullpp");

        grouponService.addPromotion(grouponRule);
    }


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
        JSON.parse(string);
        System.out.println(string);
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