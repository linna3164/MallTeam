package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponRuleDao;
import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.dao.PromotionRuleDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.exception.PromotionNotFoundException;
import com.xmu.discount.exception.SeriousException;
import com.xmu.discount.exception.UnsupportException;
import com.xmu.discount.exception.UpdatedDataFailedException;
import com.xmu.discount.inter.GoodsFeign;
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

//    @Autowired
//    GoodsFeign goodsFeign;

    @Autowired
    public CouponServiceImpl couponService;

    /**
     * 活动实效后的行为
     */
    public  abstract void toDoSomthingAfterDisable(PromotionRule promotionRule) throws SeriousException;


    /**
     * 获取某种促销规则列表(不带商品)
     * @param promotionName
     * @return
     */
    public List<? extends PromotionRule> listPromotionRuleOfType(String promotionName){
        return ((PromotionRuleDao)SpringContextUtil.getBean(promotionName+"Dao")).listPromotions();
    }


//    /**
//     * 用户查看促销规则列表(带商品)
//     * @return
//     */
//    public List<? extends PromotionRule> listPromotionRuleOfTypeInprocessWithGoods(String promotionName){
//        List<? extends PromotionRule> promotionRules=((PromotionRuleDao)SpringContextUtil.getBean(promotionName+"Dao")).listPromotions();
//
//        for(PromotionRule promotionRule:promotionRules){
//            if(promotionRule.getActiveStatus().equals(PromotionRule.ActiveStatus.INPROCESS)){
//                promotionRule.setGoods(goodsFeign.getGoodsById(promotionRule.getId()));
//            }
//        }
//        return promotionRules;
//
//    }

//    /**
//     * 管理员看到的某种促销活动规则(带商品)
//     * @return
//     */
//    public List<? extends PromotionRule> listPromotionRuleOfTypeWithGoods(String promotionName){
//        List<? extends PromotionRule> promotionRules=grouponRuleDao.listPromotions();
//        for(PromotionRule promotionRule:promotionRules){
//            promotionRule.setGoods(goodsFeign.getGoodsById(promotionRule.getId()));
//
//        }
//
//        return promotionRules;
//    }

    /**
     * 设置失效
     * @param promotionRule
     */
    public void setDisabled(PromotionRule promotionRule) throws UpdatedDataFailedException, SeriousException {
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
        return ((PromotionRuleDao)SpringContextUtil.getBean(promotionName+"Dao")).getPromotionRuleById(id);
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
        String name=promotionRule.getClass().getSimpleName()+"Dao";
        return (new StringBuilder()).append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).toString();
    }

    /**
     * 获取订单应付金额
     * @param order
     * @return
     */
    public Order getPayment(Order order) throws SeriousException, UnsupportException, PromotionNotFoundException {

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

//    /**
//     * 获取某种状态的促销活动--带商品
//     * @param promotionName
//     * @param activeStatus
//     * @return
//     */
//    public List<PromotionRule> listPromotionRuleOfTypeAndStatusByGoodsId(Integer goodsId,String promotionName, PromotionRule.ActiveStatus activeStatus){
//        List<PromotionRule> promotionRules=this.listPromotionRuleOfType(promotionName);
//        List<PromotionRule> res=new ArrayList<>();
//        for(PromotionRule promotionRule:promotionRules){
//            if(promotionRule.getActiveStatus().equals(PromotionRule.ActiveStatus.INPROCESS)){
//                res.add(promotionRule);
//            }
//        }
//        return res;
//    }


    /**
     * 获得商品当前正在进行的促销活动
     * @param goodsId
     * @return
     */
    public PromotionRule listCurrentPromotionByGoodsId(Integer goodsId) throws PromotionNotFoundException ,SeriousException {
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
        List<? extends PromotionRule> promotionRules=this.listProimotionByGoodsId(promotionRule.getPromotionGoodsId());

        if(promotionRule.isOkToAdd(promotionRules)){
            //调用DAO层的add方法。
            String daoName=getDaoClassName(promotionRule);
            ((PromotionRuleDao)SpringContextUtil.getBean(daoName)).addPromotionRule(promotionRule);
        }
        return promotionRule;
    }

//
//    /**
//     * 获取某种状态的促销活动--带商品
//     * @param promotionName
//     * @param activeStatus
//     * @return
//     */
//    public List<PromotionRule> listPromotionRuleOfTypeAndStatus(String promotionName, PromotionRule.ActiveStatus activeStatus){
//        List<PromotionRule> promotionRules=this.listPromotionRuleOfType(promotionName);
//        List<PromotionRule> res=new ArrayList<>();
//        for(PromotionRule promotionRule:promotionRules){
//            if(promotionRule.getActiveStatus().equals(PromotionRule.ActiveStatus.INPROCESS)){
//                res.add(promotionRule);
//            }
//        }
//        return res;
//    }





}
