package com.xmu.discount.domain.others.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author: 数据库与对象模型标准组
 * @Description: 购物车明细
 * @Date: Created in 14:30 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CartItemPo {
    private Integer id;
    /**
     * 购物车归属的用户id
     */
    private Integer userId;
    /**
     * 货品ID
     */
    private Integer productId;
    /**
     * 是否选中，0未选中，1已选中
     */
    private Boolean beCheck;
    /**
     * 数量
     */
    private Integer number;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Boolean getBeCheck() {
        return beCheck;
    }

    public void setBeCheck(Boolean beCheck) {
        this.beCheck = beCheck;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
}
