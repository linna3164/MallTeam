package com.xmu.discount.service.impl;

import com.xmu.discount.dao.CouponDao;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.oomall.dao.CouponDao;
import xmu.oomall.domain.coupon.Coupon;
import xmu.oomall.service.CouponService;

import java.util.List;

/**
 * @Author: Ming Qiu
 * @Description:优惠卷服务的实现
 * @Date: Created in 11:03 2019/11/19
 * @Modified By:
 **/
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    @Override
    public Coupon findCouponById(Integer id) {
        return couponDao.getCouponById(id);
    }


    /**
     * 用户领取优惠券
     * @param coupon
     * @return
     */
    @Override
    public Coupon addCoupon(Coupon coupon) {
        List<Coupon> coupons=couponDao.listCouponByCouponRuleIdAndUserId(coupon.getCouponRuleId(),coupon.getUserId());
        CouponRule couponRule=couponDao.getCouponRuleById(coupon.getCouponRuleId());
        if(couponRule==null){
            //TODO:优惠券规则不存在,报错
        }
        coupon.setCouponRule(couponRule);
        //用户还没领取过
        if(coupons.size()>0){
            //优惠券规则可被领取
            if (couponRule.canGet()){
                couponDao.addCoupon(coupon);
                return coupon;
            }
        }
        return null;
    }


    /**
     * 管理员删除优惠券规则
     * @param id
     * @return
     */
    @Override
    public boolean deleteCouponRuleById(Integer id) {
        CouponRule couponRule=new CouponRule(id,true);
        couponDao.updateCouponRuleById(couponRule);
        return true;
    }

    /**
     * 管理员新增优惠券规则
     * @param couponRule
     * @return
     */
    @Override
    public CouponRule addCouponRule(CouponRule couponRule) {
        couponDao.addCouponRule(couponRule);
        return couponRule;
    }

    /**
     * 管理员修改优惠券规则
     * @param couponRule
     * @return
     */
    @Override
    public CouponRule updateCouponRule(CouponRule couponRule) {
        couponDao.updateCouponRuleById(couponRule);
        return couponRule;
    }


    @Override
    public List<Coupon> listOverDueCouponOfUser(Integer userId) {
        return null;
    }

    @Override
    public List<Coupon> listUsedCouponOfUser(Integer userId) {
        return null;
    }

    @Override
    public List<Coupon> listUnUsedCouponOfUser(Integer userId) {
        return null;
    }
}
