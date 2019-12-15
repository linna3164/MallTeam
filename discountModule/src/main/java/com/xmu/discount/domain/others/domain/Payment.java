package com.xmu.discount.domain.others.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: ���ݿ������ģ�ͱ�׼��
 * @Description: �����¼
 * @Date: Created in 16:00 2019/12/11
 **/

@ToString
@EqualsAndHashCode
public class Payment {

    

    private Integer id;
    /**
     * ������
     */
    private BigDecimal actualPrice;
     /**
     * ����������΢�ţ����еȵ�
     */
    private Integer payChannel;
    /**
     * �Ƿ񸶿�ɹ�,0���ɹ���1�ɹ�
     */
    private Boolean beSuccessful;
    /**
     * ����ʱ��
     */
    private LocalDateTime payTime;
    /**
     * �����ţ���֧��������õ�
     */
    private String paySn;
    /**
     * ����Ŀ�ʼʱ�� ���ڿ�ʼʱ��ͽ���ʱ��֮��ſ��Ը��
     */
    private LocalDateTime beginTime;
    /**
     * ����Ľ���ʱ��
     */
    private LocalDateTime endTime;
    /**
     * ����Id
     */
    private Integer orderId;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public Boolean getBeSuccessful() {
        return beSuccessful;
    }

    public void setBeSuccessful(Boolean beSuccessful) {
        this.beSuccessful = beSuccessful;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
