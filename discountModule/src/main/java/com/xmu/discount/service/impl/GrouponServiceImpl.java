package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class GrouponServiceImpl extends PromotionServiceImpl{

    @Autowired
    GrouponRuleDao grouponRuleDao;

//    /**
//     * 团购活动失效后的行为
//     * @param promotionRule
//     */
//    public List<Payment> toDoSomthing(PromotionRule promotionRule) {
//        //TODO:退款，找到改团购活动的订单（根据dealPrice）
//        List<Order> orders=orderService.listOrdersOfGrouponRule(GrouponRule grouponRule);
//        for(Order order:orders){
//            BigDecimal refundMoney=order.getOrderItemList().get(0).getDealPrice();
//        }
//    }

    /**
     * 计算成团人数
     * @return
     */
    public List<Payment> calcuGroupon(){
        LocalDateTime now=LocalDateTime.now();
        //TODO:获得前一天结束的groupi

        return null;
    }





}
