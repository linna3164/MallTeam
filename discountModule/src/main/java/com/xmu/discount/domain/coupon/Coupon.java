package com.xmu.discount.domain.coupon;

import com.xmu.discount.domain.others.domain.Order;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Objects;

@Alias("coupon")
public class Coupon {

    private Integer id;
    /**
     * 所属用户的ID
     */
    private Integer userId;
    /**
     * 所属优惠券规则的ID
     */
    private Integer couponRuleId;
    /**
     * 优惠券序列号
     */
    private String couponSn;
    /**
     * 本张优惠券的生效时间
     */
    private LocalDateTime beginTime;
    /**
     * 本张优惠券的失效时间
     */
    private LocalDateTime endTime;
    /**
     * 本张优惠券被使用的日期
     */
    private LocalDateTime usedTime;
    /**
     * 本张优惠券的名称
     */
    private String name;
    /**
     * 本张优惠券的图片
     */
    private String picUrl;
    /**
     * 优惠券是否已经使用，0未使用，1已使用
     */
    private Integer statusCode;//TODO:标准组更新了吗？

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;
    /**
     * 优惠券的状态
     */
    Status status;
    /**
     * 所属的优惠卷类别enum（未用，已用，过期）
     */
    CouponRule couponRule;
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
        EXPIRED("过期", 2),
        /**
         * 失效
         */
        DISABLED("失效",3);
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
     * 生成beginTime和endTime
     */
    public void setTimes(){
        CouponRule.TimeStatus timeStatus=this.getCouponRule().getTimeStatus();
        if(timeStatus.equals(CouponRule.TimeStatus.LIMIT)){
            this.setBeginTime(this.getCouponRule().getBeginTime());
            this.setEndTime(this.getCouponRule().getEndTime());
        }
        else if(timeStatus.equals(CouponRule.TimeStatus.PERIOD)){
            LocalDateTime now = LocalDateTime.now();
            this.setBeginTime(now);
            this.setEndTime(now.plusDays(this.getCouponRule().getValidPeriod()));
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

    public Coupon() {
        //TODO:还需要根据标准组确定是0还是1
        this.setStatusCode(0);
        this.setStatus(Status.NOT_USED);
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", userId=" + userId +
                ", couponRuleId=" + couponRuleId +
                ", couponSn='" + couponSn + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", usedTime=" + usedTime +
                ", name='" + name + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", statusCode=" + statusCode +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", beDeleted=" + beDeleted +
                ", status=" + status +
                ", couponRule=" + couponRule +
                '}';
    }

    /****************************************************
     * 生成代码
     ****************************************************/



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coupon)) {
            return false;
        }
        Coupon coupon = (Coupon) o;
        return Objects.equals(id, coupon.id) &&
                Objects.equals(beDeleted, coupon.beDeleted) &&
                status == coupon.status &&
                Objects.equals(couponRule, coupon.couponRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }






    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCouponRuleId() {
        return couponRuleId;
    }

    public void setCouponRuleId(Integer couponRuleId) {
        this.couponRuleId = couponRuleId;
    }

    public String getCouponSn() {
        return couponSn;
    }

    public void setCouponSn(String couponSn) {
        this.couponSn = couponSn;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(LocalDateTime usedTime) {
        this.usedTime = usedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getBeDeleted() {
        return beDeleted;
    }

    public void setBeDeleted(Boolean beDeleted) {
        this.beDeleted = beDeleted;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CouponRule getCouponRule() {
        return couponRule;
    }

    public void setCouponRule(CouponRule couponRule) {
        this.couponRule = couponRule;
    }
}