package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.exception.PromotionNotFoundException;
import com.xmu.discount.exception.UpdatedDataFailedException;
import com.xmu.discount.mapper.GrouponRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GrouponRuleDao implements PromotionRuleDao {

    @Autowired
    public GrouponRuleMapper grouponRuleMapper;

    /**
     * 用id找团购规则
     * @param id
     * @return
     */
    @Override
    public PromotionRule  getPromotionRuleById(Integer id) throws PromotionNotFoundException {

       GrouponRulePo grouponRulePo = grouponRuleMapper.getGrouponRuleById(id);
       GrouponRule grouponRule=new GrouponRule();
        grouponRule.setRealObj(grouponRulePo);
        PromotionRule promotionRule=(PromotionRule) grouponRule;
        if(promotionRule==null){
            throw new PromotionNotFoundException();
        }
        return promotionRule;
    }


    /**
     * 查看所有的团购规则
     * @return
     */
    @Override
    public List<PromotionRule> listPromotions() {
        List<GrouponRulePo> grouponRules=grouponRuleMapper.getGrouponRules();
        List<GrouponRule> grouponRules1=new ArrayList<GrouponRule>();
        for(GrouponRulePo g:grouponRules)
            grouponRules1.add(new GrouponRule(g));
        List<PromotionRule> promotionRules=new ArrayList<PromotionRule>();
        promotionRules.addAll(grouponRules1);
        return promotionRules;
    }

    /**
     * 添加团购规则
     * @param promotionRule
     * @return
     */
    @Override
    public int addPromotionRule(PromotionRule promotionRule) {
        GrouponRule grouponRule=(GrouponRule)promotionRule;
        GrouponRulePo grouponRulePo=grouponRule.getRealObj();
        grouponRulePo.setGmtCreate(LocalDateTime.now());
        grouponRulePo.setGmtModified(LocalDateTime.now());
        return grouponRuleMapper.addGrouponRule(grouponRulePo);
    }

    /**
     * 查询一个商品的团购规则
     * @param goodsId
     * @return
     */
    @Override
    public List<PromotionRule> listPromotionRuleByGoodsId(Integer goodsId) {
        List<PromotionRule>promotionRules=new ArrayList<>();
        List<GrouponRulePo> grouponRules=grouponRuleMapper.listGrouponRuleByGoodsId(goodsId);
        List<GrouponRule> grouponRules1=new ArrayList<GrouponRule>();
        for(GrouponRulePo g:grouponRules)
            grouponRules1.add(new GrouponRule(g));
        return promotionRules;
    }

    /**
     * 修改团购规则
     * @return
     */
    @Override
    public boolean updatePromotionRuleById(PromotionRule promotionRule) throws UpdatedDataFailedException {
        GrouponRule grouponRule=(GrouponRule)promotionRule;
        GrouponRulePo grouponRulePo=grouponRule.getRealObj();
        grouponRulePo.setGmtModified(LocalDateTime.now());
        int res=grouponRuleMapper.updateGrouponRuleById(grouponRulePo);
        if(res==0){
            throw new UpdatedDataFailedException();
        }
        else{
            return true;
        }

    }


    /**
     * 删除团购规则
     * @param id
     */
    @Override
    public void deletePromotionRuleById(Integer id) throws UpdatedDataFailedException {
        GrouponRule grouponRule=new GrouponRule();
        GrouponRulePo grouponRulePo=new GrouponRulePo(id,true);
        grouponRulePo.setGmtModified(LocalDateTime.now());
        grouponRule.setRealObj(grouponRulePo);
        this.updatePromotionRuleById(grouponRule);
    }
}
