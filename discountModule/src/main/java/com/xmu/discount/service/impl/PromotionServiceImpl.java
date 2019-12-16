package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.dao.PromotionRuleDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.exception.PromotionNotFoundException;
import com.xmu.discount.exception.SeriousException;
import com.xmu.discount.exception.UnsupportException;
import com.xmu.discount.exception.UpdatedDataFailedException;
import com.xmu.discount.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public abstract class PromotionServiceImpl {

    @Autowired
    public GrouponRuleDao grouponRuleDao;

    @Autowired
    public PresaleRuleDao presaleRuleDao;

    @Autowired
    public CouponRuleDao couponRuleDao;


    @Autowired
    public CouponServiceImpl couponService;

    /**
     * 活动实效后的行为
     */
    public  abstract void toDoSomthingAfterDisable(PromotionRule promotionRule) throws SeriousException;





    /**
     * 设置失效
     * @param promotionRule
     */
    public void setDisabled(PromotionRule promotionRule) throws UpdatedDataFailedException {
        if(promotionRule.isOkToDisable())
        {
            String daoName=getDaoClassName(promotionRule);
            ((PromotionRuleDao)SpringContextUtil.getBean(daoName)).setDisable(promotionRule);
            this.toDoSomthingAfterDisable(promotionRule);
        }
    }

    /**
     * 根据id获取促销规则(controller 传优惠券名)
     * @param id
     * @return
     */
    public PromotionRule getPromotionById(Integer id,String promotionName) throws PromotionNotFoundException {
        return ((PromotionRuleDao)SpringContextUtil.getBean(promotionName+"DAO")).getPromotionRuleById(id);
    }

    /**
     * 删除促销活动
     * @param promotionRule
     * @return
     */
    public void deletePromotionById(PromotionRule promotionRule) throws UpdatedDataFailedException {
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
    public PromotionRule updatepromotionRule(PromotionRule promotionRule) throws UpdatedDataFailedException {
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
     * 获取订单应付金额
     * @param order
     * @return
     */
    public Order getPayment(Order order) throws SeriousException, UnsupportException {

        //获得订单商品当前的促销活动规则
        List<PromotionRule> promotionRules=this.listCurrentPromotionByGoodsId(order.getOrderItemList().get(0).getProduct().getGoodsId());
        //没有促销活动
        if(promotionRules.size()==0){
            //没有用优惠券
           if(order.getCouponId()==null){
               return order;
           }
           //有用优惠券
           else{
               Coupon coupon=couponService.findCouponById(order.getCouponId());
               coupon.cacuCouponPrice(order);
               return order;
           }
        }
        //促销活动大于1个
        else  if(promotionRules.size()>1){
                throw new SeriousException();
        }
        else{
           return  promotionRules.get(0).getPayment(order);
        }
    }

    /**
     * 通过id找到商品的所有促销活动
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
     * 通过id找到商品当前的促销活动
     * @param goodsId
     * @return
     */
    public List<PromotionRule> listCurrentPromotionByGoodsId(Integer goodsId){

        //商品的所有促销活动
        List<PromotionRule> promotionRules=this.listProimotionByGoodsId(goodsId);
        List<PromotionRule> res=new ArrayList<>();

        for(PromotionRule promotionRule:promotionRules){
            if(promotionRule.isInTime()){
                res.add(promotionRule);
            }
        }
        return res;
    }



    /**
     * 获得商品当前正在进行的促销活动
     * @param goodsId
     * @return
     */
    public PromotionRule getCurrentPromotionByGoodsId(Integer goodsId) throws PromotionNotFoundException ,SeriousException {
        List<PromotionRule> promotionRules=this.listProimotionByGoodsId(goodsId);//活动商品的所有促销活动
        for(PromotionRule p:promotionRules){
            LocalDateTime start=p.getpromotionRuleStartTime();
            LocalDateTime end=p.getPromotionEndTime();
            if(LocalDateTime.now().isBefore(start)||LocalDateTime.now().isAfter(end)) {
                promotionRules.remove(p);
            }
        }
        //没有促销活动
        if(promotionRules.size()==0){
            return null;
        }
        //促销活动大于1个
        else  if(promotionRules.size()>1){
            throw new SeriousException();

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
    public PromotionRule addPromotion(PromotionRule promotionRule) throws UpdatedDataFailedException, SeriousException {
        //获得商品的所有促销活动
        List<PromotionRule> promotionRules=this.listProimotionByGoodsId(promotionRule.getPromotionGoodsId());
        if(promotionRule.isOkToAdd(promotionRules)){
            //调用DAO层的add方法。
            String daoName=getDaoClassName(promotionRule);
            ((PromotionRuleDao)SpringContextUtil.getBean(daoName)).addPromotionRule(promotionRule);
        }
        return promotionRule;
    }






}
