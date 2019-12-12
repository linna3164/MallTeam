package com.xmu.discount.domain.coupon;

import com.xmu.discount.domain.others.OrderItemDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  优惠卷策略的抽象類
 * @author: Ming Qiu
 * @date: Created in 15:47 2019/11/5
 * */
    public abstract class AbstractCouponStrategy {
    private Logger logger = LoggerFactory.getLogger(AbstractCouponStrategy.class);

    /**
     * 用于判断是否已经满足优惠门槛
     * @param totalPrice 总费用
     * @param totalQuantity 总数量
     * @return true- 已经满足， false - 不满足
     */
    protected abstract boolean isEnough(BigDecimal totalPrice, Integer totalQuantity);

    /**
     * 计算折扣后的价格
     * @param totalPrice 优惠货品的总价
     * @param  itemPrice 当前货品的小计价格
     * @return 折扣后的价格
     */
    protected abstract BigDecimal getDealPrice(BigDecimal itemPrice, BigDecimal totalPrice);

    /**
     * 获得优惠金额与设定的误差
     * @param totalPrice 订单原价
     * @param dealPrice 优惠价
     * @return 误差值
     */
    protected abstract BigDecimal getError(BigDecimal totalPrice, BigDecimal dealPrice);

    /**
     * 获得优惠的费用
     * @param validItems 订单中可以用于优惠卷的明细
     * @param couponSn 优惠卷序号
     * @return 更新的订单列表(validItems)，包含可能因为误差拆开的明细(newItems)
     */
    public List<OrderItemDto> cacuDiscount(List<OrderItemDto> validItems, String couponSn){
        logger.debug("cacuDiscount的参数： validItems"+ validItems +" couponSn = "+couponSn);
        //优惠商品的总价和数量
        BigDecimal totalPrice = BigDecimal.ZERO;//可用优惠券明细的订单item的总价
        Integer totalQuantity = 0;//可用优惠券明细的订单item的总数量

        //优惠的货品
        List<OrderItemDto> discountItems = new ArrayList<>(validItems.size());

        Iterator<OrderItemDto> itemIterator = validItems.iterator();

        while (itemIterator.hasNext()){
            OrderItemDto item = itemIterator.next();
            logger.debug("总价 totalPrice="+ totalPrice + " 总数 totalQuantitiy = "+totalQuantity);
            totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(item.getNumber())));//可优惠总价
            totalQuantity += item.getNumber();
            discountItems.add(item);
        }

        logger.debug("总价 totalPrice="+ totalPrice + " 总数 totalQuantitiy = "+totalQuantity);
        //判断是否达到优惠门槛
        boolean enough = this.isEnough(totalPrice, totalQuantity);
        logger.debug("优惠门槛 enough = "+enough);

        //计算优惠后的价格
        List<OrderItemDto> newItems = new ArrayList<>();
        BigDecimal dealTotalPrice = BigDecimal.ZERO;
        if (enough) {
            for (OrderItemDto item: discountItems){
                //按照比例分配，可能会出现精度误差，在后面补偿到第一个货品上
                BigDecimal dealPrice = this.getDealPrice(item.getPrice(), totalPrice);//每一项商品的每一项
                item.setPromotionSn(couponSn);
                logger.debug("优惠价格 dealPrice="+ dealPrice);
                item.setDealPrice(dealPrice);
                dealTotalPrice = dealTotalPrice.add(dealPrice.multiply(BigDecimal.valueOf(item.getNumber())));//每一项的优惠价格
                logger.debug("总优惠价 dealTotalPrice="+ dealTotalPrice);
                logger.debug("优惠明细 item="+ item);
            }

            //计算误差
            BigDecimal error = this.getError(totalPrice, dealTotalPrice);
            logger.debug("误差 error="+ error);
            if (error.compareTo(BigDecimal.ZERO) != 0){
                //前面保留小数两位数可能出现的精度误差
                //寻找数量为1的明细，将误差补偿在此明细上，否则拆开一个现有明细

                Boolean gotIt = false;
                for (OrderItemDto item : validItems){
                    if (item.getNumber() == 1){
                        BigDecimal dealPrice = item.getDealPrice();
                        item.setDealPrice(dealPrice.add(error));
                        gotIt = true;
                        break;
                    }
                }

                if (!gotIt){
                    //无数量为1的明细，拆第一个
                    OrderItemDto item = validItems.get(0);
                    Integer quantity = item.getNumber();
                    item.setNumber(quantity - 1);
                    try {
                        OrderItemDto newItem = (OrderItemDto) item.clone();
                        newItem.setNumber(1);
                        BigDecimal dealPrice = newItem.getDealPrice();
                        newItem.setDealPrice(dealPrice.add(error));
                        newItems.add(newItem);
                    } catch (CloneNotSupportedException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }

        logger.debug("cacuDiscount返回 newItems = "+newItems);
        return newItems;
    }
}
