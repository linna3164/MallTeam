package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.inter.GoodsFeign;
import com.xmu.discount.inter.OrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class GrouponServiceImpl extends PromotionServiceImpl{

    @Autowired
    GrouponRuleDao grouponRuleDao;

    @Autowired
    OrderFeign orderFeign;




    /**
     *
     * @param promotionRule
     */
    @Override
    public void toDoSomthingAfterDisable(PromotionRule promotionRule)   {

    }

    /**
     * 定时任务， 每天晚上搜索前一天24小时之内的完成的grouponpo，然后对这
     * 些po都调用这个接口，根据grouponpo里的团购开始时间、结束时
     * 间和goodsid检查所有买了这个商品的order，返给discount模块就
     * 行
     */
    @Scheduled(cron = "0 0 0 ? * *")
    public void caculateGroupon(){

        System.out.println("into groupon1");
        List<GrouponRule> grouponRuleList = listNeedCalcuGrouponRule();
        for(GrouponRule grouponRule:grouponRuleList)
        {

//                Integer numstest = orderFeign.getGrouponOrders(grouponRule.getRealObj());
                System.out.println("into try");

//            catch (Exception e){
                System.out.println("into catch");

                Integer nums = orderFeign.getGrouponOrders(grouponRule.getRealObj());
                BigDecimal rate = grouponRule.cacuGrouponRefund(nums);


                System.out.println("nums:"+nums);
                System.out.println("rate"+rate);

                orderFeign.refundGrouponOrder(grouponRule.getRealObj(),rate);

            }



    }


    /**
     * 得到waitfinish的团购规则
     * @return
     */
    public List<GrouponRule> listNeedCalcuGrouponRule(){
        List<? extends PromotionRule> promotionRules=grouponRuleDao.listPromotions();

        List<GrouponRule> res=new ArrayList<>();
        LocalDate yesterday=LocalDate.now().minusDays(1);

        for(PromotionRule promotionRule:promotionRules){

            LocalDate endDay=promotionRule.getPromotionEndTime().toLocalDate();

            if(endDay.equals(yesterday)){
                res.add((GrouponRule) promotionRule);
            }
        }

        System.out.println(res);
        return res;
    }



    /**
     * 管理员查询一个商品的团购规则（不带商品）
     * @param goodsId
     * @return
     */
    public List<? extends PromotionRule> listGrouponRuleByGoodsId(Integer goodsId) {
        List<? extends PromotionRule> grouponRules=grouponRuleDao.listPromotionRuleByGoodsId(goodsId);

        return grouponRules;
    }

}
