package com.xmu.discount.standard;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:团购规则信息
 * @Data:Created in 14:50 2019/11/29
 * @Modified By:
 **/

public class GrouponRule {
    private Integer id;
    /**
     *团购开始时间
     */
    private LocalDateTime startTime;
    /**
     *团购结束时间
     */
    private LocalDateTime endTime;
    /**
    *判断团购是否还在进行中
    */
    private Boolean statusCode;
    /**
    * 团购等级（满多少人组团多少折扣）
    */
    private String grouponLevelStrategy;
    /**
    *团购商品id
    */
    private Integer goodsId;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;

    @Override
    public String toString() {
        return "GrouponRule{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", statusCode=" + statusCode +
                ", grouponLevelStrategy='" + grouponLevelStrategy + '\'' +
                ", goodsId=" + goodsId +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", beDeleted=" + beDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        GrouponRule that = (GrouponRule) o;
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

    public String getGrouponLevelStrategy() {
        return grouponLevelStrategy;
    }

    public void setGrouponLevelStrategy(String grouponLevelStrategy) {
        this.grouponLevelStrategy = grouponLevelStrategy;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
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