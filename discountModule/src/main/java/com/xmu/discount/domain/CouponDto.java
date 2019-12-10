package com.xmu.discount.domain;

import com.xmu.discount.standard.Coupon;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("couponDto")
public class CouponDto extends Coupon {


    /**
     * 优惠券的状态
     */
    Status status;
    /**
     * 所属的优惠卷类别enum（未用，已用，过期）
     */
    CouponRuleDto couponRuleDto;
    /**
     * 优惠卷的状态
     */
    public enum Status {
        /**
         * 未用
         */
        NOT_USED("未用", 0),
        /**
         * 已用
         */
        USED("已用", 1),
        /**
         * 过期
         */
        EXPIRED("过期", 2);

        /**
         * 值
         */
        private final Integer value;

        /**
         * 名称
         */
        private final String name;

        /**
         * 构造函数
         * @param name 名称
         * @param value 值
         */
        Status(String name, Integer value) {
            this.value = value;
            this.name = name;
        }

        /**
         * 获得值
         * @return 值
         */
        public Integer getValue() {
            return this.value;
        }

        /**
         * 获得名称
         * @return 名
         */
        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * 判断某个优惠卷是否能用
     * @return
     */
    public boolean isReadyToUse() {
        LocalDateTime now = LocalDateTime.now();
        return (this.getBeginTime().isBefore(now) &&
                this.getEndTime().isAfter(now) &&
                this.getStatus().equals(Status.NOT_USED.getValue()));
    }

    /**
     * 获得优惠的费用
     *
     * @param order 订单
     * @return 优惠的费用
     */
    public void cacuCouponPrice(Order order) {
        if (this.isReadyToUse()){
            this.getCouponRule().cacuCouponPrice(order, this.couponSn);
        }
    }

    public CouponDto() {
        super.setStatusCode(false);
        this.setStatus(Status.NOT_USED);
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CouponRuleDto getCouponRuleDto() {
        return couponRuleDto;
    }

    public void setCouponRuleDto(CouponRuleDto couponRuleDto) {
        this.couponRuleDto = couponRuleDto;
    }
}