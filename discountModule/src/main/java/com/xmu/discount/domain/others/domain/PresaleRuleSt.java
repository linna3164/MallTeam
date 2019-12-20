package com.xmu.discount.domain.others.domain;

import com.xmu.discount.domain.discount.PresaleRule;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: 数据库与对象模型标准组
 * @Description: 预售信息表
 * @Date: Created in 16:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PresaleRuleSt {
    private Integer id;
    /**
     *预售开始时间
     */
    private LocalDateTime startTime;
    /**
     * 预付结束时间
     */
    private LocalDateTime adEndTime;
    /**
     * 尾款开始时间
     */
    private LocalDateTime finalStartTime;
    /**
     *预售结束时间
     */
    private LocalDateTime endTime;
    /**
     *判断预售是否还在进行中
     */
    private Boolean statusCode;
    /**
     *预售商品id
     */
    private Integer goodsId;
    /**
     *定金
     */
    private BigDecimal deposit;
    /**
     * 尾款金额
     */
    private BigDecimal finalPayment;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;

    public PresaleRuleSt(PresaleRule p){
        this.setAdEndTime(p.getAdEndTime());
        this.setBeDeleted(p.getBeDeleted());
        this.setDeposit(p.getDeposit());
        this.setEndTime(p.getAdEndTime());
        this.setFinalPayment(p.getFinalPayment());
        this.setFinalStartTime(p.getFinalStartTime());
        this.setGmtCreate(p.getGmtCreate());
        this.setGmtModified(p.getGmtModified());
        this.setGoodsId(p.getGoodsId());
        this.setStartTime(p.getStartTime());
        this.setStatusCode(p.getStatusCode());
        this.setId(p.getId());

    }

}
