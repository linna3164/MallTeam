package com.xmu.discount.web;

import com.github.pagehelper.PageHelper;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRulePo;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.CartItem;
import com.xmu.discount.exception.*;
import com.xmu.discount.service.impl.*;
import com.xmu.discount.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    private CouponServiceImpl couponService;

    @Autowired
    @Qualifier("couponRuleServiceImpl")
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
    @GetMapping("/admin/couponRules")
    public List<CouponRulePo> getCouponRules(@RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer limit){
        PageHelper.startPage(page,limit);
        List<CouponRule> couponRules= (List<CouponRule>) couponRuleService.listPromotionRuleOfType("couponRule");
        List<CouponRulePo> couponRulePos=new ArrayList<CouponRulePo>();
       for(CouponRule c:couponRules)
       {
           couponRulePos.add(c.getRealObj());
       }

        return couponRulePos;
    }

    /**
     * 管理员添加优惠券规则
     * @param couponRulePo
     * @return
     */
    @PostMapping("/couponRules")
    public Object addCouponRule(CouponRulePo couponRulePo,HttpServletRequest request) throws UpdatedDataFailedException, SeriousException {
        CouponRule couponRule=new CouponRule(couponRulePo);
        couponRule = (CouponRule) couponRuleService.addPromotion((PromotionRule) couponRule);
        couponRulePo=couponRule.getRealObj();
        if(couponRulePo!=null) {
            return ResponseUtil.ok(couponRulePo);
        }
        else {
            return ResponseUtil.badArgument();
        }
    }

    /**
     * 通过id查找优惠券规则
     * @param id
     * @return
     */
    @GetMapping("/couponRules/{id}")
    public Object findCouponRuleById(@PathVariable Integer id) throws PromotionNotFoundException {
        CouponRule couponRule= (CouponRule) couponRuleService.getPromotionById(id,"couponRule");
        CouponRulePo couponRulePo = couponRule.getRealObj();
        if(couponRulePo!=null) {
            return ResponseUtil.ok(couponRulePo);
        }
        else {
            return ResponseUtil.badArgumentValue();
        }
    }

    /**
     * 修改优惠券规则
     * @param id
     * @param couponRulePo
     * @return
     */
    @PutMapping("/couponRules/{id}")
    public Object updateCouponRuleById(@PathVariable Integer id,@RequestBody CouponRulePo couponRulePo,HttpServletRequest request) throws UpdatedDataFailedException, PromotionNotFoundException {
        CouponRule couponRule=new CouponRule(couponRulePo);
        couponRule.setId(id);
       couponRule= (CouponRule) couponRuleService.updatepromotionRule(couponRule);
       couponRulePo=couponRule.getRealObj();
       if(couponRulePo==null) {
           return ResponseUtil.badArgumentValue();
       }
       else {
           return ResponseUtil.ok(couponRulePo);
       }
    }

    /**
     *管理员通过id删除优惠券规则
     * @param id
     * @return
     */
    @DeleteMapping("/couponRules/{id}")
    public Object deleteCouponRuleById(@PathVariable Integer id,HttpServletRequest request) throws PromotionNotFoundException, UpdatedDataFailedException {
       CouponRule couponRule= (CouponRule) couponRuleService.getPromotionById(id,"couponRule");
        System.out.println(couponRule);
        couponRuleService.deletePromotionById(couponRule);
            couponRule= (CouponRule) couponRuleService.getPromotionById(id,"couponRule");

        CouponRulePo couponRulePo=couponRule.getRealObj();
        if(couponRulePo!=null){
            return ResponseUtil.ok(couponRulePo);
        }
        else {
            return ResponseUtil.badArgumentValue();
        }
    }

    /**
     * 用户查看部分优惠券规则
     * @return
     */
    @GetMapping("/couponRules")
    public List<CouponRulePo> userGetCouponRules(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer limit){
        PageHelper.startPage(page,limit);
        List<CouponRule> couponRules= (List<CouponRule>) couponRuleService.listPromotionRuleOfType("couponRule");
        List<CouponRulePo> couponRulePos=new ArrayList<CouponRulePo>();
        for(CouponRule c:couponRules)  couponRulePos.add(c.getRealObj());
        return couponRulePos;
    }

    /**
     * ******************优惠券*************
     */

    /**
     * 管理员查看不同状态状态所有优惠券
     * 查看不同状态的优惠券（未使用、已使用、已失效、已过期、）
     *(0,1,2,3)
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/coupons")
    public List<Coupon> getCoupons(@RequestParam Integer type,@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer limit){
        PageHelper.startPage(page,limit);
        return couponService.getCoupons(type);
    }

    /**
     * 管理员添加优惠券（用户领取优惠券）
     * @param coupon
     * @return
     */
    @PostMapping("/coupons")
    public Object addCoupon(@RequestBody Coupon coupon) throws CouponNotFoundException, UnsupportException, PromotionNotFoundException, CouponRuleNotFoundException, UpdatedDataFailedException {
        try {
            coupon = couponService.addCoupon(coupon);    //直接传的coupon，参数都有
            if (coupon == null) {
                return ResponseUtil.badArgumentValue();
            } else {
                return ResponseUtil.ok(coupon);
            }
        } catch (CouponRuleNotFoundException e) {
            return ResponseUtil.CouponRuleNotFOund();
        }

    }



    /**
     * 查看订单可用优惠券
     * @param cartItems
     * @return
     */
    @GetMapping("/coupons/availableCoupons")
    public List<Coupon> getAvailableCoupons(@RequestBody List<CartItem> cartItems, HttpServletRequest request) throws PromotionNotFoundException {
        Integer userId = Integer.valueOf(request.getHeader("userId"));
          return couponService.listAvailableCoupons(cartItems,userId);
    }

    /**
    * * ** **************团购*************
     **/

    /**
     * 获取某个商品的团购规则列表
     * @param page
     * @param limit
     * @return 标准组List<GrouponRuleVo>
     */
    @GetMapping("/grouponRules")
    public List<GrouponRulePo> getGroupRules(@RequestParam Integer goodsId, @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer limit) throws SeriousException, PromotionNotFoundException {
        PageHelper.startPage(page,limit);
        List<GrouponRule> grouponRules= (List<GrouponRule>) grouponService.listCurrentPromotionByGoodsId(goodsId);

        List<GrouponRulePo> grouponRulePos=new ArrayList<GrouponRulePo>();
        for(GrouponRule g:grouponRules) {
            grouponRulePos.add(g.getRealObj());
        }
        return grouponRulePos;
    }

    /**
     * 新建团购规则
     * @param grouponRulePo
     * @return 标准组GrouponRulePo
     */
    @PostMapping("/grouponRules")
    public GrouponRulePo addGrouponRule(@RequestBody GrouponRulePo grouponRulePo) throws UpdatedDataFailedException, SeriousException {
        GrouponRule grouponRule=new GrouponRule(grouponRulePo);
        grouponRule=(GrouponRule) grouponService.addPromotion(grouponRule);
        grouponRulePo=grouponRule.getRealObj();
        return grouponRulePo;
    }

    /**
     * 通过id获得团购规则
     * @param id
     * @return 标准组GrouponRuleVo
     */
    @GetMapping("/admin/grouponRules/{id}")
    public GrouponRulePo findGroupRuleById(@PathVariable Integer id) throws PromotionNotFoundException {
        GrouponRule grouponRule=(GrouponRule) grouponService.getPromotionById(id,"grouponRule");
        GrouponRulePo grouponRulePo=grouponRule.getRealObj();
        return grouponRulePo;
    }

    /**
     * 管理员修改团购规则
     * @param id
     * @param grouponRulePo
     * @return 标准组GrouponRulePo
     */
    @PutMapping("/grouponRules/{id}")
    public Object modifyGrouponRuleById(@PathVariable Integer id,@RequestBody GrouponRulePo grouponRulePo) throws UpdatedDataFailedException, PromotionNotFoundException {
        GrouponRule grouponRule=new GrouponRule(grouponRulePo);
        grouponRule=(GrouponRule) grouponService.updatepromotionRule(grouponRule);
        if(grouponRule.getRealObj()==null){
            return ResponseUtil.badArgumentValue();
        }
        else return grouponRule.getRealObj();
    }

    /**
     * 管理员通过id删除团购规则
     * @param id
     * @return 无
     */
    @DeleteMapping("/grouponRules/{id}")
    public Object deleteGroupRule(@PathVariable Integer id) throws PromotionNotFoundException, UpdatedDataFailedException {

        PromotionRule promotionRule=grouponService.getPromotionById(id,"grouponRule");

        grouponService.deletePromotionById(promotionRule);

        return ResponseUtil.ok(grouponService.getPromotionById(id,"grouponRule"));
    }

    /**
     *经协商，加上这条url，用来获取团购商品列表/ 管理员可以看到除了删除的所有团购
     * @return 标准组List<GrouponRuleVo>
     */
    @GetMapping("admin/grouponGoods")
    public Object getAllGrouponGoods(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit){
        List<? extends PromotionRule> promotionRules=grouponService.listPromotionRuleOfType("grouponRule");
        return promotionRules;
    }

    /**
     * 用户查看单个优惠券规则
     * @return GrouponRuleVo
     */
    @GetMapping("/grouponRules/{id}")
    public Object getGrouponRulesById(@PathVariable Integer id) throws PromotionNotFoundException {
        return grouponService.getPromotionById(id,"grouponRule");
    }


    /**
     *经协商，加上这条url，用来获取团购商品列表/ 用户可以看到没有删除且状态为上架的团购
     * @param page
     * @param limit
     * @return 标准组List<GrouponRuleVo>
     */
    @GetMapping("/grouponGoods")
    public Object getOnsaleGrouponGoods(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit){
        List<? extends PromotionRule> promotionRules=grouponService.listPromotionRuleOfType("grouponRule");
        return promotionRules;
    }
    /*

          ***********
          **********预售********************
          * ************
          *
     */
    /**
     * 管理员根据条件查看预售信息
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/presaleRules")
    public List<PresaleRule> getAllPresaleRules(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer limit,
                                                @RequestParam Integer goodsId) throws SeriousException, PromotionNotFoundException {
        return (List<PresaleRule>) presaleService.listProimotionByGoodsId(goodsId);

    }

    /**
     * 管理员查看预售商品列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("admin/presaleRules")
    public List<PresaleRule> getAllPresaleRules(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer limit){
        return (List<PresaleRule>) presaleService.listPromotionRuleOfType("presaleRule");

    }

    /**
     * 管理员发布预售信息
     * @param presaleRule//body中包含startTime; adEndTime; finalStartTime; endTime; statusCode; goodsId; deposit,,finalPayment
     * @return
     * @throws UpdatedDataFailedException
     */
    @PostMapping("/presaleRules")
    public Object addPresaleRule(@RequestBody PresaleRule presaleRule) throws UpdatedDataFailedException, SeriousException {
        PresaleRule presaleRule1=(PresaleRule)presaleService.addPromotion(presaleRule);
        if(presaleRule1==null) {
            return ResponseUtil.badArgument();
        } else {
            return ResponseUtil.ok(presaleRule1);
        }
    }

    /**
     * 管理员修改预售信息
     * @param presaleRule
     * @param id
     * @return
     * @throws UpdatedDataFailedException
     */
    @PutMapping("/presaleRules/{id}")
    public Object updatePresaleRuleById(@RequestBody PresaleRule presaleRule,@PathVariable Integer id) throws UpdatedDataFailedException, PromotionNotFoundException {
        presaleRule.setId(id);
        PresaleRule presaleRule1=(PresaleRule) presaleService.updatepromotionRule(presaleRule);
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
        PresaleRule presaleRule=(PresaleRule) presaleService.getPromotionById(id,"presaleRule");
        if(presaleRule==null) {
            return ResponseUtil.badArgumentValue();
        } else {
            return ResponseUtil.ok(presaleRule);
        }
    }

    /**
     * 管理员通过id删除预售信息
     * @param id
     * @return
     * @throws PromotionNotFoundException
     */
    @DeleteMapping("presaleRules/{id}")
    public Object deletePresaleRuleById(@PathVariable Integer id) throws PromotionNotFoundException, UpdatedDataFailedException {
        PresaleRule presaleRule=(PresaleRule)presaleService.getPromotionById(id,"presaleRule"); //为什么会有name这个参数
      if(presaleRule!=null) {
          presaleService.deletePromotionById(presaleRule);
          presaleRule = (PresaleRule) presaleService.getPromotionById(id, "presaleRule");
          return ResponseUtil.ok(presaleRule);
      }
      else {
          return ResponseUtil.badArgumentValue();
      }
    }

    /**
     * 用户查看预售商品列表，只能查看上架的
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/presaleGoods")
    public List<PresaleRule> getOnsalePresaleRules(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer limit){
  //
              return (List<PresaleRule>) presaleService.listPromotionRuleOfType("presaleRule");


    }


    /**
     * 返回payment
     * @param order
     * @return
     */
//    @PostMapping("/discount/orders")
//    public Object getPayment(Order order){
//
//    }

}
