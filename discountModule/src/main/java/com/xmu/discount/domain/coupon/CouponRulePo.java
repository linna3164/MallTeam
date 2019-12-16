package com.xmu.discount.domain.coupon;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:优惠券规则信息
 * @Data:Created in 14:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Alias("couponRulePo")
public class CouponRulePo {
    private Integer id;
    /**
     * 优惠券规则名称
     */
    private String name;
    /**
     * 优惠券简介
     */
    private String brief;
    /**
     * 优惠券规则的生效日期
     */
    private LocalDateTime beginTime;
    /**
     * 优惠券规则的失效日期
     */
    private LocalDateTime endTime;
    /**
     * 优惠券规则的图片
     */
    private String picUrl;
    /**
     * 优惠卷有效时间，单位天，
     * 负数表示绝对时间，即优惠卷开始领取时间后的天数，
     * 正数表示相对时间，即用户实际领取优惠卷后的天数
     */
    private Integer validPeriod;
    /**
     * 折扣策略
     * JSON格式:{"name":“XXX”, "obj":{XXX}}
     * eg. {"name":"xmu.oomall.discount.domain.DiscountStrategy.NumberStrategy", "obj":{"threshold":5, "offCash":10.01}}
     */
    private String strategy;
    /**
     * 该优惠券规则下优惠券的总张数
     */
    private Integer total;
    /**
     * 该优惠券规则下优惠券的被领取数
     */
    private Integer collectedNum;
    /**
     * 存放适用于本优惠券规则的所有商品ID
     * JSON格式:{"goodsIds": [xxx,xxx,xxx,xxx,xxx]}
     * eg. {"goodsIds": [1, 2, 3, 4, 5]}
     */
    private String goodsList1;
    /**
     * 存放适用于本优惠券规则的所有商品ID(商品可能很多，可能需要多个list存放)
     * JSON格式:{"goodsIds": [xxx,xxx,xxx,xxx,xxx]}
     * eg. {"goodsIds": [1, 2, 3, 4, 5]}
     */
    private String goodsList2;
    /**
     * 是否是失效的优惠活动
     */
    private Integer statusCode;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;

    public CouponRulePo(Integer id, boolean b) {
        this.id=id;
        this.beDeleted=b;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(Integer validPeriod) {
        this.validPeriod = validPeriod;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCollectedNum() {
        return collectedNum;
    }

    public void setCollectedNum(Integer collectedNum) {
        this.collectedNum = collectedNum;
    }

    public String getGoodsList1() {
        return goodsList1;
    }

    public void setGoodsList1(String goodsList1) {
        this.goodsList1 = goodsList1;
    }

    public String getGoodsList2() {
        return goodsList2;
    }

    public void setGoodsList2(String goodsList2) {
        this.goodsList2 = goodsList2;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
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

    public CouponRulePo (){

    }

    @Override
    public String toString() {
        return "CouponRulePo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brief='" + brief + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", picUrl='" + picUrl + '\'' +
                ", validPeriod=" + validPeriod +
                ", strategy='" + strategy + '\'' +
                ", total=" + total +
                ", collectedNum=" + collectedNum +
                ", goodsList1='" + goodsList1 + '\'' +
                ", goodsList2='" + goodsList2 + '\'' +
                ", statusCode=" + statusCode +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", beDeleted=" + beDeleted +
                '}';
    }
}
