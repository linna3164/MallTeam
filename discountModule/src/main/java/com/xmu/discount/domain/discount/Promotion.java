package com.xmu.discount.domain.discount;

import com.xmu.discount.domain.others.Order;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.standard.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xmu.oomall.domain.order.Order;
import xmu.oomall.domain.payment.Payment;
import xmu.oomall.util.JacksonUtil;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 促销活动
 * @author Ming Qiu
 * @date 2019/11/26 10:39
 */
public abstract class Promotion implements Serializable {
//    private static final Logger logger = LoggerFactory.getLogger();

    /**
     * 促销活动是否可修改
     * @return
     */
    public boolean isOkToUpdate(){
        if(this.isAlreadyStart()){
            return false;
        }
        return true;
    }

    /**
     * 促销活动是否可删除
     * @return
     */
    public boolean isOkToDelete(){
        if(this.isAlreadyStart()){
            return false;
        }
        return true;
    }

    /**
     * 促销活动和其他活动是否有冲突
     * @param promotions
     * @return
     */
    public boolean isNoConflict(List<Promotion> promotions){
        for(Promotion promotion:promotions){
            //时间和其他促销活动无冲突
            if(this.getPromotionEndTime().isBefore(promotion.getPromotionStartTime())||this.getPromotionStartTime().isAfter(promotion.getPromotionEndTime())){
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否能加入 promotions(时间有效，并且和其他promotions没有冲突)
     * @param promotions
     * @return
     */
    public boolean isOkToAdd(List<Promotion> promotions){
        if(this.isValid()&&this.isNoConflict(promotions)){
            return true;
        }
        return false;
    }

    /**
     * 促销活动是否有效（开始时间小于结束时间）
     * @return
     */
    public boolean isValid(){
        return this.getPromotionStartTime().isBefore(this.getPromotionEndTime());
    }

    /**
     * 促销活动是否已开始
     * @return
     */
    public  boolean isAlreadyStart(){
        LocalDateTime now = LocalDateTime.now();
        return (this.getPromotionStartTime().isBefore(now));
    }


    /**
     * 促销活动是否在进行
     */
    public  boolean isGoingOn(){

        LocalDateTime now = LocalDateTime.now();
        return (this.getPromotionStartTime().isBefore(now) &&
                this.getPromotionEndTime().isAfter(now));
    }

    /**
     * 返回应付金额
     * @param order 订单
     * @return
     */
    public abstract Payment getPayment(Order order);

    /**
     * 获得促销开始的时间
     * @return
     */
    public abstract LocalDateTime getPromotionStartTime();

    /**
     * 获得促销结束的时间
     * @return
     */
    public   abstract LocalDateTime getPromotionEndTime();


    /**
     * 获取促销商品id
     * @return
     */
    public abstract Integer getPromotionGoodsId();

    /**
     * 判断这个promotion是否可添加
     * @param promotions
     * @return
     */
    public boolean isValid(List<Promotion> promotions){
        LocalDateTime startTime=this.getPromotionStartTime();
        LocalDateTime endTime=this.getPromotionEndTime();
        for(Promotion p:promotions){
            LocalDateTime s=p.getPromotionStartTime();
            LocalDateTime e=p.getPromotionEndTime();
            if(p.getPromotionStartTime().isAfter(this.getPromotionEndTime())||p.getPromotionEndTime().isBefore(this.getPromotionStartTime())) {
                continue;
            } else {
                return false;
            }
        }
        return  true;

    }

}
