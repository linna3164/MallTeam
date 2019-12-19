package com.xmu.discount.dao;

import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRulePo;
import com.xmu.discount.mapper.CouponMapper;
import com.xmu.discount.mapper.CouponRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    @Autowired
    private CouponRuleMapper couponRuleMapper;

    /**
     * 获取某种优惠券规则的全部优惠券
     * @param couponRuleId
     * @return
     */
    public List<Coupon>listCouponByCouponRuleId(Integer couponRuleId){
        return couponMapper.listCouponByCouponRuleId(couponRuleId);
    }

    /**
     * 用ID获取优惠券
     * @param id 优惠券id
     * @return 优惠券，带优惠券的优惠券规则一起返回
     */
    public Coupon getCouponById(Integer id){
      Coupon coupon=couponMapper.getCouponById(id);
      CouponRulePo couponRulePo=couponRuleMapper.getCouponRuleById(coupon.getCouponRuleId());
        CouponRule couponRule=new CouponRule(couponRulePo);
      coupon.setCouponRule(couponRule);
      return coupon;
    };



    /**
     * 查看所有被领取的优惠券
     * @return
     */
    public List<Coupon> listCoupons(){
        return couponMapper.listCoupons();
    };


    /**
     * 新增优惠券
     * @param coupon
     * @return
     */
    public boolean addCoupon(Coupon coupon){
        coupon.setGmtCreate(LocalDateTime.now());
        coupon.setGmtModified(LocalDateTime.now());
        coupon.setBeDeleted(false);
        return couponMapper.addCoupon(coupon);
    };





    /**
     * 修改优惠券
     * @param coupon
     * @return
     */
    public boolean updateCouponById(Coupon coupon)
    {
        coupon.setGmtModified(LocalDateTime.now());
        return couponMapper.updateCouponById(coupon);
    };


    /**
     * 根据优惠券规则id和用户id查找优惠券
     * @param couponRuleId
     * @param userId
     * @return
     */
    public List<Coupon> listCouponByCouponRuleIdAndUserId(Integer couponRuleId, Integer userId){
        return  couponMapper.listCouponByCouponRuleIdAndUserId(couponRuleId,userId);
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


    public  boolean deleteCouponById(Integer id){
        Coupon coupon=new Coupon();
        coupon.setId(id);
        coupon.setGmtModified(LocalDateTime.now());
        coupon.setBeDeleted(true);
        return couponMapper.updateCouponById(coupon);
    }
}
