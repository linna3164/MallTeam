package com.xmu.discount.domain.coupon;

import com.xmu.discount.domain.others.domain.GoodsPo;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.OrderItem;
import com.xmu.discount.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

public class CouponRule {

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

    /**
     * 优惠卷的活动状态
     */
    public enum ActiveStatus {
        /**
         * 未开始
         */
        NOTSTART("未开始", 0),
        /**
         * 进行中
         */
        INPROCESS("进行中", 1),
        /**
         * 已结束
         */
        DONE("已结束", 2),
        /**
         * 失效
         */
        DISABLED("失效", 3);

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
         *
         * @param name  名称
         * @param value 值
         */
        ActiveStatus(String name, Integer value) {
            this.value = value;
            this.name = name;
        }

        /**
         * 获得值
         *
         * @return 值
         */
        public Integer getValue() {
            return this.value;
        }

        /**
         * 获得名称
         *
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
     * 优惠券规则是否已开始
     * @return
     */
    public  boolean isAlreadyStart(){
        LocalDateTime now = LocalDateTime.now();
        return (this.getBeginTime().isBefore(now));
    }


    /**
     * 优惠券规则能否被领取（有剩余张数，在活动时间内，不是失效的）
     * @return
     */
    public boolean canGet(){
        if(this.isLeft()&&this.isGoingOn()&&this.getStatusCode()==1){
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

    /**
     * 优惠券规则是否过期
     * @return
     */
    public boolean isGoingOn(){
        LocalDateTime now = LocalDateTime.now();
        return this.getBeginTime().isBefore(now)&&
                this.getEndTime().isAfter(now);
    }

    /**
     * 获得能用于此优惠卷的明细  ok
     * @param items 订单明细
     * @return 适用的订单明细
     */
    private List<OrderItem> getValidItems(List<OrderItem> items){
        logger.debug("getValidItems参数：items = "+items);
        List<OrderItem> validItems = new ArrayList<OrderItem>(items.size());
        for (OrderItem item: items){
            GoodsPo goods = item.getProduct().getGoodsPo();
            logger.debug("goods = "+goods);
            if (this.isUsedOnGoods(goods.getId())){
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
    public boolean isUsedOnGoods(Integer goodsId) {

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

//    public ActiveStatus getActiveStatus() {
//        //通过属性判断是否失效
//        if(){
//
//        }
//        else if()
//    }



    public CouponRule() {

    }

    public CouponRule(Integer id,boolean beDeleted){
        this.setBeDeleted(beDeleted);
        this.setId(id);
    }

    /****************************************************
     * 生成代码
     ****************************************************/


    public void setActiveStatus(ActiveStatus activeStatus) {
        this.activeStatus = activeStatus;
    }

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

    public boolean canEqual(Object other) {
        return realObj.canEqual(other);
    }

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
