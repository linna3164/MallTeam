package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class GrouponServiceImpl extends PromotionServiceImpl{

    @Autowired
    GrouponRuleDao grouponRuleDao;

    @Override
    public void toDoSomthing(PromotionRule promotionRule) {

    }

    /**
     * 计算成团人数
     * @return
     */
    public List<Payment> calcuGroupon(){
        LocalDateTime now=LocalDateTime.now();
        if()
        //TODO:获得前一天结束的groupi
    }









}
