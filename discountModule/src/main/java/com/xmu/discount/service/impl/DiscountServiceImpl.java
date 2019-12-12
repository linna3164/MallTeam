package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.domain.others.Order;
import com.xmu.discount.domain.discount.Promotion;
import com.xmu.discount.service.DiscountService;
import com.xmu.discount.standard.Payment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DiscountServiceImpl implements DiscountService {

    @Autowired
    GrouponRuleDao grouponRuleDao;

    @Autowired
    PresaleRuleDao presaleRuleDao;

    @Override
    public Payment getPayment(Order order) {
        Promotion promotion=this.getPromotion(goodsId);
        promotion.getPayment(Order order);
    }

    /**
     * 通过id找到商品的促销活动
     * @param goodsId
     * @return
     */
    public List<Promotion> getPromotion(Integer goodsId){
        List<Promotion> grouponRule=grouponRuleDao.listGrouponRuleByGoodsId(goodsId);
        List<Promotion> presaleRule=presaleRuleDao.listPresaleRuleByGoodsId(goodsId);

        List<Promotion>promotions=new ArrayList<>();
        promotions.addAll(grouponRule);
        promotions.addAll(presaleRule);

        return promotions;
    }

}
