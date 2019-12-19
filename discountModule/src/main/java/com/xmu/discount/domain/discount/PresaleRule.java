package com.xmu.discount.domain.discount;


import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.OrderItem;
import com.xmu.discount.domain.others.domain.Payment;

import org.apache.ibatis.type.Alias;
import org.apache.tomcat.jni.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Alias("presaleRule")
public class PresaleRule extends PromotionRule {

    /**
     * 预售活动是否失效
     * @return
     */
    @Override
    public boolean beDisabled() {
        System.out.println(this);
        if(this.getStatusCode()){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 预售活动是否等待结束
     * @return
     */
    @Override
    public boolean beWaitFinish() {
        return false;
    }


    /**
     * 是否可付定金
     * @return
     */
    protected boolean isAdPayValiable() {
        LocalDateTime now=LocalDateTime.now();
        return (this.getStartTime().isBefore(now) &&
                this.getAdEndTime().isAfter(now));
    }


    /**
     * 是否可付尾款
     * @return
     */
    protected boolean isFinalPayValiable() {
        LocalDateTime now=LocalDateTime.now();
        return (this.getFinalStartTime().isBefore(now) &&
                this.getEndTime().isAfter(now));
    }



    /**
     * 提交订单时
     * @param order 订单
     * @return
     */
    @Override
    public Order getPayment(Order order) {

        OrderItem orderItem=order.getOrderItemList().get(0);

        BigDecimal prePay = BigDecimal.ZERO;
        BigDecimal finalPay = BigDecimal.ZERO;
        LocalDateTime now = LocalDateTime.now();

        for (OrderItem item: order.getOrderItemList()){
            prePay = prePay.add(this.getDeposit().multiply(new BigDecimal(orderItem.getNumber())));
            finalPay = finalPay.add(this.getFinalPayment().multiply(new BigDecimal(orderItem.getNumber())));
        }

        Payment prePayment = new Payment();
        prePayment.setActualPrice(prePay);
        prePayment.setEndTime(this.getAdEndTime());

        Payment finalPayment = new Payment();
        finalPayment.setActualPrice(finalPay);
        finalPayment.setBeginTime(this.getFinalStartTime());
        finalPayment.setEndTime(this.getEndTime());

        List<Payment> ret = new ArrayList<>(2);
        ret.add(prePayment);
        ret.add(finalPayment);

        order.setPaymentList(ret);

        order.getOrderItemList().get(0).setItemType(1);

        return order;
    }

    /**
     * 获得促销开始的时间
     * @return
     */
    @Override
    public LocalDateTime getpromotionRuleStartTime() {

        return this.getStartTime();
    }

    /**
     * 获得促销结束的时间
     * @return
     */
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


    public PresaleRule(Integer id) {
        this.id = id;
    }

    private Integer id;
    /**
     *预售开始时间
     */
    private LocalDateTime startTime;
    /**
     * 预付结束时间
     */
    private LocalDateTime adEndTime;
    /**
     * 尾款开始时间
     */
    private LocalDateTime finalStartTime;
    /**
     *预售结束时间
     */
    private LocalDateTime endTime;
    /**
     *判断预售是否还在进行中
     */
    private Boolean statusCode;
    /**
     *预售商品id
     */
    private Integer goodsId;
    /**
     *定金
     */
    private BigDecimal deposit;
    /**
     * 尾款金额
     */
    private BigDecimal finalPayment;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;


    public PresaleRule(Integer id,boolean beDeleted){
        this.setBeDeleted(beDeleted);
        this.setId(id);
    }

    public PresaleRule(Integer id, LocalDateTime time){
        this.setGmtModified(time);
        this.setId(id);
    }


    //*****生成代码******


    @Override
    public String toString() {
        return "PresaleRule{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", adEndTime=" + adEndTime +
                ", finalStartTime=" + finalStartTime +
                ", endTime=" + endTime +
                ", statusCode=" + statusCode +
                ", goodsId=" + goodsId +
                ", deposit=" + deposit +
                ", finalPayment=" + finalPayment +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", beDeleted=" + beDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PresaleRule)) {
            return false;
        }
        PresaleRule that = (PresaleRule) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getAdEndTime() {
        return adEndTime;
    }

    public void setAdEndTime(LocalDateTime adEndTime) {
        this.adEndTime = adEndTime;
    }

    public LocalDateTime getFinalStartTime() {
        return finalStartTime;
    }

    public void setFinalStartTime(LocalDateTime finalStartTime) {
        this.finalStartTime = finalStartTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Boolean statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public Integer getGoodsId() {
        return goodsId;
    }

    @Override
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getFinalPayment() {
        return finalPayment;
    }

    public void setFinalPayment(BigDecimal finalPayment) {
        this.finalPayment = finalPayment;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getBeDeleted() {
        return beDeleted;
    }

    public void setBeDeleted(Boolean beDeleted) {
        this.beDeleted = beDeleted;
    }

    public PresaleRule() {
    }


}
