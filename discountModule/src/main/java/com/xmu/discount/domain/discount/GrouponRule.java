package com.xmu.discount.domain.discount;

import com.xmu.discount.domain.others.Order;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.standard.Payment;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Objects;

@Alias("grouponRule")
public class GrouponRule extends Promotion {

    private GrouponRulePo realObj;

    /**
     * 团购是否在进行中
     * @param order
     * @return
     */
    protected boolean isValiable(Order order) {

        LocalDateTime now = LocalDateTime.now();
        return (this.getStartTime().isBefore(now) &&
                this.getEndTime().isAfter(now));

    }

    /**
     * 返回商品的价格
     * @param order 订单
     * @return
     */
    @Override
    protected Payment getPayment(Order order) {
        //TODO:return  商品价格*数量

    }


    /**
     *团购到点退费
     * @return
     */
    protected Payment refund(Order order){
        Order
    }

    @Override
    protected LocalDateTime getPromotionStartTime() {
        return this.getStartTime();
    }

    @Override
    protected LocalDateTime getPromotionEndTime() {
        return this.getEndTime();
    }


    /**
     * 获取促销商品id
     * @return
     */
    @Override
    protected Integer getPromotionGoodsId() {
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
