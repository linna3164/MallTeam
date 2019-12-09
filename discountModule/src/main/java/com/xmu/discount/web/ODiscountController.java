package com.xmu.discount.web;

import com.xmu.discount.standard.DiscountController;

import javax.validation.constraints.NotNull;

public class ODiscountController implements DiscountController {

    @Override
    public Object list(String name, Short type, Short status, Integer page, Integer limit, String sort, String order) {
        return null;
    }

    @Override
    public Object listuser(Integer userId, Integer id, Short status, Integer page, Integer limit, String sort, String order) {
        return null;
    }

    @Override
    public Object create(LitemallCoupon coupon) {
        return null;
    }

    @Override
    public Object read(Integer id) {
        return null;
    }

    @Override
    public Object update(Integer id, LitemallCoupon coupon) {
        return null;
    }

    @Override
    public Object delete(Integer id, LitemallCoupon coupon) {
        return null;
    }

    @Override
    public Object mylist(Integer userId, Short status, Integer page, Integer limit, String sort, String order) {
        return null;
    }

    @Override
    public Object selectlist(Integer userId, List<Integer> cartIds) {
        return null;
    }

    @Override
    public Object calcDiscount(List<Integer> cartIds, Integer couponId) {
        return null;
    }

    @Override
    public Object receive(Integer userId, String body) {
        return null;
    }

    @Override
    public Object update(LitemallGrouponRules grouponRules) {
        return null;
    }

    @Override
    public Object create(LitemallGrouponRules grouponRules) {
        return null;
    }

    @Override
    public Object delete(LitemallGrouponRules grouponRules) {
        return null;
    }

    @Override
    public Object list(Integer page, Integer limit, String sort, String order) {
        return null;
    }

    @Override
    public Object detail(Integer userId, @NotNull Integer grouponRuleId) {
        return null;
    }

    @Override
    public Object my(Integer userId, Integer showType) {
        return null;
    }
}
