package com.xmu.discount.domain.coupon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * number策略
 * @author: nanaLin
 * @date: Created in 18:00 2019/11/10
 **/
public class NumberStrategy extends AbstractCouponStrategy {

    /**
     * JSON格式
     *{"name":“NumberStrategy","obj":{"threshold":xxx, "offCash":xxx}}
     */

    /**
     *满(多少件)
     */
    private Integer threshold = 0;
    /**
     * 减
     */
    private BigDecimal offCash = BigDecimal.ZERO;


    @Override
    protected boolean isEnough(BigDecimal totalPrice, Integer totalQuantity) {
        return totalQuantity.compareTo(this.threshold) >= 0;

    }

    @Override
    protected BigDecimal getDealPrice(BigDecimal itemPrice, BigDecimal totalPrice) {
        return  itemPrice.subtract(this.offCash.multiply(itemPrice.divide(totalPrice, 3, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    protected BigDecimal getError(BigDecimal totalPrice, BigDecimal dealPrice) {
        return totalPrice.subtract(dealPrice).subtract(this.offCash);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        NumberStrategy that = (NumberStrategy) o;
        return Objects.equals(threshold, that.threshold) &&
                Objects.equals(offCash, that.offCash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threshold, offCash);
    }


    /****************************************************
     * 生成代码
     ****************************************************/

    @Override
    public String toString() {
        return "CashOffStrategy{" +
                "threshold=" + threshold +
                ", offCash=" + offCash +
                '}';
    }

    public NumberStrategy() {
    }

    public NumberStrategy(Integer threshold, BigDecimal offCash) {
        this.threshold = threshold;
        this.offCash = offCash;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public BigDecimal getOffCash() {
        return offCash;
    }

    public void setOffCash(BigDecimal offCash) {
        this.offCash = offCash;
    }
}
