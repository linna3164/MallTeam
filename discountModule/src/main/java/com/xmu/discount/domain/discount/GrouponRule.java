package com.xmu.discount.domain.discount;


import com.alibaba.fastjson.JSON;
import com.xmu.discount.domain.others.domain.Goods;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.OrderItem;
import com.xmu.discount.domain.others.domain.Payment;

import com.xmu.discount.exception.UnsupportException;
import com.xmu.discount.util.JacksonUtil;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Alias("grouponRule")
public class GrouponRule extends PromotionRule {

    @Override
    public boolean isDisabled() {
        //TODO:标准组
        return false;
    }

    private GrouponRulePo realObj;

    public GrouponRule(GrouponRulePo grouponRulePo){
       this.setRealObj(grouponRulePo);
    }

    private List<Strategy> strategyList;

    public GrouponRule() {

    }

    /**
     * 计算折扣(给定时操作做)
     * @param orders
     * @return
     */
    public List<Payment> cacuGrouponRefund(List<Order> orders, Goods goods){
//        BigDecimal productsPriceorders.get(0).getOrderItemList().get(0).getProduct().getPrice();
        //成团人数 nums
        int nums=0;
        for(Order order:orders){
            OrderItem orderItem=order.getOrderItemList().get(0);
            nums+=orderItem.getNumber();
        }

        //策略
        List<Strategy>strategies=this.getStrategyList();
        //按lowerbound从小到大排序
        strategies.sort((a,b)->{return a.getLowerBound()-a.getUpperBound();});
        //判断在哪个区间
        BigDecimal levelRate=BigDecimal.ZERO;
        for(Strategy strategy:strategies){
            if(strategy.getLowerBound()<=nums&&strategy.getUpperBound()>=nums){
                //在这个区间,用这个rate
                levelRate=strategy.getDiscountRate();
            }
        }
        List<Payment> payments=new ArrayList<>();

        //有打折
        if(levelRate.compareTo(BigDecimal.ZERO)==0){
            for(Order order:orders){
                OrderItem orderItem=order.getOrderItemList().get(0);
                BigDecimal productsPrice=orderItem.getProduct().getPrice();
                Integer number=orderItem.getNumber();

                //每个订单应退价格
                BigDecimal refundPrice=productsPrice.multiply(new BigDecimal(number)).multiply(BigDecimal.ONE.subtract(levelRate));

                Payment payment=new Payment();
                //正数转负数
                payment.setActualPrice(refundPrice.negate());
             //   payment.setOrderId(order.getId());
            }
        }
        return payments;
    }


    public GrouponRule(Integer id,LocalDateTime time){
        this.setGmtModified(LocalDateTime.now());
        this.setId(id);
    }

    public GrouponRule(Integer id,boolean deleted){
        this.setId(id);
        this.setBeDeleted(deleted);
    }


    /**
     * 提交订单时
     * @param order 订单
     * @return
     */
    @Override
    public Order getPayment(Order order) throws UnsupportException{
        List<OrderItem> orderItems = order.getOrderItemList();
        BigDecimal finalPrice = new BigDecimal(0);

        if(orderItems.size()!=1){
             throw new UnsupportException();
        }
        else {
           OrderItem orderItem=orderItems.get(0);
           int num = orderItem.getNumber();
           BigDecimal price = orderItem.getProduct().getPrice();

           finalPrice = finalPrice.add(price.multiply(new BigDecimal(num)));

            Payment payment = new Payment();
            payment.setActualPrice(finalPrice);
            payment.setBeginTime(this.getStartTime());
            payment.setEndTime(this.getEndTime());

            List<Payment> payments=new ArrayList<>(1);
            payments.add(payment);

            order.getOrderItemList().get(0).setItemType(2);
            order.setPaymentList(payments);

            return order;
        }
    }


    @Override
    public LocalDateTime getpromotionRuleStartTime() {
        return this.getStartTime();
    }

    @Override
    public LocalDateTime getPromotionEndTime() {
        return this.getEndTime();
    }


    /**
     * 获取促销商品id
     * @return
     */
    @Override
    public Integer getPromotionGoodsId() {
        return this.getGoodsId();
    }




    /**
     * 获取团购level策略
     * @return
     */
    public List<Strategy> getStrategyList() {
        String jsonString = realObj.getGrouponLevelStrategy();
        jsonString = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString);
        List<String>strategiesString=JacksonUtil.parseStringList(jsonString,"strategy");
        List<Strategy>strategies=new ArrayList<>();
        for(String string:strategiesString){
            Strategy strategy=JSON.parseObject(string,Strategy.class);
            strategies.add(strategy);
        }
        return strategyList;
    }

    /**
     * 设置团购的level策略
     * @param strategyList
     */
    public void setStrategyList(List<Strategy> strategyList) {

        Map<String, Object> jsonObj = new HashMap<String, Object>(2);
        jsonObj.put("strategy", strategyList);

        String jsonString = JacksonUtil.toJson(jsonObj);
        realObj.setGrouponLevelStrategy(jsonString);
        this.strategyList = strategyList;
    }






    private class Strategy{
        private Integer lowerBound;
        private Integer upperBound;
        private BigDecimal discountRate;

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








    //******生成代码*****
    public GrouponRulePo getRealObj() {
        return realObj;
    }

    public void setRealObj(GrouponRulePo realObj) {
        this.realObj = realObj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrouponRule)) {
            return false;
        }
        GrouponRule that = (GrouponRule) o;
        return realObj.equals(that.realObj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(realObj);
    }

    public void setId(Integer id) {
        realObj.setId(id);
    }

    public void setEndTime(LocalDateTime endTime) {
        realObj.setEndTime(endTime);
    }

    public void setStartTime(LocalDateTime startTime) {
        realObj.setStartTime(startTime);
    }

    public void setStatusCode(Boolean statusCode) {
        realObj.setStatusCode(statusCode);
    }

    public void setGrouponLevelStragety(String grouponLevelStragety) {
        realObj.setGrouponLevelStrategy(grouponLevelStragety);
    }

    public void setGoodsId(Integer goodsId) {
        realObj.setGoodsId(goodsId);
    }

    public LocalDateTime getEndTime() {
        return realObj.getEndTime();
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        realObj.setGmtCreate(gmtCreate);
    }

    public LocalDateTime getGmtModified() {
        return realObj.getGmtModified();
    }

    public Boolean getBeDeleted() {
        return realObj.getBeDeleted();
    }

    public Boolean getStatusCode() {
        return realObj.getStatusCode();
    }

    public String getGrouponLevelStragety() {
        return realObj.getGrouponLevelStrategy();
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        realObj.setGmtModified(gmtModified);
    }

//   public boolean canEqual(Object other) {
//        return realObj.canEqual(other);
//    }

    public void setBeDeleted(Boolean beDeleted) {
        realObj.setBeDeleted(beDeleted);
    }

    @Override
    public Integer getId() {
        return realObj.getId();
    }

    public LocalDateTime getGmtCreate() {
        return realObj.getGmtCreate();
    }

    public LocalDateTime getStartTime() {
        return realObj.getStartTime();
    }

    public Integer getGoodsId() {
        return realObj.getGoodsId();
    }

    @Override
    public String toString() {
        return "GrouponRule{" +
                "realObj=" + realObj +
                ", strategyList=" + strategyList +
                '}';
    }
}
