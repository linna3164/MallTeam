package com.xmu.discount.domain.discount;

import com.xmu.discount.domain.others.Order;
import com.xmu.discount.standard.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xmu.oomall.domain.order.Order;
import xmu.oomall.domain.payment.Payment;
import xmu.oomall.util.JacksonUtil;

import java.io.Serializable;

/**
 * 促销活动
 * @author Ming Qiu
 * @date 2019/11/26 10:39
 */
public abstract class Promotion implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(com.xmu.discount.standard.Order.class);


    /**
     * 判断当前促销活动是否在进行
     */
    protected abstract boolean isValiable();

    /**
     * 返回应付金额
     * @param order 订单
     * @return
     */
    protected abstract Payment getPayment(Order order);



}
