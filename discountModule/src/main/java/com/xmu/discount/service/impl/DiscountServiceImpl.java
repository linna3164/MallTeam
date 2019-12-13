package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.Promotion;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiscountServiceImpl implements DiscountService {

    @Autowired
    GrouponRuleDao grouponRuleDao;

    @Autowired
    PresaleRuleDao presaleRuleDao;

    @Override
    public Payment getPayment(Order order) {
        List<Promotion> promotions=this.listProimotionByGoodsId(order.getOrderItemList().get(0).getProduct().getGoodsId());
        if(promotions.size()==0){//没有促销活动
            //TODO:报错
           return null;
        }
        else  if(promotions.size()>1){//促销活动大于1个
            //TODO:报错
            return null;
        }
        else{
           return  promotions.get(0).getPayment(order);
        }
    }

    /**
     * 通过id找到商品的促销活动
     * @param goodsId
     * @return
     */
    @Override
    public List<Promotion> listProimotionByGoodsId(Integer goodsId){
        List<Promotion> grouponRule=grouponRuleDao.listGrouponRuleByGoodsId(goodsId);
        List<Promotion> presaleRule=presaleRuleDao.listPresaleRuleByGoodsId(goodsId);

        List<Promotion>promotions=new ArrayList<>();
        promotions.addAll(grouponRule);
        promotions.addAll(presaleRule);

        return promotions;
    }


    /**
     * 判断这个促销活动是否可添加
     * @param promotion
     * @return
     */
    @Override
    public Boolean isValid(Promotion promotion) {
        List<Promotion> promotions=this.listProimotionByGoodsId(promotion.getPromotionGoodsId());//活动商品的所有促销活动
        //TODO:判断promotion的时间不能和其他的促销活动有交集

        return promotion.isValid(promotions);

    }


    /**
     * 获得商品当前正在进行的促销活动
     * @param goodsId
     * @return
     */
    @Override
    public Promotion getCurrentPromotionByGoodsId(Integer goodsId) {
        List<Promotion> promotions=this.listProimotionByGoodsId(goodsId);//活动商品的所有促销活动
        //TODO:结束时间大于当前时间，开始时间小于当前时间？？不确定这个逻辑是否正确
        for(Promotion p:promotions){
            LocalDateTime start=p.getPromotionStartTime();
            LocalDateTime end=p.getPromotionEndTime();
            if(LocalDateTime.now().isBefore(start)||LocalDateTime.now().isAfter(end))
                promotions.remove(p);
        }
        if(promotions.size()==0){//没有促销活动
            //TODO:报错
            return null;
        }
        else  if(promotions.size()>1){//促销活动大于1个
            //TODO:报错

            return null;
        }
        else{
            return  promotions.get(0);
        }
    }

    /**
     * 添加优惠活动
     * @param promotion
     * @return
     */
    @Override
    public Promotion addPromotion(Promotion promotion){
        if(this.isValid(promotion)){

        }
        return null;
    }

    @Override
    public GrouponRule getGrouponRuleById(Integer id) {
        return (GrouponRule) grouponRuleDao.getGrouponRuleById(id);
    }

    @Override
    public Promotion addGrouponRule(GrouponRule grouponRule) {
        grouponRule.setBeDeleted(false);
        grouponRule.setGmtCreate(LocalDateTime.now());
        int success=grouponRuleDao.addGrouponRule(grouponRule);
        if(success==0) return null;
        else return grouponRule;

    }

    @Override
    public List<Promotion> listGrouponRuleByGoodsId(Integer goodsId) {
        return grouponRuleDao.listGrouponRuleByGoodsId(goodsId);
    }

    @Override
    public List<Promotion> getGrouponRules() {
        return grouponRuleDao.getGroupRules();
    }

    @Override
    public Promotion updateGrouponRuleById(GrouponRule grouponRule) {
        if(grouponRule.getStartTime().isAfter(LocalDateTime.now())){
            grouponRule.setGmtModified(LocalDateTime.now());
        int success=grouponRuleDao.updateGrouponRuleById(grouponRule);
        if(success==0) return null;
        else return grouponRuleDao.getGrouponRuleById(grouponRule.getId());
        }
        else return null;

    }

    @Override
    public Promotion deleteGroupRuleById(Integer id) {
        if( grouponRuleDao.getGrouponRuleById(id).getPromotionStartTime().isAfter(LocalDateTime.now())){
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

    @Override
    public Promotion getPresaleRuleById(Integer id) {
        return presaleRuleDao.getPresaleRuleById(id);
    }

    @Override
    public Promotion addPresaleRule(PresaleRule presaleRule) {
        presaleRule.setBeDeleted(false);
        presaleRule.setGmtCreate(LocalDateTime.now());
       int success=presaleRuleDao.addPresaleRule(presaleRule);
       if(success==0) return null;
       else  return presaleRule;
    }

    @Override
    public List<Promotion> listPresaleRuleByGoodsId(Integer goodsId) {
        return presaleRuleDao.listPresaleRuleByGoodsId(goodsId);
    }

    @Override
    public Promotion updatePresaleRuleById(PresaleRule presaleRule) {
        if(presaleRule.getPromotionStartTime().isAfter(LocalDateTime.now())) {
            presaleRule.setGmtModified(LocalDateTime.now());
            int success = presaleRuleDao.updatePresaleRuleById(presaleRule);
            if (success == 0) return null;
            else return presaleRuleDao.getPresaleRuleById(presaleRule.getId());
        }
        else return null;
    }

    @Override
    public Promotion deletePresaleRuleById(Integer id) {
        if(presaleRuleDao.getPresaleRuleById(id).getPromotionStartTime().isAfter(LocalDateTime.now()))
        {
            PresaleRule presaleRule=new PresaleRule();
            presaleRule.setGmtModified(LocalDateTime.now());
            presaleRule.setId(id);
            presaleRule.setBeDeleted(true);
            int success=presaleRuleDao.updatePresaleRuleById(presaleRule);
            if(success==0) return null;
            else return presaleRuleDao.getPresaleRuleById(id);
        }

        else return null;
    }



}
