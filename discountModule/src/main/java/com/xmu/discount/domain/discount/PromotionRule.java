package com.xmu.discount.domain.discount;

import com.xmu.discount.domain.others.domain.GoodsPo;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.exception.SubmitOrderFailException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 促销活动
 * @author Ming Qiu
 * @date 2019/11/26 10:39
 */
public abstract class PromotionRule implements Serializable {

    /**
     * 获取id
     * @return
     */
    public abstract Integer getId();



    /**
     * 是否可设置失效
     * @return
     */
    public boolean beOkToDisable(){
        if(this.getActiveStatus().equals(ActiveStatus.WAITFINISH)||this.getActiveStatus().equals(ActiveStatus.INPROCESS)){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 获取促销活动的状态
     * @return
     */
    public ActiveStatus getActiveStatus() {
        //失效
        if(this.beDisabled()){
            return ActiveStatus.DISABLED;
        }
        else if(this.beWaitFinish()){
            return ActiveStatus.WAITFINISH;
        }
        else if(this.beInTime()){
            return ActiveStatus.INPROCESS;
        }
        else if(!this.beAlreadyStart()){
            return ActiveStatus.NOTSTART;
        }
        else if(this.beFinished()){
            return ActiveStatus.DONE;
        }
        return ActiveStatus.DISABLED;
    }

    /**
     * 促销活动是否可修改
     * @return
     */
    public boolean beOkToUpdate(){
        if(this.getActiveStatus().equals(ActiveStatus.WAITFINISH)||this.getActiveStatus().equals(ActiveStatus.INPROCESS)){
            return false;
        }
        else{
            return true;
        }
    }


    /**
     * 判断活动是否等待结束
     * @return
     */
    public abstract  boolean beWaitFinish();


    /**t
     * 判断活动是否失效
     * @return
     */
    public abstract boolean beDisabled();

    /**
     * 促销活动是否可删除
     * @return
     */
    public boolean beOkToDelete(){
        if(this.getActiveStatus().equals(ActiveStatus.WAITFINISH)||this.getActiveStatus().equals(ActiveStatus.INPROCESS)){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * 促销活动和其他活动是否有冲突
     * @param promotionRules
     * @return
     */
    public boolean beNoConflict(List<? extends PromotionRule> promotionRules){
        if(promotionRules==null){
            return true;
        }
        for(PromotionRule promotion:promotionRules){
            //时间和其他促销活动无冲突
            if(this.getPromotionEndTime().isBefore(promotion.getpromotionRuleStartTime())||this.getpromotionRuleStartTime().isAfter(promotion.getPromotionEndTime())){

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
    public boolean beOkToAdd(List<? extends PromotionRule> promotionRules){

        System.out.println("into beOkToAdd");
        LocalDateTime now=LocalDateTime.now();

//        &&this.getpromotionRuleStartTime().isBefore(now)
        if(this.beValid()&&this.beNoConflict(promotionRules)){
            return true;
        }
        return false;
    }

    /**
     * 促销活动是否有效（开始时间小于结束时间）
     * @return
     */
    public boolean beValid(){
        return this.getpromotionRuleStartTime().isBefore(this.getPromotionEndTime());
    }

    /**
     * 促销活动是否已start
     * @return
     */
    public  boolean beAlreadyStart(){
        LocalDateTime now = LocalDateTime.now();
        return (this.getpromotionRuleStartTime().isBefore(now));
    }

    /**
     * 促销活动是否end
     * @return
     */
    public boolean beAlreadyEnd(){
        LocalDateTime now = LocalDateTime.now();
        return (this.getPromotionEndTime().isBefore(now));
    }


    /**
     * 促销活动是否结束(团购活动需要重写改方法)
     * @return
     */
    public boolean beFinished(){
        LocalDateTime now = LocalDateTime.now();
        return (this.getPromotionEndTime().isBefore(now));
    }

    /**
     * 促销活动是否在进行
     */
    public  boolean beInTime(){

        LocalDateTime now = LocalDateTime.now();
        return (this.getpromotionRuleStartTime().isBefore(now) &&
                this.getPromotionEndTime().isAfter(now));
    }

    /**
     * 返回应付金额
     * @param order 订单
     * @return
     * @throws SubmitOrderFailException
     */
    public abstract Order getPayment(Order order) throws SubmitOrderFailException;

    /**
     * 获得促销开始的时间
     * @return
     */
    public abstract LocalDateTime getpromotionRuleStartTime();

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
    public boolean beValid(List<PromotionRule> promotionRules){
        LocalDateTime startTime=this.getpromotionRuleStartTime();
        LocalDateTime endTime=this.getPromotionEndTime();
        for(PromotionRule p:promotionRules){
            LocalDateTime s=p.getpromotionRuleStartTime();
            LocalDateTime e=p.getPromotionEndTime();
            if(p.getpromotionRuleStartTime().isAfter(this.getPromotionEndTime())||p.getPromotionEndTime().isBefore(this.getpromotionRuleStartTime())) {
                continue;
            } else {
                return false;
            }
        }
        return  true;

    }



    /**
     * 促销活动的活动状态
     */
    public enum ActiveStatus {
        /**
         * 未开始
         */
        NOTSTART("未开始", 0),
        /**
         * 进行中
         */
        INPROCESS("进行中", 1),
        /**
         *未完成
         */
        WAITFINISH("未完成",2),
        /**"
         * 已完成
         */
        DONE("已完成", 3),
        /**
         * 失效
         */
        DISABLED("失效", 4);

        /**
         * 值
         */
        private final Integer value;

        /**
         * 名称
         */
        private final String name;

        /**
         * 构造函数
         *
         * @param name  名称
         * @param value 值
         */
        ActiveStatus(String name, Integer value) {
            this.value = value;
            this.name = name;
        }

        /**
         * 获得值
         *
         * @return 值
         */
        public Integer getValue() {
            return this.value;
        }

        /**
         * 获得名称
         *
         * @return 名
         */
        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    private ActiveStatus activeStatus;

    public void setActiveStatus(ActiveStatus activeStatus) {
        this.activeStatus = activeStatus;
    }


    private GoodsPo goodsPo;

    public GoodsPo getGoodsPo() {
        return goodsPo;
    }

    public void setGoodsPo(GoodsPo goods) {
        this.goodsPo = goods;
    }


    /**
     * 获取商品id
     * @return
     */
    public abstract Integer getGoodsId();

    /**
     * 设置商品id
     * @param goodsId
     */
    public abstract void setGoodsId(Integer goodsId) ;
}
