package com.xmu.discount.service.impl;

import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Goods;
import com.xmu.discount.domain.others.domain.GoodsPo;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.inter.OrderFeign;
import com.xmu.discount.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PresaleServiceImpl extends PromotionServiceImpl{

    @Autowired
    PresaleRuleDao presaleRuleDao;

    @Autowired
    OrderFeign orderFeign;


    /**
     * 预售活动失效后的行为
     * @param promotionRule
     */
    @Override
    public void toDoSomthingAfterDisable(PromotionRule promotionRule)   {

        orderFeign.refundPresaleOrder((PresaleRule) promotionRule);
    }

    /**
     * 管理员查询一个商品的预售规则（不带商品）
     * @param goodsId
     * @return
     */
    public List<? extends PromotionRule> listPresaleRuleByGoodsId(Integer goodsId) {
        List<? extends PromotionRule> presaleRules=presaleRuleDao.listPromotionRuleByGoodsId(goodsId);

        return presaleRules;
    }

    /**
     * 管理员查询一个商品的预售规则（带商品）
     * @param goodsId
     * @return
     */
    public List<? extends PromotionRule> listPresaleRuleByGoodsIdWithGoods(Integer goodsId) {
        List<? extends PromotionRule> presaleRules=presaleRuleDao.listPromotionRuleByGoodsId(goodsId);
        for(PromotionRule promotionRule:presaleRules){

            Object retObj = goodsFeign.getGoodsById(promotionRule.getGoodsId());
            Goods goods=JacksonUtil.getBack(retObj, Goods.class);

            promotionRule.setGoodsPo(goods);
        }
        return presaleRules;
    }
}
