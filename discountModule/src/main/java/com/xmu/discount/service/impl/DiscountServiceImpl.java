package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.dao.PromotionRuleDao;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.service.DiscountService;
import com.xmu.discount.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiscountServiceImpl implements DiscountService {

    @Autowired
    GrouponRuleDao grouponRuleDao;

    @Autowired
    PresaleRuleDao presaleRuleDao;


    /**
     *
     */

    /**
     * 删除促销活动
     * @param promotionRule
     * @return
     */
    @Override
    public void deletePromotionById(PromotionRule promotionRule){
        if(promotionRule.isOkToDelete())
        {
            //TODO：调用DAO层的delete方法
        }
    }

    /**
     * 修改促销活动
     * @param promotionRule
     * @return
     */
    public PromotionRule updatepromotionRule(PromotionRule promotionRule){
        if(promotionRule.isOkToUpdate()){
            //TODO：调用DAO层的update方法
            String daoName=getDaoClassName(promotionRule);
            ((PromotionRuleDao)SpringContextUtil.getBean(daoName)).updatePromotionRuleById(promotionRule);

        }
    }

    public String getDaoClassName(PromotionRule promotionRule){
        return promotionRule.getClass().getSimpleName()+"DAO";
    }

    /**
     *
     * @param order
     * @return
     */
    @Override
    public Payment getPayment(Order order) {
        List<PromotionRule> promotionRules=this.listProimotionByGoodsId(order.getOrderItemList().get(0).getProduct().getGoodsId());
        if(promotionRules.size()==0){//没有促销活动
            //TODO:报错
           return null;
        }
        else  if(promotionRules.size()>1){//促销活动大于1个
            //TODO:报错
            return null;
        }
        else{
           return  promotionRules.get(0).getPayment(order);
        }
    }

    /**
     * 通过id找到商品的促销活动
     * @param goodsId
     * @return
     */
    @Override
    public List<PromotionRule> listProimotionByGoodsId(Integer goodsId){
        List<PromotionRule> grouponRule=grouponRuleDao.listGrouponRuleByGoodsId(goodsId);
        List<PromotionRule> presaleRule=presaleRuleDao.listPresaleRuleByGoodsId(goodsId);

        List<PromotionRule>promotionRules=new ArrayList<>();
        promotionRules.addAll(grouponRule);
        promotionRules.addAll(presaleRule);

        return promotionRules;
    }



    /**
     * 获得商品当前正在进行的促销活动
     * @param goodsId
     * @return
     */
    @Override
    public PromotionRule getCurrentPromotionByGoodsId(Integer goodsId) {
        List<PromotionRule> promotionRules=this.listProimotionByGoodsId(goodsId);//活动商品的所有促销活动
        //TODO:结束时间大于当前时间，开始时间小于当前时间？？不确定这个逻辑是否正确
        for(PromotionRule p:promotionRules){
            LocalDateTime start=p.getpromotionRulestartTime();
            LocalDateTime end=p.getPromotionEndTime();
            if(LocalDateTime.now().isBefore(start)||LocalDateTime.now().isAfter(end))
                promotionRules.remove(p);
        }
        if(promotionRules.size()==0){//没有促销活动
            //TODO:报错
            return null;
        }
        else  if(promotionRules.size()>1){//促销活动大于1个
            //TODO:报错

            return null;
        }
        else{
            return  promotionRules.get(0);
        }
    }

    /**
     * 添加优惠活动
     * @param promotion
     * @return
     */
    @Override
    public PromotionRule addPromotion(PromotionRule promotion){
        //获得商品的所有促销活动
        List<PromotionRule> promotionRules=this.listProimotionByGoodsId(promotion.getPromotionGoodsId());
        if(promotion.isOkToAdd(promotionRules)){
            //调用DAO层的add方法。
        }
        return promotion;
    }






}
