package com.xmu.discount.service.impl;

import com.xmu.discount.dao.GrouponRuleDao;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.others.domain.Payment;
import com.xmu.discount.exception.SeriousException;
import com.xmu.discount.inter.GoodsFeign;
import com.xmu.discount.inter.OrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
     * @throws SeriousException
     */
    @Override
    public void toDoSomthingAfterDisable(PromotionRule promotionRule) throws SeriousException {

    }

    /**
     * 定时任务， 每天晚上搜索前一天24小时之内的完成的grouponpo，然后对这
     * 些po都调用这个接口，根据grouponpo里的团购开始时间、结束时
     * 间和goodsid检查所有买了这个商品的order，返给discount模块就
     * 行
     */
    @Scheduled(cron = "0 10 0 ? * *")
    public void caculateGroupon(){
        List<GrouponRule> grouponRuleList = listNeedCalcuGrouponRule();
        for(GrouponRule grouponRule:grouponRuleList)
        {
            Integer nums = orderFeign.getGrouponOrders(grouponRule.getRealObj());
            BigDecimal rate = grouponRule.cacuGrouponRefund(nums);

            orderFeign.refundGrouponOrder(grouponRule.getRealObj(),rate);

            //设置团购规则为已经结束（finish）
            grouponRule.setStatusCode(false);
        }


    }


    /**
     * 得到waitfinish的团购规则
     * @return
     */
    public List<GrouponRule> listNeedCalcuGrouponRule(){
        List<? extends PromotionRule> promotionRules=grouponRuleDao.listPromotions();

        List<GrouponRule> res=new ArrayList<>();
        for(PromotionRule promotionRule:promotionRules){
            if(promotionRule.getActiveStatus().equals(PromotionRule.ActiveStatus.WAITFINISH)){
                res.add((GrouponRule) promotionRule);
            }
        }
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
