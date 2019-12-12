package com.xmu.discount.domain.others;

import com.xmu.discount.standard.Goods;

import java.time.LocalDateTime;

public class GoodsDto extends Goods {


    public static Goods ALL_GOODS = new GoodsDto(0);

    /**
     * 用id构造Goods
     * @param id
     */
    public GoodsDto(Integer id) {
        super.setId(id);
        super.setGmtCreate(LocalDateTime.now());
    }

    public GoodsDto(){
    }

}
