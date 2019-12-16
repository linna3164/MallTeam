package com.xmu.discount.domain.coupon;

import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.GoodsPo;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.OrderItem;
import com.xmu.discount.exception.UnsupportException;
import com.xmu.discount.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class CouponRule extends PromotionRule {

    @Override
    public boolean isDisabled() {
        //TODO:根据标准组
        return false;
    }

    @Override
    public Order getPayment(Order order) throws UnsupportException {
        logger.debug("cacuCouponPrice参数: Order = "+ order + "couponSn = "+order.getCouponId());

        List<OrderItem> validItems = this.getValidItems(order.getOrderItemList());

        if (validItems.size() != 0) {
            List<OrderItem> newItems = this.getStrategy().cacuDiscount(validItems, "oo");
            order.getOrderItemList().addAll(newItems);
        }
        order.setPaymentList(null);

        for(OrderItem orderItem:order.getOrderItemList()){
            orderItem.setItemType(0);
        }

        logger.debug("cacuCouponPrice返回");
        return order;
    }

    @Override
    public LocalDateTime getpromotionRuleStartTime() {
        return this.getBeginTime();
    }

    @Override
    public LocalDateTime getPromotionEndTime() {
        return this.getEndTime();
    }

    @Override
    public Integer getPromotionGoodsId() {
        return null;
    }



    /**
     *创建优惠券（）
     * @return
     */
    public Coupon createCoupon(Integer userId){
        if(this.canGet()){
            Coupon coupon=new Coupon(this,userId);

            //已领取张数加一
            this.setCollectedNum(getCollectedNum()+1);
            return coupon;
        }
        return null;
    }

    /**
     * 获取用户领取优惠券的结束时间
     */
    public LocalDateTime getCouponEndTimes(){
        CouponRule.TimeStatus timeStatus=this.getTimeStatus();
        if(timeStatus.equals(CouponRule.TimeStatus.LIMIT)){
            return this.getEndTime();
        }
        else if(timeStatus.equals(CouponRule.TimeStatus.PERIOD)){
            LocalDateTime now = LocalDateTime.now();
            return now.plusDays(this.getValidPeriod());
        }
        return null;
    }

    /**
     *ordeItems是否可用该优惠券规则
     * @param orderItems
     * @return
     */
    public boolean isOkToUse(List<OrderItem> orderItems) {

        //能用于该优惠券规则的orderItem项
        List<OrderItem> validItems = this.getValidItems(orderItems);

        if (validItems.size() != 0) {
            return this.getStrategy().isOkToUse(validItems);
        }
        return false;
    }

//        /**
//         * 优惠券规则是否已开始
//         * @return
//         */
//    public  boolean isAlreadyStart(){
//        LocalDateTime now = LocalDateTime.now();
//        return (this.getBeginTime().isBefore(now));
//    }
//
//    /**
//     * 优惠券规则是否已经结束
//     * @return
//     */
//    public  boolean isAlreadyEnd(){
//        LocalDateTime now = LocalDateTime.now();
//        return (this.getEndTime().isBefore(now));
//    }

    /**
     * 优惠券规则能否被领取（有剩余张数，且状态是inprocess）
     * @return
     */
    public boolean canGet(){
        if(this.isLeft()&&this.getActiveStatus().equals(ActiveStatus.INPROCESS)){
            return true;
        }
        return false;
    }

    /**
     * 优惠券规则是否有剩余
     * @return
     */
    public boolean isLeft(){
        return this.getCollectedNum()<this.getTotal();
    }

//    /**
//     * 优惠券规则是否在时间内
//     * @return
//     */
//    public boolean isInTime(){
//        LocalDateTime now = LocalDateTime.now();
//        return this.getBeginTime().isBefore(now)&&
//                this.getEndTime().isAfter(now);
//    }

    /**
     * 获得能用于此优惠卷规则的明细  ok
     * @param items 订单明细
     * @return 适用的订单明细
     */
    public List<OrderItem> getValidItems(List<OrderItem> items){
        logger.debug("getValidItems参数：items = "+items);
        List<OrderItem> validItems = new ArrayList<OrderItem>(items.size());
        for (OrderItem item: items){
            GoodsPo goods = item.getProduct().getGoodsPo();
            logger.debug("goods = "+goods);
            if (this.isCanUsedOnGoods(goods.getId())){
                validItems.add(item);
            }
        }
        return validItems;
    }

    /**
     * 获得折扣策略
     * JSON格式:{ name：“XXX”, obj:{XXX}}
     * @return 折扣策略对象
     */
    public AbstractCouponStrategy getStrategy() {
        logger.debug("getStrategy参数：");
        String jsonString = realObj.getStrategy();
        logger.debug("jsonString = "+ jsonString);
        jsonString = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString);
        String strategyName = JacksonUtil.parseString(jsonString, "name");

        AbstractCouponStrategy strategy = null;
        try {
            strategy = (AbstractCouponStrategy) JacksonUtil.parseObject(jsonString, "obj", Class.forName(strategyName));
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return strategy;
    }

    /**
     * 设置折扣策略
     * JSON格式:{ strategy：“XXX”, obj:{XXX}}
     * @param strategy 策略对象
     *
     */
    public void setStrategy(AbstractCouponStrategy strategy) {

        Map<String, Object> jsonObj = new HashMap<String, Object>(2);
        jsonObj.put("name", strategy.getClass().getName());
        jsonObj.put("obj",strategy);
        String jsonString = JacksonUtil.toJson(jsonObj);
        realObj.setStrategy(jsonString);
    }

    /**
     * 判断商品是否能用于这张优惠卷
     *
     * @param goodsId 商品的id
     * @return
     */
    public boolean isCanUsedOnGoods(Integer goodsId) {

        Set<Integer> goodsIds = new TreeSet<>();
        goodsIds.clear();
        goodsIds.addAll(this.getGoodsIds());

        if (goodsIds.contains(0)){
            return true;
        } else {
            return goodsIds.contains(goodsId);
        }
    }

    /**
     * 获得适用商品id列表
     * { gIDs：[xxx,xxx,xxx,xxx,xxx]}
     * @return 商品id列表
     */
    public List<Integer> getGoodsIds() {
        logger.debug("getGoodsIds参数");
        String jsonString1 = realObj.getGoodsList1();
        String jsonString2=realObj.getGoodsList2();
        jsonString1 = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString1);
        jsonString2 = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString2);
        logger.debug("jsonString1 =" +jsonString1+", jsonString2="+jsonString2);
        List<Integer> goodsIds1=JacksonUtil.parseIntegerList(jsonString1, "gIDs");
        List<Integer> goodsIds2=JacksonUtil.parseIntegerList(jsonString2, "gIDs");
        goodsIds1.addAll(goodsIds2);
        return goodsIds1;
    }

    /**
     * 设置适用商品id列表 两个列表
     * { gIDs1：[xxx,xxx,xxx,xxx,xxx]}, { gIDs2：[xxx,xxx,xxx,xxx,xxx]}
     */
    public void setGoodsIds(List<Integer> goodsIds) {

        int listSize=goodsIds.size();
        if(listSize<=MAXIDNUMS){
            Map<String,Object> idMap = new HashMap<String, Object>(1);
            idMap.put("gIDs1", goodsIds);
            realObj.setGoodsList1(JacksonUtil.toJson(idMap));
        }
        else if(listSize>MAXIDNUMS &&listSize<=MAXIDNUMS*2){
            Map<String,Object> idMap1= new HashMap<String, Object>(1);
            idMap1.put("gIDs1", goodsIds.subList(0,MAXIDNUMS-1));
            realObj.setGoodsList1(JacksonUtil.toJson(idMap1));

            Map<String,Object> idMap2 = new HashMap<String, Object>(1);
            idMap2.put("gIDs2", goodsIds.subList(MAXIDNUMS,listSize-1));
            realObj.setGoodsList2(JacksonUtil.toJson(idMap2));
        }
        else{
            //TODO: 抛出Exception
        }

    }

    /**
     * 获得优惠的费用
     * @param order 订单
     * @param couponSn 优惠卷号
     * @return
     */
    public void cacuCouponPrice(Order order, String couponSn){

        logger.debug("cacuCouponPrice参数: Order = "+ order + "couponSn = "+couponSn);

        List<OrderItem> validItems = this.getValidItems(order.getOrderItemList());

        if (validItems.size() != 0) {
            List<OrderItem> newItems = this.getStrategy().cacuDiscount(validItems, couponSn);
            order.getOrderItemList().addAll(newItems);
        }
        logger.debug("cacuCouponPrice返回");
    }


    public TimeStatus getTimeStatus() {
        if(this.getValidPeriod()>0){
            return TimeStatus.PERIOD;
        }
        else{
            return TimeStatus.LIMIT;
        }

    }




    private static final Logger logger = LoggerFactory.getLogger(CouponRule.class);
    private static final Integer MAXIDNUMS=2500;

    private Object couponStrategy;//标准组
    private CouponRulePo realObj;
    /**
     * 时间类型
     */
    private TimeStatus timeStatus;

    /**
     * 优惠卷规则的时间类型
     */
    public enum TimeStatus {
        /**
         * 未用
         */
        LIMIT("限制时间", 0),
        /**
         * 已用
         */
        PERIOD("时期", 1);

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
        TimeStatus(String name, Integer value) {
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
     * 活动状态
     */
    private ActiveStatus activeStatus;


    public CouponRule() {

    }

    public CouponRule(Integer id,boolean beDeleted){
        this.setBeDeleted(beDeleted);
        this.setId(id);
    }

    /****************************************************
     * 生成代码
     ****************************************************/



    public void setTimeStatus(TimeStatus timeStatus) {
        this.timeStatus = timeStatus;
    }

    public static Logger getLogger() {
        return logger;
    }

    public CouponRulePo getRealObj() {
        return realObj;
    }

    public void setRealObj(CouponRulePo realObj) {
        this.realObj = realObj;
    }

    public CouponRule(CouponRulePo realObj) {
        this.realObj = realObj;
    }

//    public boolean canEqual(Object other) {
//        return realObj.canEqual(other);
//    }

    @Override
    public Integer getId() {
        return realObj.getId();
    }

    public void setId(Integer id) {
        realObj.setId(id);
    }

    public String getName() {
        return realObj.getName();
    }

    public void setName(String name) {
        realObj.setName(name);
    }

    public String getBrief() {
        return realObj.getBrief();
    }

    public void setBrief(String brief) {
        realObj.setBrief(brief);
    }

    public LocalDateTime getBeginTime() {
        return realObj.getBeginTime();
    }

    public void setBeginTime(LocalDateTime beginTime) {
        realObj.setBeginTime(beginTime);
    }

    public LocalDateTime getEndTime() {
        return realObj.getEndTime();
    }

    public void setEndTime(LocalDateTime endTime) {
        realObj.setEndTime(endTime);
    }

    public String getPicUrl() {
        return realObj.getPicUrl();
    }

    public void setPicUrl(String picUrl) {
        realObj.setPicUrl(picUrl);
    }

    public Integer getValidPeriod() {
        return realObj.getValidPeriod();
    }

    public void setValidPeriod(Integer validPeriod) {
        realObj.setValidPeriod(validPeriod);
    }

    public void setStrategy(String strategy) {
        realObj.setStrategy(strategy);
    }

    public Integer getTotal() {
        return realObj.getTotal();
    }

    public void setTotal(Integer total) {
        realObj.setTotal(total);
    }

    public Integer getCollectedNum() {
        return realObj.getCollectedNum();
    }

    public void setCollectedNum(Integer collectedNum) {
        realObj.setCollectedNum(collectedNum);
    }

    public String getGoodsList1() {
        return realObj.getGoodsList1();
    }

    public void setGoodsList1(String goodsList1) {
        realObj.setGoodsList1(goodsList1);
    }

    public String getGoodsList2() {
        return realObj.getGoodsList2();
    }

    public void setGoodsList2(String goodsList2) {
        realObj.setGoodsList2(goodsList2);
    }

    public Integer getStatusCode() {
        return realObj.getStatusCode();
    }

    public void setStatusCode(Integer statusCode) {
        realObj.setStatusCode(statusCode);
    }

    public LocalDateTime getGmtCreate() {
        return realObj.getGmtCreate();
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        realObj.setGmtCreate(gmtCreate);
    }

    public LocalDateTime getGmtModified() {
        return realObj.getGmtModified();
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        realObj.setGmtModified(gmtModified);
    }

    public Boolean getBeDeleted() {
        return realObj.getBeDeleted();
    }

    public void setBeDeleted(Boolean beDeleted) {
        realObj.setBeDeleted(beDeleted);
    }

    @Override
    public boolean equals(Object o) {
        return realObj.equals(o);
    }


}
