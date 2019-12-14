package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.dao.PromotionDao;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.Promotion;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.service.DiscountService;
import com.xmu.discount.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

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
     * @param promotion
     * @return
     */
    @Override
    public void deletePromotionById(Promotion promotion){
        if(promotion.isOkToDelete())
        {
            //TODO：调用DAO层的delete方法
        }
    }

    /**
     * 修改促销活动
     * @param promotion
     * @return
     */
    public Promotion updatePromotion(Promotion promotion){
        if(promotion.isOkToUpdate()){
            //TODO：调用DAO层的update方法
            String daoName=getDaoClassName(promotion);
            ((PromotionDao)SpringContextUtil.getBean(daoName)).updatePromotionRuleById(promotion);

        }
    }

    public String getDaoClassName(Promotion promotion){
        return promotion.getClass().getSimpleName()+"DAO";
    }

    /**
     *
     * @param order
     * @return
     */
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
        //获得商品的所有促销活动
        List<Promotion> promotions=this.listProimotionByGoodsId(promotion.getPromotionGoodsId());
        if(promotion.isOkToAdd(promotions)){
            //调用DAO层的add方法。
        }
        return promotion;
    }






}
