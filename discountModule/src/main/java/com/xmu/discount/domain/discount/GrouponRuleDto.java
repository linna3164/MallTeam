package com.xmu.discount.domain.discount;

import com.xmu.discount.domain.others.OrderDto;
import com.xmu.discount.standard.Payment;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("grouponRuleDto")
public class GrouponRuleDto extends Promotion {

    private GrouponRule grouponRule;

    /**
     * 团购是否在进行中
     * @param orderDto
     * @return
     */
    protected boolean isValiable(OrderDto orderDto) {

        LocalDateTime now = LocalDateTime.now();
        return (this.getStartTime().isBefore(now) &&
                this.getEndTime().isAfter(now));

    }

    /**
     * 返回商品的价格
     * @param order 订单
     * @return
     */
    @Override
    protected Payment getPayment(OrderDto order) {
        //TODO:return  商品价格*数量
    }



    //---------
    public GrouponRuleDto getGrouponRuleDto() {
        return grouponRuleDto;
    }

    public void setGrouponRuleDto(GrouponRuleDto grouponRuleDto) {
        this.grouponRuleDto = grouponRuleDto;
    }



}
