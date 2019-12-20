package com.xmu.discount.domain.discount;

import java.math.BigDecimal;

/**
 * 团购规则策略
 * @author: nanaLin
 * @date: Created in 18:00 2019/11/10
 **/
public class GrouponRuleStrategy {

        private Integer lowerBound;
        private Integer upperBound;
        private BigDecimal discountRate;

        public GrouponRuleStrategy() {
        }

        public GrouponRuleStrategy(Integer lowerBound, Integer upperBound, BigDecimal discountRate) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.discountRate = discountRate;
        }

        /**
         * 一条策略是否有效
         * @return
         */
        public boolean beValid(){
            if(this.getLowerBound()>=0&&this.getLowerBound()<=this.getUpperBound()){
                return true;
            }
            else{
                return false;
            }
        }

        public Integer getLowerBound() {
            return lowerBound;
        }

        public void setLowerBound(Integer lowerBound) {
            this.lowerBound = lowerBound;
        }

        public Integer getUpperBound() {
            return upperBound;
        }

        public void setUpperBound(Integer upperBound) {
            this.upperBound = upperBound;
        }

        public BigDecimal getDiscountRate() {
            return discountRate;
        }

        public void setDiscountRate(BigDecimal discountRate) {
            this.discountRate = discountRate;
        }

}
