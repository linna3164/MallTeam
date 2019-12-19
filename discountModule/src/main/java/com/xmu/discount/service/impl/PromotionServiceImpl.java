package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.dao.PromotionRuleDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.GoodsPo;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.exception.*;
import com.xmu.discount.inter.GoodsFeign;
import com.xmu.discount.util.JacksonUtil;
import com.xmu.discount.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public GoodsFeign goodsFeign;

    @Autowired
    public CouponServiceImpl couponService;

    /**
     * 活动实效后的行为
     */
    public  abstract void toDoSomthingAfterDisable(PromotionRule promotionRule) throws CouponRuleSetDisableFailException;


    /**
     * 获取某种促销规则列表(不带商品)
     * @param promotionName
     * @return
     */
    public List<? extends PromotionRule> listPromotionRuleOfType(String promotionName){
        return ((PromotionRuleDao)SpringContextUtil.getBean(promotionName+"Dao")).listPromotions();
    }


    /**
     * 用户查看促销规则列表(带商品)--(要用的)
     * @return
     */
    public List<? extends PromotionRule> listPromotionRuleOfTypeInprocessWithGoods(String promotionName){
        List<? extends PromotionRule> promotionRules=((PromotionRuleDao)SpringContextUtil.getBean(promotionName+"Dao")).listPromotions();

        for(PromotionRule promotionRule:promotionRules){
            if(promotionRule.getActiveStatus().equals(PromotionRule.ActiveStatus.INPROCESS)){
                Object retObj = goodsFeign.getGoodsById(promotionRule.getGoodsId());

                promotionRule.setGoodsPo(JacksonUtil.getBack(retObj, GoodsPo.class));
            }
        }
        return promotionRules;

    }

    private static<T> T getBack(Object result,Class<T>clazz){
        String theResult= JacksonUtil.toJson(result);
        String errno= JacksonUtil.parseString(theResult,"errno");
        String success="0.0";
        if(!success.equals(errno)){
            return null;
        }
        return JacksonUtil.parseObject(theResult,"data",clazz);
    }


//    /**
//     * 管理员看到某种商品的某种促销活动规则(带商品)（要用的）
//     * @return
//     */
//    public List<? extends PromotionRule> listPromotionRuleOfTypeOfGoodsIdWithGoods(String promotionName){
//        List<? extends PromotionRule> promotionRules=grouponRuleDao.listPromotions();
//        ((PromotionRuleDao)SpringContextUtil.getBean(promotionName+"Dao")).listPromotionRuleByGoodsId();
//        for(PromotionRule promotionRule:promotionRules){
//            promotionRule.setGoodsPo(goodsFeign.getGoodsById(promotionRule.getId()));
//        }
//
//        return promotionRules;
//    }

    /**
     * 管理员看到的某种促销活动规则(带商品)（要用的）
     * @return
     */
    public List<? extends PromotionRule> listPromotionRuleOfTypeWithGoods(String promotionName){
        List<? extends PromotionRule> promotionRules=this.listPromotionRuleOfType(promotionName);
        for(PromotionRule promotionRule:promotionRules){

            Object retObj = goodsFeign.getGoodsById(promotionRule.getGoodsId());
            GoodsPo goodsPo=JacksonUtil.getBack(retObj, GoodsPo.class);

            promotionRule.setGoodsPo(goodsPo);
        }

        return promotionRules;
    }


    /**
     * 设置失效
     * @param promotionRule
     */
    public void setDisabled(PromotionRule promotionRule) throws PresaleRuleSetDisableFailException, GrouponRuleUpdateFailException, CouponRuleSetDisableFailException {
        if(promotionRule.beOkToDisable())
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
    public PromotionRule getPromotionById(Integer id,String promotionName) throws PresaleRuleUnValidException {
        return ((PromotionRuleDao)SpringContextUtil.getBean(promotionName+"Dao")).getPromotionRuleById(id);
    }

    /**
     * 删除促销活动
     * @param promotionRule
     * @return
     */
    public boolean deletePromotionById(PromotionRule promotionRule) throws PresaleRuleDeleteFailException, CouponRuleDeleteFailException, GrouponRuleDeleteFailException, GrouponRuleUpdateFailException {
        if(promotionRule.beOkToDelete())
        {
            String daoName=getDaoClassName(promotionRule);
            return ((PromotionRuleDao)SpringContextUtil.getBean(daoName)).deletePromotionRuleById(promotionRule.getId());
        }
        return false;
    }

    /**
     * 修改促销活动
     * @param promotionRule
     * @return
     */
    public PromotionRule updatepromotionRule(PromotionRule promotionRule) throws PresaleRuleUnValidException, PresaleRuleUpdateFailException, GrouponRuleUpdateFailException, CouponRuleUpdateFailException {
        String daoName=getDaoClassName(promotionRule);

        PromotionRule pre=this.getPromotionById(promotionRule.getId(),this.getLowerRuleName(promotionRule));
        if (pre==null){
            return null;
        }
        if(pre.beOkToUpdate()){
            ((PromotionRuleDao)SpringContextUtil.getBean(daoName)).updatePromotionRuleById(promotionRule);
            return this.getPromotionById(promotionRule.getId(),this.getLowerRuleName(promotionRule));
        }
        else {
            return null;
        }
    }



    /**
     * 获取订单应付金额
     * @param order
     * @return
     */
    public Order getPayment(Order order)    {

        //获得订单商品当前的促销活动规则
        PromotionRule promotionRule=this.listCurrentPromotionByGoodsId(order.getOrderItemList().get(0).getProduct().getGoodsId());
        //没有促销活动
        if(promotionRule==null){
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
        else{
           return  promotionRule.getPayment(order);
        }
    }

    /**
     * 通过id找到商品的所有促销活动
     * @param goodsId
     * @return 团购加预售
     */
    public List<? extends PromotionRule> listProimotionByGoodsId(Integer goodsId){
        List<? extends PromotionRule> grouponRule=grouponRuleDao.listPromotionRuleByGoodsId(goodsId);
        List<? extends PromotionRule> presaleRule=presaleRuleDao.listPromotionRuleByGoodsId(goodsId);

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
    public PromotionRule listCurrentPromotionByGoodsId(Integer goodsId)    {
        //活动商品的所有促销活动
        List<? extends PromotionRule> promotionRules=this.listProimotionByGoodsId(goodsId);
        for(PromotionRule promotionRule:promotionRules){
            if(promotionRule.getActiveStatus().equals(PromotionRule.ActiveStatus.INPROCESS)) {
                promotionRules.remove(promotionRule);
            }
        }
        //没有促销活动
        if(promotionRules.size()==0){
            return null;
        }
        //促销活动大于1个
        else  if(promotionRules.size()>1){
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
    public PromotionRule addPromotion(PromotionRule promotionRule) throws PresaleRuleAddFailException, CouponRuleAddFailException, GrouponRuleAddFailException {
        //获得商品的所有促销活动
        List<? extends PromotionRule> promotionRules=this.listProimotionByGoodsId(promotionRule.getPromotionGoodsId());
        //筛选出正在进行的或者还没开始的
        List<PromotionRule> res=new ArrayList<>();
        for(PromotionRule pro:promotionRules){
            PromotionRule.ActiveStatus activeStatus=pro.getActiveStatus();
            if(activeStatus.equals(PromotionRule.ActiveStatus.INPROCESS)||activeStatus.equals(PromotionRule.ActiveStatus.NOTSTART)){
                continue;
            }
            else {
                res.add(pro);
            }
        }

        System.out.println("into addPromotion");
        if(promotionRule.beOkToAdd(res)){
            //调用DAO层的add方法。
            String daoName=getDaoClassName(promotionRule);
            ((PromotionRuleDao)SpringContextUtil.getBean(daoName)).addPromotionRule(promotionRule);
        }
        return promotionRule;
    }



    public String getDaoClassName(PromotionRule promotionRule){
        String name=promotionRule.getClass().getSimpleName()+"Dao";
        return (new StringBuilder()).append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).toString();
    }

    public String getLowerRuleName(PromotionRule promotionRule){
        String name=promotionRule.getClass().getSimpleName();
        return (new StringBuilder()).append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).toString();
    }



}
