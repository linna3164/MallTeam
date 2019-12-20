package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PromotionRule;

import com.xmu.discount.exception.GrouponRuleAddFailException;
import com.xmu.discount.exception.GrouponRuleDeleteFailException;
import com.xmu.discount.exception.GrouponRuleUpdateFailException;
import com.xmu.discount.mapper.GrouponRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  ln
 * @Description 团购活动相关的dao
 * @ate 2019/12/10
 */
@Repository
public class GrouponRuleDao implements PromotionRuleDao {

    @Autowired
    public GrouponRuleMapper grouponRuleMapper;


    @Override
    public void setDisable(PromotionRule promotionRule) throws GrouponRuleUpdateFailException {
        GrouponRule couponRule=(GrouponRule) promotionRule;
        GrouponRule pre=new GrouponRule(couponRule.getId());
        pre.setStatusCode(false);
        this.updatePromotionRuleById(couponRule);
    }

    /**
     * 用id找团购规则
     * @param id
     * @return
     */
    @Override
    public PromotionRule  getPromotionRuleById(Integer id)   {

       GrouponRulePo grouponRulePo = grouponRuleMapper.getGrouponRuleById(id);
       GrouponRule grouponRule=new GrouponRule();
        grouponRule.setRealObj(grouponRulePo);
        PromotionRule promotionRule=(PromotionRule) grouponRule;
        return promotionRule;
    }


    /**
     * 查看所有的团购规则
     * @return
     */
    @Override
    public List<? extends PromotionRule> listPromotions() {
        List<GrouponRulePo> grouponRules=grouponRuleMapper.getGrouponRules();
        List<GrouponRule> grouponRules1=new ArrayList<GrouponRule>();
        for(GrouponRulePo g:grouponRules) {
            grouponRules1.add(new GrouponRule(g));
        }

        return grouponRules1;
    }

    /**
     * 添加团购规则
     * @param promotionRule
     * @return
     */
    @Override
    public boolean addPromotionRule(PromotionRule promotionRule) throws GrouponRuleAddFailException {
        GrouponRule grouponRule=(GrouponRule)promotionRule;
        GrouponRulePo grouponRulePo=grouponRule.getRealObj();
        grouponRule.setGmtCreate(LocalDateTime.now());
        grouponRule.setGmtModified(LocalDateTime.now());
        grouponRule.setBeDeleted(false);
        grouponRule.setStatusCode(true);
        if(grouponRuleMapper.addGrouponRule(grouponRulePo)){
            return true;
        }
        else {
            throw new GrouponRuleAddFailException();
        }

    }

    /**
     * 查询一个商品的团购规则
     * @param goodsId
     * @return
     */
    @Override
    public List<? extends PromotionRule> listPromotionRuleByGoodsId(Integer goodsId) {
        List<GrouponRulePo> grouponRules=grouponRuleMapper.listGrouponRuleByGoodsId(goodsId);
        List<GrouponRule> grouponRules1=new ArrayList<GrouponRule>();
        for(GrouponRulePo g:grouponRules) {
            grouponRules1.add(new GrouponRule(g));
        }
        return grouponRules1;
    }

    /**
     * 修改团购规则
     * @return
     */
    @Override
    public boolean updatePromotionRuleById(PromotionRule promotionRule) throws GrouponRuleUpdateFailException {
        GrouponRule grouponRule=(GrouponRule)promotionRule;
        grouponRule.setGmtModified(LocalDateTime.now());
        GrouponRulePo grouponRulePo=grouponRule.getRealObj();
        if(grouponRuleMapper.updateGrouponRuleById(grouponRulePo)){
            return true;
        }
        else {
            throw new GrouponRuleUpdateFailException();
        }

    }


    /**
     * 删除团购规则
     * @param id
     */
    @Override
    public boolean deletePromotionRuleById(Integer id) throws GrouponRuleDeleteFailException {
        GrouponRulePo grouponRule=new GrouponRulePo(id,true);
        GrouponRule grouponRule1=new GrouponRule();
        grouponRule1.setRealObj(grouponRule);
        try {
            this.updatePromotionRuleById(grouponRule1);
        } catch (GrouponRuleUpdateFailException e) {
            throw new GrouponRuleDeleteFailException();
        }
        return true;

    }
}
