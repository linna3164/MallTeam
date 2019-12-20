package com.xmu.discount.domain.vo;

import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.others.domain.GoodsPo;

/**
 * 团购规则vo
 * @author api标准组
 */
public class GrouponRuleVo {
    private GrouponRulePo grouponRulePo;
    private GoodsPo goodsPo;

    public GrouponRulePo getGrouponRulePo() {
        return grouponRulePo;
    }

    public void setGrouponRulePo(GrouponRulePo grouponRulePo) {
        this.grouponRulePo = grouponRulePo;
    }

    public GoodsPo getGoodsPo() {
        return goodsPo;
    }

    public void setGoodsPo(GoodsPo goodsPo) {
        this.goodsPo = goodsPo;
    }

    @Override
    public String toString() {
        return "GrouponRuleVo{" +
                "grouponRulePo=" + grouponRulePo +
                ", goodsPo=" + goodsPo +
                '}';
    }
}
