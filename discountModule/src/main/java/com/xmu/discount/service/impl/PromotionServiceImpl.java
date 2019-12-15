package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.dao.PromotionRuleDao;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.service.PromotionService;
import com.xmu.discount.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public  class PromotionServiceImpl {

    @Autowired
    public GrouponRuleDao grouponRuleDao;

    @Autowired
    public PresaleRuleDao presaleRuleDao;

    @Autowired
    public CouponRuleDao couponRuleDao;

    /**
     * 活动实效后的行为
     */
//    public  abstract void toDoSomthing(PromotionRule promotionRule);

    /**
     * 设置活动实效
     * @param promotionRule
     */
    public void setUnValid(PromotionRule promotionRule) {
        //只有进行中的促销活动规则可以设置实效
//        if(promotionRule.isGoingOn()){
//            //
//            this.toDoSomthing(promotionRule);
//        }
    }

    /**
     * 根据id获取促销规则(controller 传优惠券名)
     * @param id
     * @return
     */
    public PromotionRule getPromotionById(Integer id,String promotionName) {
        return ((PromotionRuleDao)SpringContextUtil.getBean(promotionName+"DAO")).getPromotionRuleById(id);
    }

    /**
     * 删除促销活动
     * @param promotionRule
     * @return
     */
    public void deletePromotionById(PromotionRule promotionRule){
        if(promotionRule.isOkToDelete())
        {
            String daoName=getDaoClassName(promotionRule);
            ((PromotionRuleDao)SpringContextUtil.getBean(daoName)).deletePromotionRuleById(promotionRule.getId());
        }
    }

    /**
     * 修改促销活动
     * @param promotionRule
     * @return
     */
    public PromotionRule updatepromotionRule(PromotionRule promotionRule){
        if(promotionRule.isOkToUpdate()){
            String daoName=getDaoClassName(promotionRule);
            ((PromotionRuleDao)SpringContextUtil.getBean(daoName)).updatePromotionRuleById(promotionRule);
        }
        return promotionRule;
    }

    public String getDaoClassName(PromotionRule promotionRule){
        return promotionRule.getClass().getSimpleName()+"DAO";
    }

    /**
     *
     * @param order
     * @return
     */
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
     * @return 团购加预售
     */
    public List<PromotionRule> listProimotionByGoodsId(Integer goodsId){
        List<PromotionRule> grouponRule=grouponRuleDao.listPromotionRuleByGoodsId(goodsId);
        List<PromotionRule> presaleRule=presaleRuleDao.listPromotionRuleByGoodsId(goodsId);

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
    public PromotionRule getCurrentPromotionByGoodsId(Integer goodsId) {
        List<PromotionRule> promotionRules=this.listProimotionByGoodsId(goodsId);//活动商品的所有促销活动
        for(PromotionRule p:promotionRules){
            LocalDateTime start=p.getpromotionRulestartTime();
            LocalDateTime end=p.getPromotionEndTime();
            if(LocalDateTime.now().isBefore(start)||LocalDateTime.now().isAfter(end)) {
                promotionRules.remove(p);
            }
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
     * @param promotionRule
     * @return
     */
    public PromotionRule addPromotion(PromotionRule promotionRule){
        //获得商品的所有促销活动
        List<PromotionRule> promotionRules=this.listProimotionByGoodsId(promotionRule.getPromotionGoodsId());
        if(promotionRule.isOkToAdd(promotionRules)){
            //调用DAO层的add方法。
            String daoName=getDaoClassName(promotionRule);
            ((PromotionRuleDao)SpringContextUtil.getBean(daoName)).updatePromotionRuleById(promotionRule);
        }
        return promotionRule;
    }






}
