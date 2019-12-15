package com.xmu.discount.service.impl;

import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.PromotionRule;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class PresaleServiceImpl extends PromotionServiceImpl{

    @Autowired
    PresaleRuleDao presaleRuleDao;


    /**
     * 预售活动失效后的行为
     * @param promotionRule
     */
    @Override
    public void toDoSomthing(PromotionRule promotionRule) {
        //TODO:退款
    }






}