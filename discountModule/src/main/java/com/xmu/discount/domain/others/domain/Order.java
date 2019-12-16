package com.xmu.discount.domain.others.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:订单对象
 * @Data:Created in 14:50 2019/12/11
 **/

@EqualsAndHashCode(callSuper = true)
public class Order extends OrderPo {
    private Address addressObj;
    private User user;
    private List<OrderItem> orderItemList;
    private Integer couponId;
    private List<Payment>paymentList;

    public Address getAddressObj() {
        return addressObj;
    }

    public User getUser() {
        return user;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setAddressObj(Address addressObj) {
        this.addressObj = addressObj;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }


    @Override
    public String toString() {
        return "Order{" +
                "addressObj=" + addressObj +
                ", user=" + user +
                ", orderItemList=" + orderItemList +
                ", couponId=" + couponId +
                '}';
    }
}
