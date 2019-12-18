package com.xmu.discount.domain.discount;


import com.alibaba.fastjson.JSON;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xmu.discount.domain.others.domain.*;
import com.xmu.discount.exception.UnsupportException;
import com.xmu.discount.util.JacksonUtil;
import org.apache.ibatis.type.Alias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Alias("grouponRule")
public class GrouponRule extends PromotionRule {

    private static final Logger logger = LoggerFactory.getLogger(GrouponRule.class);


    /**
     * true 失效，false  不失效
     * @return
     */
    @Override
    public boolean isDisabled() {

        if(this.isStatusCode()){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isStrategyValid(){
        List<GrouponRuleStrategy> strategies=this.getStrategyList();

        if(strategies==null){
            return false;
        }
        //排序
        strategies.sort((a,b)->{return a.getLowerBound()-b.getLowerBound();});

        //没有策略--false
        if(strategies.size()==0){
            return false;
        }
        else {
            for (int i = 0; i < strategies.size() - 1; i++) {
                GrouponRuleStrategy strategy = strategies.get(0);
                if(!strategy.beValid()){
                    return false;
                }
                else if (strategy.getUpperBound()!= strategies.get(i + 1).getLowerBound()-1){
                    return false;
                }
            }
            if(strategies.get(strategies.size()-1).getUpperBound()!=null){
                return false;
            }
        }

        return true;
    }

    /**
     * 是否有效
     * @return
     */
    @Override
    public boolean isValid() {
        if(super.isValid()&&this.isStrategyValid()){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 团购是否等待结束
     * @return
     */
    @Override
    public boolean isWaitFinish() {
//        if(super.isAlreadyEnd()){
//            LocalDate now=LocalDate.now();
//            this.getPromotionEndTime().toLocalDate();
//            if(now.equals(this.getPromotionEndTime().toLocalDate())){
//                return true;
//            }
//        }
        return false;
    }

    /**
     * 团购活动已经正常结束
     * @return
     */
    @Override
    public boolean isFinished() {

        //进行中
        if(this.isStatusCode()){
            return false;
        }
        else {
            return true;
        }
    }

    private GrouponRulePo realObj;

    public GrouponRule(GrouponRulePo grouponRulePo){
       this.setRealObj(grouponRulePo);
    }

    private List<GrouponRuleStrategy> strategyList;

    public GrouponRule() {

    }

    public GrouponRule(Integer id) {
        this.setId(id);
    }

    /**
     * 计算折扣(给定时操作做)
     * @param nums
     * @return
     */
    public BigDecimal cacuGrouponRefund(Integer nums){
        //成团人数 nums

        //策略
        List<GrouponRuleStrategy>strategies=this.getStrategyList();
        //按lowerbound从小到大排序
        strategies.sort((a,b)->{return a.getLowerBound()-a.getUpperBound();});
        //判断在哪个区间
        BigDecimal levelRate=BigDecimal.ZERO;
        for(GrouponRuleStrategy strategy:strategies){
            if(strategy.getLowerBound()<=nums){
                if(strategy.getUpperBound()==null||strategy.getUpperBound()>=nums){
                    //在这个区间,用这个rate
                    levelRate=strategy.getDiscountRate();
                }
            }
        }

        //有打折
        return levelRate;
    }


    public GrouponRule(Integer id,LocalDateTime time){
        this.setGmtModified(LocalDateTime.now());
        this.setId(id);
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
    public List<GrouponRuleStrategy> getStrategyList() {
        String jsonString = realObj.getGrouponLevelStrategy();
        logger.debug("jsonString = " + jsonString);
        if(jsonString==null){
            return null;
        }
        else {
            JsonParser jp = new JsonParser();
            JsonObject jo = jp.parse(jsonString).getAsJsonObject();
            JsonArray messageArray = jo.get("strategy").getAsJsonArray();
            List<GrouponRuleStrategy> strategyList= JSON.parseArray(messageArray.toString(),GrouponRuleStrategy.class);
            return strategyList;
        }
    }

    /**
     * 设置团购的level策略
     * @param strategyList
     */
    public void setStrategyList(List<GrouponRuleStrategy> strategyList) {

        Map<String, Object> jsonObj = new HashMap<String, Object>(2);
        jsonObj.put("strategy", strategyList);

        String jsonString = JacksonUtil.toJson(jsonObj);
        realObj.setGrouponLevelStrategy(jsonString);
        this.strategyList = strategyList;
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

    public void setStatusCode(boolean statusCode) {
        realObj.setStatusCode(statusCode);
    }


    public boolean isStatusCode() {
        return realObj.isStatusCode();
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


    public String getGrouponLevelStragety() {
        return realObj.getGrouponLevelStrategy();
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        realObj.setGmtModified(gmtModified);
    }


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
