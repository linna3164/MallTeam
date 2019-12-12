package com.xmu.discount.service;

import com.xmu.discount.domain.discount.Promotion;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;

import java.util.List;

public interface DiscountService {

    Payment getPayment(Order order);

    List<Promotion> listProimotionByGoodsId(Integer goodsId);

    /**
     * 判断这个促销活动是否可添加
     * @param promotion
     * @return
     */
    Boolean isValid(Promotion promotion);

    /**
     * 获得商品当前的促销活动
     * @param goodsId
     * @return
     */
    Promotion getCurrentPromotionByGoodsId(Integer goodsId);

    /**
     * 添加优惠活动
     * @param promotion
     * @return
     */
    Promotion addPromotion(Promotion promotion);
}
