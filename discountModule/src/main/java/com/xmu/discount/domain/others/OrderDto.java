package com.xmu.discount.domain.others;

import com.xmu.discount.standard.Order;

import java.util.List;

/**
 *
 */
public class OrderDto extends Order {
    List<OrderItemDto> orderItemDtos;


    public List<OrderItemDto> getOrderItemDtos() {
        return orderItemDtos;
    }

    public void setOrderItemDtos(List<OrderItemDto> orderItemDtos) {
        this.orderItemDtos = orderItemDtos;
    }
}
