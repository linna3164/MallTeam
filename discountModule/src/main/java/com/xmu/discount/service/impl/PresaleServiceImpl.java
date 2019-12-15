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

//    @Override
    public PromotionRule getPresaleRuleById(Integer id) {
        return presaleRuleDao.getPromotionRuleById(id);
    }

//    @Override
    public PromotionRule addPresaleRule(PresaleRule presaleRule) {
        presaleRule.setBeDeleted(false);
        presaleRule.setGmtCreate(LocalDateTime.now());
        int success=presaleRuleDao.addPromotionRule(presaleRule);
        if(success==0) return null;
        else  return presaleRule;
    }

//    @Override
    public List<PromotionRule> listPresaleRuleByGoodsId(Integer goodsId) {
        return presaleRuleDao.listPromotionRuleByGoodsId(goodsId);
    }

//    @Override
    public PromotionRule updatePresaleRuleById(PresaleRule presaleRule) {
        if(presaleRule.getpromotionRulestartTime().isAfter(LocalDateTime.now())) {
            presaleRule.setGmtModified(LocalDateTime.now());
            int success = presaleRuleDao.updatePromotionRuleById(presaleRule);
            if (success == 0) return null;
            else return presaleRuleDao.getPromotionRuleById(presaleRule.getId());
        }
        else return null;
    }

//    @Override
    public PromotionRule deletePresaleRuleById(Integer id) {
        if(presaleRuleDao.getPromotionRuleById(id).getpromotionRulestartTime().isAfter(LocalDateTime.now()))
        {
            PresaleRule presaleRule=new PresaleRule();
            presaleRule.setGmtModified(LocalDateTime.now());
            presaleRule.setId(id);
            presaleRule.setBeDeleted(true);
            int success=presaleRuleDao.updatePromotionRuleById(presaleRule);
            if(success==0) return null;
            else return presaleRuleDao.getPromotionRuleById(id);
        }

        else return null;
    }

    @Override
    public void toDoSomthing(PromotionRule promotionRule) {

    }
}
