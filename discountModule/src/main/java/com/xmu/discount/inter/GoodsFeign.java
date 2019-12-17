package com.xmu.discount.inter;

import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.others.domain.Goods;
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
@FeignClient(value = "goodsService")
public interface GoodsFeign {
    @RequestMapping(value = "/goods/{id}",method = RequestMethod.GET)
    public Goods getGoodsById(Integer goodsId);


}
