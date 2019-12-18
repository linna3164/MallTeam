package com.xmu.discount.dao;

import com.xmu.discount.domain.coupon.AbstractCouponStrategy;
import com.xmu.discount.util.JacksonUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PresaleRuleDaoTest {

    @Test
    void getPromotionRuleById() {
        String jsonString = "{\"name\":\"com.xmu.discount.domain.coupon.CashOffStrategy\", \"obj\":{\"threshold\":5, \"offCash\":10.01}}";
        jsonString = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString);
        String strategyName = JacksonUtil.parseString(jsonString, "name");
        System.out.println(strategyName);
        AbstractCouponStrategy strategy = null;
        try {
            strategy = (AbstractCouponStrategy) JacksonUtil.parseObject(jsonString, "obj", Class.forName(strategyName));
            System.out.println(strategy);
        } catch (ClassNotFoundException e) {
            System.out.println(1);
        }

    }

    @Test
    void listPromotions() {
    }

    @Test
    void addPromotionRule() {
    }

    @Test
    void listPromotionRuleByGoodsId() {
    }

    @Test
    void updatePromotionRuleById() {
    }

    @Test
    void deletePromotionRuleById() {
    }
}