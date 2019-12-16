package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.swing.*;
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

//    /**
//     * 计算成团人数
//     * @return
//     */
//    public List<Payment> calcuGroupon(){
//        LocalDateTime now=LocalDateTime.now();
//        //TODO:获得前一天结束的groupi
//
//        return null;
//    }

    /**
     * 定时任务， 每天晚上搜索前一天24小时之内的完成的grouponpo，然后对这
     * 些po都调用这个接口，根据grouponpo里的团购开始时间、结束时
     * 间和goodsid检查所有买了这个商品的order，返给discount模块就
     * 行
     */
    @Scheduled(cron = "0 10 0 ? * *")
    public void caculateGroupon(){
        List<GrouponRulePo> grouponRulePoList = getGrouponRulePoList();
        for(GrouponRulePo grouponRulePo:grouponRulePoList)
        {
            List<Order> orderList = OrderFeign.getGrouponOrders(grouponRulePo);
            List<Payment> paymentList = caculGrouponOrderRefundList(orderList);
            OrderFeign.refundGrouponOrder(paymentList);
        }
    }

    public List<GrouponRulePo> getGrouponRulePoList(){
        //TODO:获得前一天完成的grouponRulePo
        return null;
    }

    public List<Payment> caculGrouponOrderRefundList(List<Order> orderList)
    {
        //TODO:计算出每个order应该退款的数量
        return null;
    }

}
