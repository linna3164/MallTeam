package com.xmu.discount.domain.coupon;

import com.xmu.discount.domain.others.GoodsDto;
import com.xmu.discount.domain.others.OrderDto;
import com.xmu.discount.domain.others.OrderItemDto;
import com.xmu.discount.standard.CouponRule;
import com.xmu.discount.util.JacksonUtil;
import org.apache.ibatis.type.Alias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Alias("couponRuleDto")
public class CouponRuleDto extends CouponRule {

    private static final Logger logger = LoggerFactory.getLogger(CouponRuleDto.class);
    private static final Integer MAXIDNUMS=2500;

    /**
     * 获得能用于此优惠卷的明细  ok
     * @param items 订单明细
     * @return 适用的订单明细
     */
    private List<OrderItemDto> getValidItems(List<OrderItemDto> items){
        logger.debug("getValidItems参数：items = "+items);
        List<OrderItemDto> validItems = new ArrayList<OrderItemDto>(items.size());
        for (OrderItemDto item: items){
            GoodsDto goods = item.getProductDto().getDesc();
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
        String jsonString = super.getStrategy();
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
        super.setStrategy(jsonString);
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

        if (goodsIds.contains(GoodsDto.ALL_GOODS.getId())){
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
        String jsonString1 = super.getGoodsList1();
        String jsonString2=super.getGoodsList2();
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
            super.setGoodsList1(JacksonUtil.toJson(idMap));
        }
        else if(listSize>MAXIDNUMS &&listSize<=MAXIDNUMS*2){
            Map<String,Object> idMap1= new HashMap<String, Object>(1);
            idMap1.put("gIDs1", goodsIds.subList(0,MAXIDNUMS-1));
            super.setGoodsList1(JacksonUtil.toJson(idMap1));

            Map<String,Object> idMap2 = new HashMap<String, Object>(1);
            idMap2.put("gIDs2", goodsIds.subList(MAXIDNUMS,listSize-1));
            super.setGoodsList2(JacksonUtil.toJson(idMap2));
        }
        else{
            //TODO: 抛出Exception
        }

    }

    /**
     * 获得优惠的费用
     * @param orderDto 订单
     * @param couponSn 优惠卷号
     * @return
     */
    public void cacuCouponPrice(OrderDto orderDto, String couponSn){

        logger.debug("cacuCouponPrice参数: Order = "+ orderDto+ "couponSn = "+couponSn);

        List<OrderItemDto> validItems = this.getValidItems(orderDto.getOrderItemDtos());

        if (validItems.size() != 0) {
            List<OrderItemDto> newItems = this.getStrategy().cacuDiscount(validItems, couponSn);
            orderDto.getOrderItemDtos().addAll(newItems);
        }
        logger.debug("cacuCouponPrice返回");
    }


}
