package com.xmu.discount.domain.discount;


import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.OrderItem;
import com.xmu.discount.domain.others.domain.Payment;

import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Alias("grouponRule")
public class GrouponRule extends PromotionRule {

    private GrouponRulePo realObj;



    /**
     * 返回商品的价格
     * @param order 订单
     * @return
     */
    @Override
    public Payment getPayment(Order order) {
        //TODO:return  商品价格*数量
        List<OrderItem> oi = order.getOrderItemList();
        BigDecimal finalPrice = new BigDecimal(0);
        if(oi.size()!=1){
             return null;
        }
        else {
           OrderItem o=oi.get(0);
           int num = o.getNumber();
           BigDecimal price = o.getDealPrice();
           finalPrice = finalPrice.add(price.multiply(new BigDecimal(num)));
            Payment payment = new Payment();
            payment.setActualPrice(finalPrice);
            payment.setBeginTime(LocalDateTime.now());
            payment.setGmtCreate(LocalDateTime.now());
            return payment;
        }
    }


    /**
     *团购到点退费
     * @return
     */
    protected Payment refund(Order order){

    }

    @Override
    public LocalDateTime getpromotionRulestartTime() {
        return this.getStartTime();
    }

    @Override
    public LocalDateTime getPromotionEndTime() {
        return this.getEndTime();
    }


    /**
     * 获取促销商品id
     * @return
     */
    @Override
    public Integer getPromotionGoodsId() {
        return this.getGoodsId();
    }

    //******生成代码*****


    public GrouponRulePo getRealObj() {
        return realObj;
    }

    public void setRealObj(GrouponRulePo realObj) {
        this.realObj = realObj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrouponRule)) {
            return false;
        }
        GrouponRule that = (GrouponRule) o;
        return realObj.equals(that.realObj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(realObj);
    }

    public void setId(Integer id) {
        realObj.setId(id);
    }

    public void setEndTime(LocalDateTime endTime) {
        realObj.setEndTime(endTime);
    }

    public void setStartTime(LocalDateTime startTime) {
        realObj.setStartTime(startTime);
    }

    public void setStatusCode(Boolean statusCode) {
        realObj.setStatusCode(statusCode);
    }

    public void setGrouponLevelStragety(String grouponLevelStragety) {
        realObj.setGrouponLevelStragety(grouponLevelStragety);
    }

    public void setGoodsId(Integer goodsId) {
        realObj.setGoodsId(goodsId);
    }

    public LocalDateTime getEndTime() {
        return realObj.getEndTime();
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        realObj.setGmtCreate(gmtCreate);
    }

    public LocalDateTime getGmtModified() {
        return realObj.getGmtModified();
    }

    public Boolean getBeDeleted() {
        return realObj.getBeDeleted();
    }

    public Boolean getStatusCode() {
        return realObj.getStatusCode();
    }

    public String getGrouponLevelStragety() {
        return realObj.getGrouponLevelStragety();
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        realObj.setGmtModified(gmtModified);
    }

    public boolean canEqual(Object other) {
        return realObj.canEqual(other);
    }

    public void setBeDeleted(Boolean beDeleted) {
        realObj.setBeDeleted(beDeleted);
    }

    public Integer getId() {
        return realObj.getId();
    }

    public LocalDateTime getGmtCreate() {
        return realObj.getGmtCreate();
    }

    public LocalDateTime getStartTime() {
        return realObj.getStartTime();
    }

    public Integer getGoodsId() {
        return realObj.getGoodsId();
    }
}
