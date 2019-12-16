package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.exception.SeriousException;
import com.xmu.discount.inter.OrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class GrouponServiceImpl extends PromotionServiceImpl{

    @Autowired
    GrouponRuleDao grouponRuleDao;

    @Autowired
    OrderFeign orderFeign;



    /**
     *
     * @param promotionRule
     * @throws SeriousException
     */
    @Override
    public void toDoSomthingAfterDisable(PromotionRule promotionRule) throws SeriousException {

    }

    /**
     * 定时任务， 每天晚上搜索前一天24小时之内的完成的grouponpo，然后对这
     * 些po都调用这个接口，根据grouponpo里的团购开始时间、结束时
     * 间和goodsid检查所有买了这个商品的order，返给discount模块就
     * 行
     */
    @Scheduled(cron = "0 10 0 ? * *")
    public void caculateGroupon(){
        List<GrouponRule> grouponRuleList = listNeedCalcuGrouponRule();
        for(GrouponRule grouponRule:grouponRuleList)
        {
            List<Order> orderList = orderFeign.getGrouponOrders(grouponRule.getRealObj());
            List<Payment> paymentList = caculGrouponOrderRefundList(orderList);
            orderFeign.refundGrouponOrder(paymentList);
        }
    }

    public List<GrouponRule> listNeedCalcuGrouponRule(){
        //TODO:获得前一天完成的grouponRulePo
        List<PromotionRule> promotionRules=this.listPromotionRule("GrouponDao");
        List<GrouponRule> res=new ArrayList<>();
        for(PromotionRule promotionRule:promotionRules){
            if(promotionRule.getActiveStatus().equals(PromotionRule.ActiveStatus.NOTFINISHED))
        }
        return null;
    }



    public List<Payment> caculGrouponOrderRefundList(List<Order> orderList)
    {
        //TODO:计算出每个order应该退款的数量
        return null;
    }

}
