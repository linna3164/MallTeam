package com.xmu.discount.domain.discount;

import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.exception.UnsupportException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 促销活动
 * @author Ming Qiu
 * @date 2019/11/26 10:39
 */
public abstract class PromotionRule implements Serializable {
//    private static final Logger logger = LoggerFactory.getLogger();

    public abstract Integer getId();

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
     * @param promotionRules
     * @return
     */
    public boolean isNoConflict(List<PromotionRule> promotionRules){
        for(PromotionRule promotion:promotionRules){
            //时间和其他促销活动无冲突
            if(this.getPromotionEndTime().isBefore(promotion.getpromotionRulestartTime())||this.getpromotionRulestartTime().isAfter(promotion.getPromotionEndTime())){
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否能加入 promotionRules(时间有效，并且和其他promotionRules没有冲突)
     * @param promotionRules
     * @return
     */
    public boolean isOkToAdd(List<PromotionRule> promotionRules){
        if(this.isValid()&&this.isNoConflict(promotionRules)){
            return true;
        }
        return false;
    }

    /**
     * 促销活动是否有效（开始时间小于结束时间）
     * @return
     */
    public boolean isValid(){
        return this.getpromotionRulestartTime().isBefore(this.getPromotionEndTime());
    }

    /**
     * 促销活动是否已开始
     * @return
     */
    public  boolean isAlreadyStart(){
        LocalDateTime now = LocalDateTime.now();
        return (this.getpromotionRulestartTime().isBefore(now));
    }


    /**
     * 促销活动是否在进行
     */
    public  boolean isGoingOn(){

        LocalDateTime now = LocalDateTime.now();
        return (this.getpromotionRulestartTime().isBefore(now) &&
                this.getPromotionEndTime().isAfter(now));
    }

    /**
     * 返回应付金额
     * @param order 订单
     * @return
     */
    public abstract Payment getPayment(Order order) throws UnsupportException;

    /**
     * 获得促销开始的时间
     * @return
     */
    public abstract LocalDateTime getpromotionRulestartTime();

    /**
     * 获得促销结束的时间
     * @return
     */
    public abstract LocalDateTime getPromotionEndTime();


    /**
     * 获取促销商品id
     * @return
     */
    public abstract Integer getPromotionGoodsId();

    /**
     * 判断这个promotion是否可添加
     * @param promotionRules
     * @return
     */
    public boolean isValid(List<PromotionRule> promotionRules){
        LocalDateTime startTime=this.getpromotionRulestartTime();
        LocalDateTime endTime=this.getPromotionEndTime();
        for(PromotionRule p:promotionRules){
            LocalDateTime s=p.getpromotionRulestartTime();
            LocalDateTime e=p.getPromotionEndTime();
            if(p.getpromotionRulestartTime().isAfter(this.getPromotionEndTime())||p.getPromotionEndTime().isBefore(this.getpromotionRulestartTime())) {
                continue;
            } else {
                return false;
            }
        }
        return  true;

    }

}
