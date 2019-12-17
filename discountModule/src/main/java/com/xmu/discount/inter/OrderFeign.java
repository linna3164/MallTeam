package com.xmu.discount.inter;

import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hsx
 * @version 1.0
 * @date 2019/12/17 1:28
 */
@FeignClient(value = "orderService")
public interface OrderFeign {

    @RequestMapping(value = "/orders/grouponOrders",method = RequestMethod.GET)
    public Integer getGrouponOrders(GrouponRulePo grouponRulePo);

    @RequestMapping(value = "/order/grouponOrders/refund",method = RequestMethod.POST)
    public boolean refundGrouponOrder(@RequestBody GrouponRulePo grouponRulePo,@RequestParam BigDecimal price
    );

    @RequestMapping(value = "/order/presaleRule/refund",method = RequestMethod.POST)
    public boolean refundPresaleOrder(@RequestBody PresaleRule presaleRulePo);

}
