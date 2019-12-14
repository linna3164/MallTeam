package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.PromotionRule;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class GrouponServiceImpl {

    @Autowired
    GrouponRuleDao grouponRuleDao;

//    @Override
    public GrouponRule getGrouponRuleById(Integer id) {
        return (GrouponRule) grouponRuleDao.getGrouponRuleById(id);
    }

//    @Override
    public PromotionRule addGrouponRule(GrouponRule grouponRule) {
        grouponRule.setBeDeleted(false);
        grouponRule.setGmtCreate(LocalDateTime.now());
        int success=grouponRuleDao.addGrouponRule(grouponRule);
        if(success==0) return null;
        else return grouponRule;

    }

//    @Override
    public List<PromotionRule> listGrouponRuleByGoodsId(Integer goodsId) {
        return grouponRuleDao.listGrouponRuleByGoodsId(goodsId);
    }

//    @Override
    public List<PromotionRule> getGrouponRules() {
        return grouponRuleDao.getGroupRules();
    }

//    @Override
    public PromotionRule updateGrouponRuleById(GrouponRule grouponRule) {
        if(grouponRule.getStartTime().isAfter(LocalDateTime.now())){
            grouponRule.setGmtModified(LocalDateTime.now());
            int success=grouponRuleDao.updateGrouponRuleById(grouponRule);
            if(success==0) return null;
            else return grouponRuleDao.getGrouponRuleById(grouponRule.getId());
        }
        else return null;

    }

//    @Override
    public PromotionRule deleteGroupRuleById(Integer id) {

        //TODO:判断。。。
        if( grouponRuleDao.getGrouponRuleById(id).getpromotionRulestartTime().isAfter(LocalDateTime.now())){
            GrouponRule g1=new GrouponRule();
            g1.setId(id);
            g1.setBeDeleted(true);
            g1.setGmtModified(LocalDateTime.now());
            int success=grouponRuleDao.updateGrouponRuleById(g1);
            if(success==0) return null;
            else return grouponRuleDao.getGrouponRuleById(id);
        }
        else return null;

    }

}
