package com.xmu.discount.domain.discount;

import com.xmu.discount.domain.others.Order;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.standard.Payment;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Alias("presaleRule")
public class PresaleRule extends Promotion {


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
     * 通过订单判断付款到哪个阶段了
     * @return
     */
    @Override
    protected Payment calcuPayment(Order order) {
        //TODO:
        if(){//orderItem价格为空，付订单
            if(isAdPayValiable()){//可付定金

            }
        }
        else if(){//orderItem价格等于定金，付尾款
            if(isFinalPayValiable()){//可付尾款

            }
        }

    }

    /**
     * 获得促销开始的时间
     * @return
     */
    @Override
    protected LocalDateTime getPromotionStartTime() {

        return this.getStartTime();
    }

    /**
     * 获得促销结束的时间
     * @return
     */
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
                "} " + super.toString();
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

    public Integer getGoodsId() {
        return goodsId;
    }

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
}