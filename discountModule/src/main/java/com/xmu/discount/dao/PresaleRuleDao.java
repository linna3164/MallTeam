package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.exception.UpdatedDataFailedException;
import com.xmu.discount.mapper.PresaleRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PresaleRuleDao implements PromotionRuleDao {

    @Autowired
    public  PresaleRuleMapper presaleRuleMapper;

    /**
     * 用id找预售规则
     * @param id
     * @return
     */
    @Override
    public PromotionRule getPromotionRuleById(Integer id) {
        return presaleRuleMapper.getPresaleRuleById(id);
    }

    /**
     * 查看所有预售规则
     * @return
     */
    @Override
    public List<PromotionRule> listPromotions() {
        List<PromotionRule> promotionRules=new ArrayList<PromotionRule>();
        List<PresaleRule> presaleRules=presaleRuleMapper.getPresaleRules();
        for(PresaleRule p:presaleRules){
            promotionRules.add(p);
        }
        return promotionRules;
    }

    /**
     * 添加预售规则
     * @param promotionRule
     * @return
     */
    @Override
    public int addPromotionRule(PromotionRule promotionRule) {

        PresaleRule presaleRule=(PresaleRule)promotionRule;
        presaleRule.setGmtCreate(LocalDateTime.now());
        presaleRule.setGmtModified(LocalDateTime.now());

        return presaleRuleMapper.addPresaleRule(presaleRule);

    }

    /**
     * 查询一个商品的预售规则
     * @param goodsId
     * @return
     */
    @Override
    public List<PromotionRule> listPromotionRuleByGoodsId(Integer goodsId) {
        List<PromotionRule>promotionRules=new ArrayList<>();
        List<PresaleRule> presaleRules=presaleRuleMapper.listPresaleRuleByGoodsId(goodsId);
        promotionRules.addAll(presaleRules);
        return promotionRules;
    }

    /**
     * 修改预售规则
     * @return
     */
    @Override
    public boolean updatePromotionRuleById(PromotionRule promotionRule) throws UpdatedDataFailedException {

        PresaleRule presaleRule=(PresaleRule) promotionRule;
        presaleRule.setGmtModified(LocalDateTime.now());

        int res=presaleRuleMapper.updatePresaleRuleById((PresaleRule) promotionRule);
        if(res==0){
            throw new UpdatedDataFailedException();
        }
        else{
            return true;
        }
    }


    /**
     * 删除预售规则
     * @param id
     */
    @Override
    public void deletePromotionRuleById(Integer id) throws UpdatedDataFailedException {

        PresaleRule grouponRule=new PresaleRule(id,true);

        this.updatePromotionRuleById(grouponRule);
    }
}
