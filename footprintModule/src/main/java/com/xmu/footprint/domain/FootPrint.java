package com.xmu.footprint.domain;

import com.xmu.footprint.standard.FootPrintItem;

import java.time.LocalDateTime;

public class FootPrint extends com.xmu.footprint.standard.FootPrintItem
{

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    @Override
    public LocalDateTime getGmtCreate() {
        return super.getGmtCreate();
    }

    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {
        super.setGmtCreate(gmtCreate);
    }

    @Override
    public LocalDateTime getBirthTime() {
        return super.getBirthTime();
    }

    @Override
    public void setBirthTime(LocalDateTime birthTime) {
        super.setBirthTime(birthTime);
    }

    @Override
    public Integer getUserId() {
        return super.getUserId();
    }

    @Override
    public void setUserId(Integer userId) {
        super.setUserId(userId);
    }

    @Override
    public Integer getGoodsId() {
        return super.getGoodsId();
    }

    @Override
    public void setGoodsId(Integer goodsId) {
        super.setGoodsId(goodsId);
    }
}
