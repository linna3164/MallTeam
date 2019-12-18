package com.xmu.discount.service.impl;

import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.exception.SeriousException;
import com.xmu.discount.inter.OrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PresaleServiceImpl extends PromotionServiceImpl{

    @Autowired
    PresaleRuleDao presaleRuleDao;

    @Autowired
    OrderFeign orderFeign;


    /**
     * 预售活动失效后的行为
     * @param promotionRule
     */
    @Override
    public void toDoSomthingAfterDisable(PromotionRule promotionRule) throws SeriousException {
        //TODO:退款

//        List<Payment> payments=new ArrayList<>();
//        List<Order> orders=orderService.listOrdersOfPromotion(promotionRule);
//
//        for(Order order:orders){
//            Payment payment=new Payment();
//            payment.setActualPrice(order.getOrderItemList().get(0).getDealPrice().negate());
//            payment.setOrderId(order.getId());
//            payments.add(payment);
//        }
//
//        orderService.refundPresaleOrders(payments);
        orderFeign.refundPresaleOrder((PresaleRule) promotionRule);
    }
}
