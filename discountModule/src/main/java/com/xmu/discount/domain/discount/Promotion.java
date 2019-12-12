package com.xmu.discount.domain.discount;

import com.xmu.discount.domain.others.OrderDto;
import com.xmu.discount.standard.Order;
import com.xmu.discount.standard.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xmu.oomall.domain.order.Order;
import xmu.oomall.domain.payment.Payment;
import xmu.oomall.util.JacksonUtil;

import java.io.Serializable;

/**
 * 促销活动
 * @author Ming Qiu
 * @date 2019/11/26 10:39
 */
public abstract class Promotion implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Order.class);


    /**
     * 判断当前促销活动是否在进行
     */
    protected abstract boolean isValiable();

    /**
     * 返回应付金额
     * @param order 订单
     * @return
     */
    protected abstract Payment getPayment(OrderDto order);



//    private PromotionPo realObj;
//
//    /**
//     * 计算活动付款
//     * @param order 订单
//     * @param maxPayTime 付款间隔
//     * @return 付款列表
//     */
//    public List<Payment> getPayment(Order order, Integer maxPayTime){
//        return this.getPayStrategy().getPayment(order, maxPayTime);
//    }
//
//    public AbstractPaymentStrategy getPayStrategy() {
//        String jsonString = realObj.getPayStrategy();
//        String strategyName = JacksonUtil.parseString(jsonString, "name");
//
//        AbstractPaymentStrategy strategy = null;
//        try {
//            strategy = (AbstractPaymentStrategy) JacksonUtil.parseObject(jsonString, "obj", Class.forName(strategyName));
//        } catch (ClassNotFoundException e) {
//            logger.error(e.getMessage(), e);
//        }
//
//        return strategy;
//    }
//
//    public void setPayStrategy(AbstractPaymentStrategy payStrategy) {
//        Map<String, Object> jsonObj = new HashMap<String, Object>(2);
//        jsonObj.put("name", payStrategy.getClass().getName());
//        jsonObj.put("obj",payStrategy);
//        String jsonString = JacksonUtil.toJson(jsonObj);
//        realObj.setPayStrategy(jsonString);
//    }
//
//
//    public Promotion() {
//
//    }
//
//    public Promotion(PromotionPo realObj){
//        this.realObj = realObj;
//    }
//
//    public PromotionPo getRealObj() {
//        return realObj;
//    }
//
//    @Override
//    public String toString() {
//        return realObj.toString();
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        return realObj.equals(o);
//    }
//
//    @Override
//    public int hashCode() {
//        return realObj.hashCode();
//    }
//
//    public Integer getId() {
//        return realObj.getId();
//    }
//
//    public void setId(Integer id) {
//        realObj.setId(id);
//    }
//
//    public LocalDateTime getBeginTime() {
//        return realObj.getBeginTime();
//    }
//
//    public void setBeginTime(LocalDateTime beginTime) {
//        realObj.setBeginTime(beginTime);
//    }
//
//    public LocalDateTime getEndTime() {
//        return realObj.getEndTime();
//    }
//
//    public void setEndTime(LocalDateTime endTime) {
//        realObj.setEndTime(endTime);
//    }
//
//    public String getPromotionSn() {
//        return realObj.getPromotionSn();
//    }
//
//    public void setPromotionSn(String promotionSn) {
//        realObj.setPromotionSn(promotionSn);
//    }
//
//    public String getDescr() {
//        return realObj.getDescr();
//    }
//
//    public void setDescr(String descr) {
//        realObj.setDescr(descr);
//    }
//
//
//    public LocalDateTime getAddTime() {
//        return realObj.getAddTime();
//    }
//
//    public void setAddTime(LocalDateTime addTime) {
//        realObj.setAddTime(addTime);
//    }
//
//    public LocalDateTime getUpdateTime() {
//        return realObj.getUpdateTime();
//    }
//
//    public void setUpdateTime(LocalDateTime updateTime) {
//        realObj.setUpdateTime(updateTime);
//    }
//
//    public Boolean getBeDeleted() {
//        return realObj.getBeDeleted();
//    }
//
//    public void setBeDeleted(Boolean beDeleted) {
//        realObj.setBeDeleted(beDeleted);
//    }
}
