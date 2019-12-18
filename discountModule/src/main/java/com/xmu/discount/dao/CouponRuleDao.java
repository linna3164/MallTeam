package com.xmu.discount.dao;

import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRulePo;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.exception.PromotionNotFoundException;
import com.xmu.discount.exception.UpdatedDataFailedException;
import com.xmu.discount.mapper.CouponRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CouponRuleDao implements PromotionRuleDao  {

    @Autowired
    private CouponRuleMapper couponRuleMapper;


    @Override
    public void setDisable(PromotionRule promotionRule) throws UpdatedDataFailedException {
        CouponRule couponRule=(CouponRule) promotionRule;
        CouponRule pre=new CouponRule(couponRule.getId());
        //TODO:标准组！！！
        pre.setStatusCode(false);
        this.updatePromotionRuleById(couponRule);
    }

    @Override
    public PromotionRule getPromotionRuleById(Integer id) throws PromotionNotFoundException {
        CouponRulePo couponRulePo=couponRuleMapper.getCouponRuleById(id);
        CouponRule couponRule=new CouponRule(couponRulePo);
        return couponRule;
    }

    @Override
    public List<? extends PromotionRule> listPromotions() {
        List<CouponRulePo> couponRulePos=couponRuleMapper.listCouponRules();
        List<CouponRule> couponRules = new ArrayList<CouponRule>();
        for(int i=0;i<couponRulePos.size();i++)
        {
            CouponRule couponRule=new CouponRule(couponRulePos.get(i));
            couponRules.add(couponRule);
        }
        return couponRules;
    }

    @Override
    public int addPromotionRule(PromotionRule promotionRule) {
        CouponRule couponRule=(CouponRule)promotionRule;
        CouponRulePo couponRulePo=couponRule.getRealObj();
        couponRule.setGmtCreate(LocalDateTime.now());
        couponRule.setGmtModified(LocalDateTime.now());
        couponRule.setStatusCode(true);
        couponRule.setBeDeleted(false);
        int n=couponRuleMapper.addCouponRule(couponRulePo);
        return n;
    }

    @Override
    public List<? extends PromotionRule> listPromotionRuleByGoodsId(Integer goodsId) {
        List<CouponRulePo> couponRulePos=couponRuleMapper.listCouponRules();
        List<CouponRule> couponRules = new ArrayList<CouponRule>();
        for(int i=0;i<couponRulePos.size();i++)
        {
            CouponRule couponRule=new CouponRule(couponRulePos.get(i));
            couponRules.add(couponRule);
        }
        return couponRules;
    }

    @Override
    public boolean updatePromotionRuleById(PromotionRule promotionRule) throws UpdatedDataFailedException {
        CouponRule couponRule=(CouponRule)promotionRule;
        CouponRulePo couponRulePo=couponRule.getRealObj();
        couponRulePo.setGmtModified(LocalDateTime.now());
        int res= couponRuleMapper.updateCouponRuleById(couponRulePo);
        if(res==0){
            throw new UpdatedDataFailedException();
        }
        else{
            return true;
        }
    }



//    /**
//     * 查看所有优惠券规则
//     * @return
//     */
//    public List<? extends CouponRule> listCouponRule() {
//       List<CouponRulePo> couponRulePos=couponRuleMapper.listCouponRules();
//       List<CouponRule> couponRules = new ArrayList<CouponRule>();
//       for(int i=0;i<couponRulePos.size();i++)
//       {
//           CouponRule couponRule=new CouponRule(couponRulePos.get(i));
//           couponRules.add(couponRule);
//       }
//           return couponRules;
//    }



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
     * 删除优惠券规则
     * @param id
     */
    @Override
    public void deletePromotionRuleById(Integer id) {
        CouponRulePo couponRule=new CouponRulePo(id,true);
        couponRule.setGmtModified(LocalDateTime.now());
        couponRuleMapper.updateCouponRuleById(couponRule);
    }
}
