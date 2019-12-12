package com.xmu.discount.service;

import com.xmu.discount.domain.others.OrderDto;

public interface DiscountService {

    public Payment getPayment(OrderDto orderDto);

    public Promotion getProimotion(Integer goodsId);

}
