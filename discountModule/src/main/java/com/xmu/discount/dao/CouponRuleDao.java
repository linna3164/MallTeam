package com.xmu.discount.dao;

import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRulePo;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.mapper.CouponMapper;
import com.xmu.discount.mapper.CouponRuleMapper;
import com.xmu.discount.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CouponRuleDao implements PromotionRuleDao {

    @Autowired
    private CouponRuleMapper couponRuleMapper;

    /**
     * 用id找优惠卷规则
     * @param id 优惠卷规则id
     * @return 优惠
     */

    @Override
    public  PromotionRule getPromotionRuleById(Integer id){
        CouponRule couponRule=couponRuleMapper.getCouponRuleById(id);
        return couponRule;
    }


    /**
     * 查看所有优惠券规则
     * @return
     */
    @Override
    public List<PromotionRule> getPromotionRules() {
        List<PromotionRule>promotionRules=new ArrayList<>();
        List<CouponRule> couponRules=couponRuleMapper.getCouponRules();
        promotionRules.addAll(couponRules);
        return promotionRules;
    }

    /**
     * 新增优惠券规则
     * @param promotionRule
     * @return
     */
    @Override
    public int addPromotionRule(PromotionRule promotionRule) {
        return couponRuleMapper.addCouponRule((CouponRule) promotionRule);
    }

    /**
     * 获取商品的所有优惠券规则
     * @param goodsId
     * @return
     */
    @Override
    public List<PromotionRule> listPromotionRuleByGoodsId(Integer goodsId) {
        //TODO:

        return null;
    }

    /**
     * 修改优惠券规则
     * @param promotionRule
     * @return
     */
    @Override
    public int updatePromotionRuleById(PromotionRule promotionRule) {
        return couponRuleMapper.updateCouponRuleById((CouponRule) promotionRule);
    }

    /**
     * 删除优惠券规则
     * @param id
     */
    @Override
    public void deletePromotionRuleById(Integer id) {
        CouponRule couponRule=new CouponRule(id,true);
        couponRuleMapper.updateCouponRuleById(couponRule);
    }
}
