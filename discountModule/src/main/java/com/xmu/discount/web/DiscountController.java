package com.xmu.discount.web;

import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRulePo;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.CartItem;
import com.xmu.discount.exception.*;
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
import javax.ws.rs.PathParam;
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
    public Object addCouponRule(CouponRulePo couponRulePo,HttpServletRequest request) throws UpdatedDataFailedException, SeriousException {
        CouponRule couponRule=new CouponRule(couponRulePo);
        return couponRuleService.addPromotion((PromotionRule) couponRule);
    }

    /**
     * 通过id查找优惠券规则
     * @param id
     * @return
     */
    @GetMapping("/couponRules/{id}")
    public Object findCouponRuleById(@PathVariable Integer id) throws PromotionNotFoundException {
        return couponRuleService.getPromotionById(id,"CouponRule");
    }

    /**
     * 修改优惠券规则
     * @param id
     * @param couponRulePo
     * @return
     */
    @PutMapping("/couponRules/{id}")
    public Object updateCouponRuleById(@PathVariable Integer id,@RequestBody CouponRulePo couponRulePo,HttpServletRequest request) throws UpdatedDataFailedException {
        CouponRule couponRule=new CouponRule(couponRulePo);
        couponRule.setId(id);
        return couponRuleService.updatepromotionRule(couponRule);
    }

    /**
     *管理员通过id删除优惠券规则
     * @param id
     * @return
     */
    @DeleteMapping("/couponRules/{id}")
    public Object deleteCouponRuleById(@PathVariable Integer id,HttpServletRequest request) throws PromotionNotFoundException, UpdatedDataFailedException {
        PromotionRule promotionRule=couponRuleService.getPromotionById(id,"CouponRule");
        couponRuleService.deletePromotionById(promotionRule);

            return couponRuleService.getPromotionById(id,"CouponRule");

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
     * @param couponRule
     * @return
     */
//    @PostMapping("/coupons")
//    public Object addCoupon(@RequestBody CouponRule couponRule) throws CouponNotFoundException, UnsupportException {
//        Coupon coupon1=couponService.addCoupon(couponRule,1);
//        if(coupon1==null) {
//            return ResponseUtil.badArgumentValue();
//        }else {
//            return ResponseUtil.ok(coupon1);
//        }
//    }

    /**
     * 查看已使用的优惠券
     * @return
     */
    @GetMapping("/alreadyUsedCoupons")
    public List<Coupon> getUsedCoupons(){
        return null;
    }

    /**
     * 查看未使用的优惠券
     * @return
     */
    @GetMapping("/notUsedCoupons")
    public List<Coupon> getNotUsedCoupons(){
        return null;
    }

    /**
     * 查看已过期的优惠券
     * @return
     */
    @GetMapping("/expiredCoupons")
    public List<Coupon> getExpiredCoupons(){
        return null;
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

    /*
     **************团购*************
     */
    /**
     * 获取团某个商品的购规则列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/grouponRules")
    public List<GrouponRule> getGroupRules(@RequestParam Integer goodsId, @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer limit){
        return null;
    }

    /**
     * 新建团购规则
     * @param grouponRulePo
     * @return
     */
    @PostMapping("/grouponRules")
    public GrouponRule addGrouponRule(@RequestBody GrouponRulePo grouponRulePo) throws UpdatedDataFailedException, SeriousException {
        GrouponRule grouponRule=new GrouponRule(grouponRulePo);
        return (GrouponRule)discountService.addPromotion(grouponRule);
    }

    /**
     * 通过id获得团购规则
     * @param id
     * @return
     */
    @GetMapping("/grouponRules/{id}")
    public GrouponRule findGroupRuleById(@PathVariable Integer id) throws PromotionNotFoundException {
        return (GrouponRule)discountService.getPromotionById(id,"sda");
    }

    /**
     * 管理员修改团购规则
     * @param id
     * @param grouponRulePo
     * @return
     */
    @PutMapping("/grouponRules/{id}")
    public GrouponRule modifyGrouponRuleById(@PathVariable Integer id,@RequestBody GrouponRulePo grouponRulePo) throws UpdatedDataFailedException {
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


    //经协商，加上这条url，用来获取团购商品列表/ 管理员可以看到除了删除的所有团购
    @GetMapping("admin/grouponGoods")
    public List<GrouponRule> getAllGrouponGoods(){
        return null;
    }

    //经协商，加上这条url，用来获取团购商品列表/ 用户可以看到没有删除且状态为上架的团购
    @GetMapping("/grouponGoods")
    public List<GrouponRule> getOnsaleGrouponGoods(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit){
        return null;
    }
    /*
          **********预售********************
     */
    /**
     * 管理员根据条件查看预售信息
     * @param page
     * @param limit
     * @return
     */
//    @GetMapping("/presaleRules")
//    public List<PresaleRule> getAllPresaleRules(@RequestParam(defaultValue = "1") Integer page,
//                                                @RequestParam(defaultValue = "10") Integer limit){
//        return presaleService.listCurrentPromotionByGoodsId();
//
//    }

    /**
     * 管理员查看预售商品列表
     * @param page
     * @param limit
     * @return
     */
//    @GetMapping("admin/presaleRules")
//    public List<PresaleRule> getAllPresaleRules(@RequestParam(defaultValue = "1") Integer page,
//                                                @RequestParam(defaultValue = "10") Integer limit){
//        return presaleService.listAllPresaleRules();
//
//    }

    /**
     * 管理员发布预售信息
     * @param presaleRule
     * @return
     * @throws UpdatedDataFailedException
     */
    @PostMapping("/presaleRules")
    public Object addPresaleRule(@RequestBody PresaleRule presaleRule) throws UpdatedDataFailedException, SeriousException {
        PresaleRule presaleRule1=(PresaleRule)presaleService.addPromotion((PromotionRule) presaleRule);
        if(presaleRule1==null) return ResponseUtil.badArgument();
        else return ResponseUtil.ok(presaleRule1);
    }

    /**
     * 管理员修改预售信息
     * @param presaleRule
     * @param id
     * @return
     * @throws UpdatedDataFailedException
     */
    @PutMapping("/presaleRules/{id}")
    public Object updatePresaleRuleById(@RequestBody PresaleRule presaleRule,@PathVariable Integer id) throws UpdatedDataFailedException {
        presaleRule.setId(id);
        PresaleRule presaleRule1=(PresaleRule) presaleService.updatepromotionRule((PromotionRule) presaleRule);
        if(presaleRule1==null) {
            return ResponseUtil.badArgument();
        } else {
            return ResponseUtil.ok(presaleRule1);
        }
    }

    /**
     * 查看预售信息详情
     * @param id
     * @return
     * @throws PromotionNotFoundException
     */
    @GetMapping("/presaleRules/{id}")
    public Object getPresaleRuleById(@PathVariable Integer id) throws PromotionNotFoundException {
        PresaleRule presaleRule=(PresaleRule) presaleService.getPromotionById(id,"s");
        if(presaleRule==null) return ResponseUtil.badArgumentValue();
        else return ResponseUtil.ok(presaleRule);
    }

    /**
     * 管理员通过id删除预售信息
     * @param id
     * @return
     * @throws PromotionNotFoundException
     */
    @DeleteMapping("presaleRules/{id}")
    public Object deletePresaleRuleById(@PathVariable Integer id) throws PromotionNotFoundException, UpdatedDataFailedException {
        PresaleRule presaleRule=(PresaleRule)presaleService.getPromotionById(id,"ssss"); //为什么会有name这个参数
      if(presaleRule!=null) {
          presaleService.deletePromotionById(presaleRule);
          presaleRule = (PresaleRule) presaleService.getPromotionById(id, "ssss");
          return ResponseUtil.ok(presaleRule);
      }
      else return ResponseUtil.badArgumentValue();
    }

//    /**
//     * 用户查看预售商品列表，只能查看上架的
//     * @param page
//     * @param limit
//     * @return
//     */
//    @GetMapping("/presaleRules")
//    public List<PresaleRule> getOnsalePresaleRules(@RequestParam(defaultValue = "1") Integer page,
//                                                @RequestParam(defaultValue = "10") Integer limit){
//  //
//              return presaleService.listOnsalePresaleRules();
//
//
//    }


}
