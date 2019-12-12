package com.xmu.discount.domain.others;

import com.xmu.discount.standard.OrderItem;

public class OrderItemDto extends OrderItem {

    private ProductDto productDto;
    /**
     * 优惠活动序号（优惠卷，团购活动或者预售活动的编号）
     */
    private String promotionSn = "";

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public String getPromotionSn() {
        return promotionSn;
    }

    public void setPromotionSn(String promotionSn) {
        this.promotionSn = promotionSn;
    }
}
