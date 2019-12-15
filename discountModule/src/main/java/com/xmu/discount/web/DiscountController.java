package com.xmu.discount.web;

import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRulePo;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.CartItem;
import com.xmu.discount.service.CouponService;
import com.xmu.discount.service.impl.*;
import com.xmu.discount.util.ResponseUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author zhc
 * @create 2019/12/4 8:30
 * couponRule,coupon,goupon,gouponRule一律不加s,列表才加s
 */

@RestController
@RequestMapping("/discount")

public class DiscountController {

    @Autowired
    @Qualifier("promotionServiceImpl")
    private PromotionServiceImpl discountService;


    @Autowired
    private CouponServiceImpl couponService;

    @Autowired
    private CouponRuleServiceImpl couponRuleService;

    @Autowired
    @Qualifier("grouponServiceImpl")
    private PromotionServiceImpl grouponService;

    @Autowired
    @Qualifier("presaleServiceImpl")
    private PromotionServiceImpl presaleService;


    /**
     *  管理员查看所有的优惠券规则
     * @return
     */
    @GetMapping("/couponRules")
    public List<CouponRule> getCouponRules(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer limit){
        return couponRuleService.listCouponRule();
    }

    /**
     * 管理员添加优惠券规则
     * @param couponRulePo
     * @return
     */
    @PostMapping("/couponRules")
    public CouponRule addCouponRule(CouponRulePo couponRulePo){
        CouponRule couponRule=new CouponRule(couponRulePo);
        return couponRuleService.addCouponRule(couponRule);
    }

    /**
     * 通过id查找优惠券规则
     * @param id
     * @return
     */
    @GetMapping("/couponRules/{id}")
    public CouponRule findCouponRuleById(@PathVariable Integer id){
        return couponRuleService.getCouponRuleById(id);
    }

    /**
     * 修改优惠券规则
     * @param id
     * @param couponRulePo
     * @return
     */
    @PutMapping("/couponRules/{id}")
    public CouponRule updateCouponRuleById(@PathVariable Integer id,@RequestBody CouponRulePo couponRulePo){
        CouponRule couponRule=new CouponRule(couponRulePo);
        couponRule.setId(id);
        return couponRuleService.updateCouponRule(couponRule);
    }

    /**
     *管理员通过id删除优惠券规则
     * @param id
     * @return
     */
    @DeleteMapping("/couponRules/{id}")
    public CouponRule deleteCouponRuleById(@PathVariable Integer id){
        boolean success=couponRuleService.deleteCouponRuleById(id);
        if(!success) {
            return null;
        }
        else {
            return couponRuleService.getCouponRuleById(id);
        }
    }

    /**
     * 管理员查看所有优惠券
     * @param page
     * @param limit
     * @return
             */
    @GetMapping("/coupons")
    public List<Coupon> getCoupons(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer limit){
             return couponService.getCoupons();
    }

    /**
     * 管理员添加优惠券
     * @param coupon
     * @return
     */
    @PostMapping("/coupons")
    public Object addCoupon(@RequestBody Coupon coupon){
        Coupon coupon1=couponService.addCoupon(coupon);
        if(coupon1==null) {
            return ResponseUtil.badArgumentValue();
        }else {
            return ResponseUtil.ok(coupon1);
        }
    }

    /**
     * 查看订单可用优惠券
     * @param cartItems
     * @return
     */
    @GetMapping("/coupons/availableCoupons")
    public List<Coupon> getAvailableCoupons(@RequestBody List<CartItem> cartItems, HttpServletRequest request){
        Integer userId = Integer.valueOf(request.getHeader("userId"));

          return couponService.listAvailableCoupons(cartItems);
    }

    /**
     * 获取团购规则列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/grouponRules")
    public List<GrouponRule> getGroupRules(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer limit){
        return null;
    }

    /**
     * 新建团购规则
     * @param grouponRulePo
     * @return
     */
    @PostMapping("/grouponRules")
    public GrouponRule addGrouponRule(@RequestBody GrouponRulePo grouponRulePo){
        GrouponRule grouponRule=new GrouponRule(grouponRulePo);
        return (GrouponRule)discountService.addPromotion(grouponRule);
    }

    /**
     * 通过id获得团购规则
     * @param id
     * @return
     */
    @GetMapping("/grouponRules/{id}")
    public GrouponRule findGroupRuleById(@PathVariable Integer id){
        return (GrouponRule)discountService.getPromotionById(id,"sda");
    }

    /**
     * 管理员修改团购规则
     * @param id
     * @param grouponRulePo
     * @return
     */
    @PutMapping("/grouponRules/{id}")
    public GrouponRule modifyGrouponRuleById(@PathVariable Integer id,@RequestBody GrouponRulePo grouponRulePo){
        GrouponRule grouponRule=new GrouponRule(grouponRulePo);
        return (GrouponRule)discountService.updatepromotionRule(grouponRule);
    }

    /**
     * 管理员通过id删除团购订单
     * @param id
     * @return
     */
    @DeleteMapping("/grouponRules/{id}")
    public Object deleteGroupRule(@PathVariable Integer id){

//        PromotionRule promotionRule=discountService.deletePromotionById(id);
////        if(promotionRule!=null) return ResponseUtil.ok();
////        else return ResponseUtil.fail();
        return null;
    }

    /*此url待定，不确定界面标准组是否实现*/
    //经协商，加上这条url，用来获取团购商品列表/

    @GetMapping("/grouponGoods")
    public List<GrouponRule> getGrouponGoods(){
        return null;
    }


}
