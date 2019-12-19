package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.exception.*;
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
    public PromotionRule getPromotionRuleById(Integer id) throws PresaleRuleUnValidException {

        PromotionRule promotionRule=presaleRuleMapper.getPresaleRuleById(id);
        if(promotionRule==null){
            throw new PresaleRuleUnValidException();
        }
        return promotionRule;
    }

    /**
     * 查看所有预售规则
     * @return
     */
    @Override
    public List<? extends PromotionRule> listPromotions() {

        List<PresaleRule> presaleRules=presaleRuleMapper.getPresaleRules();

        return presaleRules;
    }

    /**
     * 添加预售规则
     * @param promotionRule
     * @return
     */
    @Override
    public boolean addPromotionRule(PromotionRule promotionRule) throws PresaleRuleAddFailException {

        PresaleRule presaleRule=(PresaleRule)promotionRule;
        presaleRule.setGmtCreate(LocalDateTime.now());
        presaleRule.setGmtModified(LocalDateTime.now());
        presaleRule.setBeDeleted(false);
        presaleRule.setStatusCode(true);
        if ( presaleRuleMapper.addPresaleRule(presaleRule)){
            return true;
        }
        else {
            throw new PresaleRuleAddFailException();
        }


    }

    /**
     * 查询一个商品的预售规则
     * @param goodsId
     * @return
     */
    @Override
    public List<? extends PromotionRule> listPromotionRuleByGoodsId(Integer goodsId) {
        List<PresaleRule> presaleRules=presaleRuleMapper.listPresaleRuleByGoodsId(goodsId);
        return presaleRules;
    }

    /**
     * 修改预售规则
     * @return
     */
    @Override
    public boolean updatePromotionRuleById(PromotionRule promotionRule) throws PresaleRuleUpdateFailException {

        PresaleRule presaleRule=(PresaleRule) promotionRule;
        presaleRule.setGmtModified(LocalDateTime.now());

        if(presaleRuleMapper.updatePresaleRuleById((PresaleRule) promotionRule)){
            return true;
        }
        else {
            throw new PresaleRuleUpdateFailException();
        }

    }

    @Override
    public void  setDisable(PromotionRule promotionRule) throws PresaleRuleSetDisableFailException {

        PresaleRule presaleRule=(PresaleRule) promotionRule;
        PresaleRule pre=new PresaleRule(presaleRule.getId());
        pre.setStatusCode(false);

        try {
            this.updatePromotionRuleById(presaleRule);
        } catch (PresaleRuleUpdateFailException e) {
            throw new PresaleRuleSetDisableFailException();
        }
    }


    /**
     * 删除预售规则
     * @param id
     */
    @Override
    public boolean deletePromotionRuleById(Integer id) throws PresaleRuleDeleteFailException {

        PresaleRule grouponRule=new PresaleRule(id,true);

        try {
            updatePromotionRuleById(grouponRule);
        } catch (PresaleRuleUpdateFailException e) {
            throw new PresaleRuleDeleteFailException();
        }
        return true;
    }
}
