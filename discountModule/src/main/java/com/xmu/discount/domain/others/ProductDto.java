package com.xmu.discount.domain.others;

import com.xmu.discount.standard.Product;

public class ProductDto extends Product {

    private GoodsDto desc;

    public GoodsDto getDesc() {
        return desc;
    }

    public void setDesc(GoodsDto desc) {
        this.desc = desc;
    }
}
