package com.xmu.discount.inter;

import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author hsx
 * @version 1.0
 * @date 2019/12/17 1:28
 */
@FeignClient(value = "orderService")
public interface OrderFeign {
    @RequestMapping(value = "/orders/grouponOrders",method = RequestMethod.GET)
    public List<Order> getGrouponOrders(GrouponRulePo grouponRulePo);

    @RequestMapping(value = "/order/grouponOrders/refund",method = RequestMethod.POST)
    public void refundGrouponOrder(List<Payment> paymentList);

}
