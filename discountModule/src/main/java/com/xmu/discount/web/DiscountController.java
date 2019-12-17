//package com.xmu.discount.web;
//
//import com.sun.org.apache.xml.internal.security.algorithms.implementations.IntegrityHmac;
//import com.xmu.discount.domain.coupon.Coupon;
//import com.xmu.discount.domain.coupon.CouponRule;
//import com.xmu.discount.domain.coupon.CouponRulePo;
//import com.xmu.discount.domain.discount.GrouponRule;
//import com.xmu.discount.domain.discount.GrouponRulePo;
//import com.xmu.discount.domain.discount.PresaleRule;
//import com.xmu.discount.domain.discount.PromotionRule;
//import com.xmu.discount.domain.others.domain.CartItem;
//import com.xmu.discount.exception.*;
//import com.xmu.discount.service.impl.*;
//import com.xmu.discount.util.ResponseUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Author zhc
// * @create 2019/12/4 8:30
// * couponRule,coupon,goupon,gouponRule一律不加s,列表才加s
// */
//
//@RestController
//@RequestMapping("/discount")
//
//public class DiscountController {
//
//    @Autowired
//    @Qualifier("promotionServiceImpl")
//    private PromotionServiceImpl promotionService;
//
//
//    @Autowired
//    private CouponServiceImpl couponService;
//
//    @Autowired
//    private CouponRuleServiceImpl couponRuleService;
//
//    @Autowired
//    @Qualifier("grouponServiceImpl")
//    private PromotionServiceImpl grouponService;
//
//    @Autowired
//    @Qualifier("presaleServiceImpl")
//    private PromotionServiceImpl presaleService;
//
//
//    /**
//     *  管理员查看所有的优惠券规则
//     * @return
//     */
//    @GetMapping("/admin/couponRules")
//    public Object getCouponRules(@RequestParam(defaultValue = "1") Integer page,
//                                           @RequestParam(defaultValue = "10") Integer limit){
//        return promotionService.listPromotionRuleOfType("CouponRule");
//    }
//
//    /**
//     * 管理员添加优惠券规则
//     * @param couponRulePo
//     * @return
//     */
//    @PostMapping("/couponRules")
//    public Object addCouponRule(CouponRulePo couponRulePo,HttpServletRequest request) throws UpdatedDataFailedException, SeriousException {
//        CouponRule couponRule=new CouponRule(couponRulePo);
//        return couponRuleService.addPromotion((PromotionRule) couponRule);
//    }
//
//    /**
//     * 通过id查找优惠券规则
//     * @param id
//     * @return
//     */
//    @GetMapping("/couponRules/{id}")
//    public Object findCouponRuleById(@PathVariable Integer id) throws PromotionNotFoundException {
//        return couponRuleService.getPromotionById(id,"CouponRule");
//    }
//
//    /**
//     * 修改优惠券规则
//     * @param id
//     * @param couponRulePo
//     * @return
//     */
//    @PutMapping("/couponRules/{id}")
//    public Object updateCouponRuleById(@PathVariable Integer id,@RequestBody CouponRulePo couponRulePo,HttpServletRequest request) throws UpdatedDataFailedException {
//        CouponRule couponRule=new CouponRule(couponRulePo);
//        couponRule.setId(id);
//        return couponRuleService.updatepromotionRule(couponRule);
//    }
//
//    /**
//     *管理员通过id删除优惠券规则
//     * @param id
//     * @return
//     */
//    @DeleteMapping("/couponRules/{id}")
//    public Object deleteCouponRuleById(@PathVariable Integer id,HttpServletRequest request) throws PromotionNotFoundException, UpdatedDataFailedException {
//        PromotionRule promotionRule=couponRuleService.getPromotionById(id,"CouponRule");
//        couponRuleService.deletePromotionById(promotionRule);
//
//            return couponRuleService.getPromotionById(id,"CouponRule");
//    }
//
//    /**
//     * 用户查看部分优惠券规则
//     * @return
//     */
//    @GetMapping("/couponRules")
//    public List<Object> userGetCouponRules(@RequestParam(defaultValue = "1") Integer page,
//                                           @RequestParam(defaultValue = "10") Integer limit){
//        return  null;
//    }
//
//    /**
//     * 管理员查看不同状态状态所有优惠券
//     * 查看不同状态的优惠券（未使用、已过期、已使用）
//     * @param page
//     * @param limit
//     * @return
//     */
//    @GetMapping("/notUsedCoupons/{showType}")
//    public List<Coupon> getCoupons(@RequestParam(defaultValue = "1") Integer page,
//                                   @RequestParam(defaultValue = "10") Integer limit){
//        return couponService.getCoupons();
//    }
//
//    /**
//     * 管理员添加优惠券（用户领取优惠券）
//     * @param coupon
//     * @return
//     */
//    @PostMapping("/coupons")
//    public Object addCoupon(@RequestBody Coupon coupon) throws CouponNotFoundException, UnsupportException, PromotionNotFoundException, CouponRuleNotFoundException, UpdatedDataFailedException {
//        Integer id=coupon.getUserId();
//        CouponRule couponRule= (CouponRule) couponRuleService.getPromotionById(coupon.getCouponRuleId(),"CouponRule");
//        Coupon coupon1=couponService.addCoupon(couponRule,id);    //直接传的coupon，参数都有
//        if(coupon1==null) {
//            return ResponseUtil.badArgumentValue();
//        }else {
//            return ResponseUtil.ok(coupon1);
//        }
//    }
//
//
//    /**
//     * 查看订单可用优惠券
//     * @param cartItems
//     * @return
//     */
//    @GetMapping("/coupons/availableCoupons")
//    public List<Coupon> getAvailableCoupons(@RequestBody List<CartItem> cartItems, HttpServletRequest request) throws PromotionNotFoundException {
//        Integer userId = Integer.valueOf(request.getHeader("userId"));
//
//          return couponService.listAvailableCoupons(cartItems,userId);
//    }
//
//    /*
//     **************团购*************
//     */
//
//    /**
//     * 获取某个商品的团购规则列表
//     * @param page
//     * @param limit
//     * @return 标准组List<GrouponRuleVo>，我们暂时是List<GrouponRule>！！！
//     */
//    @GetMapping("/grouponRules")
//    public Object getGroupRules(@RequestParam Integer goodsId, @RequestParam(defaultValue = "1") Integer page,
//                                           @RequestParam(defaultValue = "10") Integer limit) throws SeriousException, PromotionNotFoundException {
//        return promotionService.listCurrentPromotionByGoodsId(goodsId);
//    }
//
//    /**
//     * 新建团购规则
//     * @param grouponRulePo
//     * @return 标准组GrouponRulePo，我们暂时是grouponRule!!!
//     */
//    @PostMapping("/grouponRules")
//    public Object addGrouponRule(@RequestBody GrouponRulePo grouponRulePo) throws UpdatedDataFailedException, SeriousException {
//        GrouponRule grouponRule=new GrouponRule(grouponRulePo);
//        return (GrouponRule) promotionService.addPromotion(grouponRule);
//    }
//
//    /**
//     * 通过id获得团购规则
//     * @param id
//     * @return 标准组GrouponRuleVo，我们暂时是GrouponRule!!!
//     */
//    @GetMapping("/admin/grouponRules/{id}")
//    public Object findGroupRuleById(@PathVariable Integer id) throws PromotionNotFoundException {
//        return (GrouponRule) promotionService.getPromotionById(id,"CouponRule");
//    }
//
//    /**
//     * 管理员修改团购规则
//     * @param id
//     * @param grouponRulePo
//     * @return 标准组GrouponRulePo，我们暂时是GrouponRule!!!
//     */
//    @PutMapping("/grouponRules/{id}")
//    public Object modifyGrouponRuleById(@PathVariable Integer id,@RequestBody GrouponRulePo grouponRulePo) throws UpdatedDataFailedException {
//        GrouponRule grouponRule=new GrouponRule(grouponRulePo);
//        return (GrouponRule) promotionService.updatepromotionRule(grouponRule);
//    }
//
//    /**
//     * 管理员通过id删除团购规则
//     * @param id
//     * @return 无
//     */
//    @DeleteMapping("/grouponRules/{id}")
//    public Object deleteGroupRule(@PathVariable Integer id) throws PromotionNotFoundException, UpdatedDataFailedException {
//
//        PromotionRule promotionRule=promotionService.getPromotionById(id,"GrouponRule");
//
//        promotionService.deletePromotionById(promotionRule);
//
//        return ResponseUtil.ok();
//    }
//
//    /**
//     *经协商，加上这条url，用来获取团购商品列表/ 管理员可以看到除了删除的所有团购
//     * @return 标准组List<GrouponRuleVo>
//     */
//    @GetMapping("admin/grouponGoods")
//    public Object getAllGrouponGoods(){
//        List<? extends PromotionRule> promotionRules=promotionService.listPromotionRuleOfType("GrouponRule");
//        return promotionRules;
//    }
//
//
//    /**
//     *经协商，加上这条url，用来获取团购商品列表/ 用户可以看到没有删除且状态为上架的团购
//     * @param page
//     * @param limit
//     * @return 标准组List<GrouponRuleVo>
//     */
//    @GetMapping("/grouponGoods")
//    public Object getOnsaleGrouponGoods(@RequestParam(defaultValue = "1") Integer page,
//            @RequestParam(defaultValue = "10") Integer limit){
//        List<? extends PromotionRule> promotionRules=promotionService.listPromotionRuleOfType("GrouponRule");
//        return promotionRules;
//    }
//    /*
//          **********预售********************
//     */
//    /**
//     * 管理员根据条件查看预售信息
//     * @param page
//     * @param limit
//     * @return
//     */
//    @GetMapping("/presaleRules")
//    public List<PresaleRule> getAllPresaleRules(@RequestParam(defaultValue = "1") Integer page,
//                                                @RequestParam(defaultValue = "10") Integer limit,
//                                                @RequestParam Integer goodsId) throws SeriousException, PromotionNotFoundException {
//        return (List<PresaleRule>) presaleService.listProimotionByGoodsId(goodsId);
//
//    }
//
//    /**
//     * 管理员查看预售商品列表
//     * @param page
//     * @param limit
//     * @return
//     */
//    @GetMapping("admin/presaleRules")
//    public List<PresaleRule> getAllPresaleRules(@RequestParam(defaultValue = "1") Integer page,
//                                                @RequestParam(defaultValue = "10") Integer limit){
//        return (List<PresaleRule>) presaleService.listPromotionRuleOfType("PresaleRule");
//
//    }
//
//    /**
//     * 管理员发布预售信息
//     * @param presaleRule//body中包含startTime; adEndTime; finalStartTime; endTime; statusCode; goodsId; deposit,,finalPayment
//     * @return
//     * @throws UpdatedDataFailedException
//     */
//    @PostMapping("/presaleRules")
//    public Object addPresaleRule(@RequestBody PresaleRule presaleRule) throws UpdatedDataFailedException, SeriousException {
//        PresaleRule presaleRule1=(PresaleRule)presaleService.addPromotion(presaleRule);
//        if(presaleRule1==null) {
//            return ResponseUtil.badArgument();
//        } else {
//            return ResponseUtil.ok(presaleRule1);
//        }
//    }
//
//    /**
//     * 管理员修改预售信息
//     * @param presaleRule
//     * @param id
//     * @return
//     * @throws UpdatedDataFailedException
//     */
//    @PutMapping("/presaleRules/{id}")
//    public Object updatePresaleRuleById(@RequestBody PresaleRule presaleRule,@PathVariable Integer id) throws UpdatedDataFailedException {
//        presaleRule.setId(id);
//        PresaleRule presaleRule1=(PresaleRule) promotionService.updatepromotionRule(presaleRule);
//        if(presaleRule1==null) {
//            return ResponseUtil.badArgument();
//        } else {
//            return ResponseUtil.ok(presaleRule1);
//        }
//    }
//
//    /**
//     * 查看预售信息详情
//     * @param id
//     * @return
//     * @throws PromotionNotFoundException
//     */
//    @GetMapping("/presaleRules/{id}")
//    public Object getPresaleRuleById(@PathVariable Integer id) throws PromotionNotFoundException {
//        PresaleRule presaleRule=(PresaleRule) promotionService.getPromotionById(id,"PresaleRule");
//        if(presaleRule==null) {
//            return ResponseUtil.badArgumentValue();
//        } else {
//            return ResponseUtil.ok(presaleRule);
//        }
//    }
//
//    /**
//     * 管理员通过id删除预售信息
//     * @param id
//     * @return
//     * @throws PromotionNotFoundException
//     */
//    @DeleteMapping("presaleRules/{id}")
//    public Object deletePresaleRuleById(@PathVariable Integer id) throws PromotionNotFoundException, UpdatedDataFailedException {
//        PresaleRule presaleRule=(PresaleRule)promotionService.getPromotionById(id,"PresaleRule"); //为什么会有name这个参数
//      if(presaleRule!=null) {
//          presaleService.deletePromotionById(presaleRule);
//          presaleRule = (PresaleRule) promotionService.getPromotionById(id, "PresaleRule");
//          return ResponseUtil.ok(presaleRule);
//      }
//      else {
//          return ResponseUtil.badArgumentValue();
//      }
//    }
//
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
//              return (List<PresaleRule>) presaleService.listPromotionRuleOfType("PresaleRule");
//
//
//    }
//
//
//    /**
//     * 返回payment
//     * @param order
//     * @return
//     */
////    @PostMapping("/discount/orders")
////    public Object getPayment(Order order){
////
////    }
//
//}
