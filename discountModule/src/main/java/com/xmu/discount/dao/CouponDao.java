package com.xmu.discount.dao;

import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponDto;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRuleDto;
import com.xmu.discount.mapper.CouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.oomall.domain.coupon.Coupon;
import xmu.oomall.domain.coupon.CouponRule;
import xmu.oomall.domain.coupon.CouponRulePo;
import xmu.oomall.mapper.CouponMapper;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description: 优惠卷的DAO
 * @Date: Created in 17:02 2019/11/5
 * @Modified By:
 **/

@Repository
public class CouponDao {

    @Autowired
    private CouponMapper couponMapper;

    /**
     * 用ID获取优惠券
     * @param id 优惠券id
     * @return 优惠券，带优惠券的优惠券规则一起返回
     */
    public Coupon getCouponById(Integer id){
      Coupon coupon=couponMapper.getCouponById(id);
      CouponRule couponRule=couponMapper.getCouponRuleById(coupon.getCouponRuleId());
      coupon.setCouponRule(couponRule);
      return coupon;
    };

    /**
     * 用id找优惠卷规则
     * @param id 优惠卷规则id
     * @return 优惠
     */
    public CouponRule getCouponRuleById(Integer id){
        CouponRule couponRule=couponMapper.getCouponRuleById(id);
        return couponRule;
    };

    /**
     * 查看所有被领取的优惠券
     * @return
     */
    public List<Coupon> getCoupons(){
        return couponMapper.getCoupons();
    };

    /**
     * 查看所有类型的优惠券
     * @return
     */
    public List<CouponRule> getCouponRules(){
        return couponMapper.getCouponRules();
    };

    /**
     * 新增优惠券
     * @param coupon
     * @return
     */
    public int addCoupon(Coupon coupon){
        return couponMapper.addCoupon(coupon);
    };

    /**
     * 新增优惠券规则
     * @param couponRule
     * @return
     */
    public int addCouponRule(CouponRule couponRule){
        return couponMapper.addCouponRule(couponRule);
    };

    /**
     * 修改优惠券规则
     * @param couponRule
     * @return
     */
    public int updateCouponRuleById(CouponRule couponRule){
        return couponMapper.updateCouponRuleById(couponRule);
    };

    /**
     * 修改优惠券
     * @param coupon
     * @return
     */
    public int updateCouponById(Coupon coupon)
    {
        return couponMapper.updateCouponById(coupon);
    };


    /**
     * 根据优惠券规则id和用户id查找优惠券
     * @param couponRuleId
     * @param userId
     * @return
     */
    public List<Coupon> listCouponByCouponRuleIdAndUserId(Integer couponRuleId, Integer userId){
          return  couponMapper.listCouponByCouponRuleId()
    };

    /**
     * 获取用户的所有优惠券
     * @param userId
     * @return
     */
    public List<Coupon> listCouponOfUser(Integer userId){
        List<Coupon> coupons=couponMapper.listCouponOfUser(userId);
        return coupons;
    }

    /**
     * 删除优惠券规则
     */
    public void deleteCouponRuleById(Integer id){
        CouponRule couponRule=new CouponRule(id,true);
        couponMapper.updateCouponRuleById(couponRule);
    }
}
