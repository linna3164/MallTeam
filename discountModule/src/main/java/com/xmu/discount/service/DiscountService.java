package com.xmu.discount.service;

import com.xmu.discount.domain.discount.Promotion;
import com.xmu.discount.domain.others.OrderDto;
import com.xmu.discount.standard.Payment;

public interface DiscountService {

    public Payment getPayment(OrderDto orderDto);

    public Promotion getProimotion(Integer goodsId);

}
