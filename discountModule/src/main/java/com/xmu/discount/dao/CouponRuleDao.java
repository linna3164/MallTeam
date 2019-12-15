package com.xmu.discount.dao;

import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.mapper.CouponRuleMapper;
import com.xmu.discount.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CouponRuleDao  {

    @Autowired
    private CouponRuleMapper couponRuleMapper;

    /**
     * 用id找优惠卷规则
     * @param id 优惠卷规则id
     * @return 优惠
     */

    public  CouponRule getPromotionRuleById(Integer id){
        CouponRule couponRule=couponRuleMapper.getCouponRuleById(id);
        return couponRule;
    }


    /**
     * 查看所有优惠券规则
     * @return
     */
    public List<CouponRule> listCouponRule() {
       return couponRuleMapper.listCouponRules();
    }

    /**
     * 新增优惠券规则
     * @param couponRule
     * @return
     */
    public int addCouponRule(CouponRule couponRule) {
        return couponRuleMapper.addCouponRule(couponRule);
    }

//    /**
//     * 获取商品的所有优惠券规则
//     * @param goodsId
//     * @return
//     */
//    public List<PromotionRule> listPromotionRuleByGoodsId(Integer goodsId) {
//
//        List<PromotionRule> promotionRules=new ArrayList<PromotionRule>();
//        List<CouponRule> couponRules=couponRuleMapper.listCouponRules();
//        for(CouponRule couponRule:couponRules){
//            String list1=couponRule.getGoodsList1();
//            String list2=couponRule.getGoodsList2();
//            List<Integer> l1= JacksonUtil.parseIntegerList(list1,"");
//        }
//        return null;
//    }

    /**
     * 修改优惠券规则
     * @param couponRule
     * @return
     */
    public int updateCouponRuleById(CouponRule couponRule) {
        return couponRuleMapper.updateCouponRuleById(couponRule);
    }

    /**
     * 删除优惠券规则
     * @param id
     */
    public void deletePromotionRuleById(Integer id) {
        CouponRule couponRule=new CouponRule(id,true);
        couponRuleMapper.updateCouponRuleById(couponRule);
    }
}
