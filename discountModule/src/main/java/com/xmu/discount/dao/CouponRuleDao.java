package com.xmu.discount.dao;

import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRulePo;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.mapper.CouponRuleMapper;
import com.xmu.discount.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    public  CouponRule getCouponRuleById(Integer id){
        CouponRulePo couponRulePo=couponRuleMapper.getCouponRuleById(id);
        CouponRule couponRule=new CouponRule(couponRulePo);
        return couponRule;
    }


    /**
     * 查看所有优惠券规则
     * @return
     */
    public List<CouponRule> listCouponRule() {
       List<CouponRulePo> couponRulePos=couponRuleMapper.listCouponRules();
       List<CouponRule> couponRules = new ArrayList<CouponRule>();
       for(int i=0;i<couponRulePos.size();i++)
       {
           CouponRule couponRule=new CouponRule(couponRulePos.get(i));
           couponRules.add(couponRule);
       }
           return couponRules;
    }

    /**
     * 新增优惠券规则
     * @param couponRule
     * @return
     */
    public int addCouponRule(CouponRule  couponRule) {
        CouponRulePo couponRulePo=couponRule.getRealObj();
        couponRule.setGmtCreate(LocalDateTime.now());
        couponRule.setGmtModified(LocalDateTime.now());
        return couponRuleMapper.addCouponRule(couponRulePo);
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
        CouponRulePo couponRulePo=couponRule.getRealObj();
        couponRulePo.setGmtModified(LocalDateTime.now());
        return couponRuleMapper.updateCouponRuleById(couponRulePo);
    }

    /**
     * 删除优惠券规则
     * @param id
     */
    public void deletePromotionRuleById(Integer id) {
        CouponRulePo couponRule=new CouponRulePo(id,true);
        couponRule.setGmtModified(LocalDateTime.now());
        couponRuleMapper.updateCouponRuleById(couponRule);
    }
}
