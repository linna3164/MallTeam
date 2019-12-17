package com.xmu.discount.domain.others.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:订单明细对象
 * @Data:Created in 14:50 2019/12/11
 **/

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends OrderItemPo {

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    /**
     * 由购物车对象构造订单明细对象
     * @param cartItem 购物车对象
     */
    public OrderItem(CartItem cartItem) {
        this.setNumber(cartItem.getNumber());
        Product product = cartItem.getProduct();
        this.setProduct(product);
        this.setPrice(product.getPrice());
        this.setDealPrice(this.getPrice());

        StringBuffer productName = new StringBuffer(product.getGoodsPo().getName());
        productName.append(" ");
        productName.append(product.getSpecifications());
        this.setNameWithSpecifications(productName.toString());

        this.setGmtCreate(LocalDateTime.now());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        OrderItem newItem = (OrderItem) super.clone();
        newItem.setId(null);
        newItem.setGmtCreate(LocalDateTime.now());
        newItem.setGmtModified(null);
        return newItem;
    }
}
