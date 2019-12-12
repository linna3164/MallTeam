package com.xmu.discount.service;

import com.xmu.discount.domain.others.Order;

public interface DiscountService {

    public Payment getPayment(Order order);

    public Promotion getProimotion(Integer goodsId);

}
