package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.dao.PresaleRuleDao;
import com.xmu.discount.domain.others.OrderDto;
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
    public Payment getPayment(OrderDto orderDto) {
        Promotion promotion=this.getPromotion(goodsId);
        promotion.getPayment(OrderDto.orderDto);
    }

    @Override
    public Promotion getProimotion(Integer goodsId) {
        return null;
    }

    /**
     * 通过id找到商品的促销活动
     * @param goodsId
     * @return
     */
    public List<Promotion> getPromotion(Integer goodsId){
        List<Promotion> grouponRuleDtos=grouponRuleDao.listGrouponRuleByGoodsId(goodsId);
        List<Promotion> presaleRuleDtos=presaleRuleDao.listPresaleRuleByGoodsId(goodsId);

        List<Promotion>promotions=new ArrayList<>();
        promotions.addAll(grouponRuleDtos);
        promotions.addAll(presaleRuleDtos);

        return promotions;
    }

}
