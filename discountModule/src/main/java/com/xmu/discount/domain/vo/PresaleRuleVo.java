package com.xmu.discount.domain.vo;


import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.others.domain.GoodsPo;

public class PresaleRuleVo {

    private PresaleRule presaleRule;

    private GoodsPo goodsPo;

    public PresaleRule getPresaleRule() {
        return presaleRule;
    }

    public void setPresaleRule(PresaleRule presaleRule) {
        this.presaleRule = presaleRule;
    }

    public GoodsPo getGoodsPo() {
        return goodsPo;
    }

    public void setGoodsPo(GoodsPo goodsPo) {
        this.goodsPo = goodsPo;
    }

    @Override
    public String toString() {
        return "PresaleRuleVo{" +
                "presaleRule=" + presaleRule +
                ", goodsPo=" + goodsPo +
                '}';
    }
}
