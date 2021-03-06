package com.xmu.discount.domain.discount;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;
import org.apache.ibatis.type.Alias;


import java.time.LocalDateTime;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:团购规则信息
 * @Data:Created in 14:50 2019/12/11
 **/

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Alias("grouponRulePo")
public class GrouponRulePo {
    private Integer id;
    /**
     * 团购开始时间
     */
    private LocalDateTime startTime;
    /**
     * 团购结束时间
     */
    private LocalDateTime endTime;
    /**
     * 判断团购是否还在进行中(true 是，false 失效）
     */
    private boolean statusCode;

    /**
     * 团购等级（满多少人组团多少折扣）
     * JSON格式: {"strategy": [{"lowerbound":xxx, "upperbound":xxx, "rate":xxx}]}, xxx为具体数值
     */
    private String grouponLevelStrategy;
    /**
     * 团购商品id
     */
    private Integer goodsId;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;


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




    public String getGrouponLevelStrategy() {
        return grouponLevelStrategy;
    }

    public void setGrouponLevelStrategy(String grouponLevelStragety) {
        this.grouponLevelStrategy = grouponLevelStragety;
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

    public GrouponRulePo(Integer id,boolean beDeleted){
        this.setBeDeleted(beDeleted);
        this.setId(id);
    }

    public  GrouponRulePo(){

    }

    public boolean isStatusCode() {
        return statusCode;
    }

    public void setStatusCode(boolean statusCode) {
        this.statusCode = statusCode;
    }

}