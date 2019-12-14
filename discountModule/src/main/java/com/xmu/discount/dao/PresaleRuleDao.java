package com.xmu.discount.dao;

import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.Promotion;
import com.xmu.discount.mapper.PresaleRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PresaleRuleDao {

    @Autowired
    public  PresaleRuleMapper presaleRuleMapper;

    /**
     * 用id找预售规则
     * @param id
     * @return
     */
    public  Promotion getPresaleRuleById(Integer id){
        return presaleRuleMapper.getPresaleRuleById(id);
    }

    /**
     * 查看所有预售规则
     * @return
     */
    public List<Promotion> getPresaleRules(){
        List<Promotion> promotions=new ArrayList<Promotion>();
        List<PresaleRule> presaleRules=presaleRuleMapper.getPresaleRules();
        for(PresaleRule p:presaleRules){
            promotions.add(p);
        }
        return promotions;
    }
    /**
     * 添加预售规则
     * @param presaleRule
     * @return
     */
    public  int addPresaleRule(PresaleRule presaleRule){
        return presaleRuleMapper.addPresaleRule(presaleRule);
    }

    /**
     * 查询一个商品的预售规则
     * @param goodsId
     * @return
     */
    public List<Promotion> listPresaleRuleByGoodsId(Integer goodsId){
        List<Promotion>promotions=new ArrayList<>();
        List<PresaleRule> presaleRule=presaleRuleMapper.listPresaleRuleByGoodsId(goodsId);
        for(Promotion promotionItem:presaleRule){
            promotions.add(promotionItem);
        }
        return promotions;
    }

    /**
     * 修改预售规则
     * @return
     */
    public int updatePresaleRuleById(PresaleRule presaleRule){
        return presaleRuleMapper.updatePresaleRuleById(presaleRule);
    }

    /**
     * 删除预售规则
     * @return
     */
    public int deletePresaleRuleById(Integer id){
        PresaleRule presaleRule=new presaleRule(id,true);
        presaleMapper.updatePresaleRuleById(presaleRule);

    }
}
