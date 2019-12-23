package com.xmu.discount.inter;

import com.xmu.discount.domain.others.domain.GoodsPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author hsx
 * @version 1.0
 * @date 2019/12/17 1:28
 */
@FeignClient(name = "goodsInfoService",
            url = "http://47.100.91.153:3090")
public interface GoodsFeign {
    /**
     * 获取商品
     * @param id
     * @return
     */
    @RequestMapping(value = "/inner/goods/{id}",method = RequestMethod.GET)
    public Object getGoodsById(@PathVariable Integer id);


}
