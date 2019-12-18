package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.exception.PromotionNotFoundException;
import com.xmu.discount.exception.SeriousException;
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


    @Override
    public void setDisable(PromotionRule promotionRule) throws UpdatedDataFailedException {
        GrouponRule couponRule=(GrouponRule) promotionRule;
        GrouponRule pre=new GrouponRule(couponRule.getId());
        //TODO:标准组！！！
        pre.setStatusCode(false);
        this.updatePromotionRuleById(couponRule);
    }

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
    public int addPromotionRule(PromotionRule promotionRule) throws SeriousException {
        GrouponRule grouponRule=(GrouponRule)promotionRule;
        GrouponRulePo grouponRulePo=grouponRule.getRealObj();
        grouponRule.setGmtCreate(LocalDateTime.now());
        grouponRule.setGmtModified(LocalDateTime.now());
        grouponRule.setBeDeleted(false);
        grouponRule.setStatusCode(true);
        int res= grouponRuleMapper.addGrouponRule(grouponRulePo);
        if(res==0){
            throw new SeriousException();
        }
        else{
            return res;
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
    public boolean updatePromotionRuleById(PromotionRule promotionRule) throws UpdatedDataFailedException {
        GrouponRule grouponRule=(GrouponRule)promotionRule;
        grouponRule.setGmtModified(LocalDateTime.now());
        GrouponRulePo grouponRulePo=grouponRule.getRealObj();
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
        GrouponRulePo grouponRule=new GrouponRulePo(id,true);
        GrouponRule grouponRule1=new GrouponRule();
        grouponRule1.setRealObj(grouponRule);
        this.updatePromotionRuleById(grouponRule1);
    }
}
