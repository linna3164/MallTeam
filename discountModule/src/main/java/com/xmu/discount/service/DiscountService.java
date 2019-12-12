package com.xmu.discount.service;

import com.xmu.discount.domain.discount.Promotion;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;

public interface DiscountService {

    public Payment getPayment(Order order);

    public Promotion getProimotion(Integer goodsId);

}
