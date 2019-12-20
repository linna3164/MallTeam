package com.xmu.discount.domain.vo;


import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.others.domain.GoodsPo;
import com.xmu.discount.domain.others.domain.PresaleRuleSt;

/**
 * 预售规则vo
 * @author api标准组
 */
public class PresaleRuleVo {

    private PresaleRuleSt presaleRule;

    private GoodsPo goodsPo;

    public PresaleRuleSt getPresaleRule() {
        return presaleRule;
    }

    public void setPresaleRule(PresaleRuleSt presaleRule) {
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
