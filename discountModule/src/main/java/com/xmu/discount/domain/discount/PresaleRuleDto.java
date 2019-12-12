package com.xmu.discount.domain.discount;

import com.xmu.discount.domain.others.OrderDto;
import com.xmu.discount.standard.Payment;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Alias("presaleRuleDto")
public class PresaleRuleDto extends Promotion {

    /**
     * 是否可付定金
     * @return
     */
    protected boolean isAdPayValiable() {
        LocalDateTime now=LocalDateTime.now();
        return (this.getStartTime().isBefore(now) &&
                this.getAdEndTime().isAfter(now));
    }


    /**
     * 是否可付尾款
     * @return
     */
    protected boolean isFinalPayValiable() {
        LocalDateTime now=LocalDateTime.now();
        return (this.getFinalStartTime().isBefore(now) &&
                this.getEndTime().isAfter(now));
    }


    /**
     * 通过订单判断付款到哪个阶段了
     * @return
     */
    @Override
    protected Payment calcuPayment(OrderDto orderDto) {
        //TODO:
        if(){//orderItem价格为空，付订单
            if(isAdPayValiable()){//可付定金

            }
        }
        else if(){//orderItem价格等于定金，付尾款
            if(isFinalPayValiable()){//可付尾款

            }
        }

    }
}
