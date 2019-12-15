package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.exception.PromotionNotFoundException;
import com.xmu.discount.exception.UpdatedDataFailedException;
import com.xmu.discount.mapper.GrouponRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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


        PromotionRule promotionRule= grouponRuleMapper.getGrouponRuleById(id);
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
        List<GrouponRule> grouponRules=grouponRuleMapper.getGrouponRules();
        List<PromotionRule> promotionRules=new ArrayList<PromotionRule>();
        promotionRules.addAll(grouponRules);
        return promotionRules;
    }

    /**
     * 添加团购规则
     * @param promotionRule
     * @return
     */
    @Override
    public int addPromotionRule(PromotionRule promotionRule) {
        return grouponRuleMapper.addGrouponRule((GrouponRule)promotionRule);
    }

    /**
     * 查询一个商品的团购规则
     * @param goodsId
     * @return
     */
    @Override
    public List<PromotionRule> listPromotionRuleByGoodsId(Integer goodsId) {
        List<PromotionRule>promotionRules=new ArrayList<>();
        List<GrouponRule> grouponRules=grouponRuleMapper.listGrouponRuleByGoodsId(goodsId);
        promotionRules.addAll(grouponRules);
        return promotionRules;
    }

    /**
     * 修改团购规则
     * @return
     */
    @Override
    public boolean updatePromotionRuleById(PromotionRule promotionRule) throws UpdatedDataFailedException {
        int res=grouponRuleMapper.updateGrouponRuleById((GrouponRule)promotionRule);
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
    public void deletePromotionRuleById(Integer id) {
        GrouponRule grouponRule=new GrouponRule(id,true);
        grouponRuleMapper.updateGrouponRuleById(grouponRule);
    }
}
