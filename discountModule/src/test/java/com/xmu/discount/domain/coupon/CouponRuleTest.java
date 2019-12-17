package com.xmu.discount.domain.coupon;

import com.xmu.discount.domain.others.domain.OrderItem;
import com.xmu.discount.util.JacksonUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CouponRuleTest {

    @Test
    void isOkToUse() {
    }

    @Test
    void isAlreadyStart() {
    }

    @Test
    void canGet() {
    }

    @Test
    void isLeft() {
    }

    @Test
    void isGoingOn() {
    }

    @Test
    void getValidItems() {
    }

    @Test
    void getStrategy() {
        List<OrderItem> orderItems=new ArrayList<OrderItem>();
        String  jsonString="{\"name\":\"com.xmu.discount.domain.coupon.AbstractCouponStrategy\", \"obj\":{\"threshold\":5, \"oﬀCash\":10.01}}";
        jsonString = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString);
        String strategyName = JacksonUtil.parseString(jsonString, "name");
        System.out.println(strategyName);
        AbstractCouponStrategy strategy = null;
        try {
            strategy = (AbstractCouponStrategy) JacksonUtil.parseObject(jsonString, "obj", Class.forName(strategyName));
        } catch (ClassNotFoundException e) {
           System.out.println("error");
        }

    }

    @Test
    void setStrategy() {
    }

    @Test
    void isUsedOnGoods() {
    }

    @Test
    void getGoodsIds() {
    }

    @Test
    void setGoodsIds() {
    }

    @Test
    void cacuCouponPrice() {
    }

    @Test
    void getTimeStatus() {
    }

    @Test
    void setActiveStatus() {
    }

    @Test
    void setTimeStatus() {
    }
}