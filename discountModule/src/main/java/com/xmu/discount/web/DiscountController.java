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
import com.xmu.discount.domain.others.domain.GoodsPo;
import com.xmu.discount.domain.others.domain.Order;
import com.xmu.discount.domain.vo.GrouponRuleVo;
import com.xmu.discount.domain.vo.GrouponRuleVo;
import com.xmu.discount.domain.vo.PresaleRuleVo;
import com.xmu.discount.exception.*;
import com.xmu.discount.inter.GoodsFeign;
import com.xmu.discount.service.impl.*;
import com.xmu.discount.util.JacksonUtil;
import com.xmu.discount.util.ResponseUtil;
import org.apache.catalina.util.RequestUtil;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
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
    private GrouponServiceImpl grouponService;

    @Autowired
    @Qualifier("presaleServiceImpl")
    private PresaleServiceImpl presaleService;

    @Autowired
    GoodsFeign goodsFeign;
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
    public Object addCouponRule(@RequestBody  CouponRulePo couponRulePo,HttpServletRequest request)  {
        CouponRule couponRule=new CouponRule(couponRulePo);
        couponRule = (CouponRule) couponRuleService.addPromotion((PromotionRule) couponRule);
        couponRulePo=couponRule.getRealObj();
        if(couponRulePo.getId()!=null) {
            return ResponseUtil.ok(couponRulePo);
        }
        else {
            return ResponseUtil.fail(712,"优惠券规则添加失败");
        }
    }

    /**
     * 通过id查找优惠券规则
     * @param id
     * @return
     */
    @GetMapping("/couponRules/{id}")
    public Object findCouponRuleById(@PathVariable Integer id)  {
        CouponRule couponRule= (CouponRule) couponRuleService.getPromotionById(id,"couponRule");
        CouponRulePo couponRulePo = couponRule.getRealObj();
        if(couponRulePo!=null) {
            return ResponseUtil.ok(couponRulePo);
        }
        else {
            return ResponseUtil.fail(710,"该优惠券规则是无效优惠券规则");
        }
    }

    /**
     * 修改优惠券规则
     * @param id
     * @param couponRulePo
     * @return
     */
    @PutMapping("/couponRules/{id}")
    public Object updateCouponRuleById(@PathVariable Integer id,@RequestBody CouponRulePo couponRulePo,HttpServletRequest request)  {
        CouponRule couponRule=new CouponRule(couponRulePo);
        couponRule.setId(id);
       couponRule= (CouponRule) couponRuleService.updatepromotionRule(couponRule);
       couponRulePo=couponRule.getRealObj();
       if(couponRulePo==null) {
           return ResponseUtil.fail(711,"优惠券规则修改失败");
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
    public Object deleteCouponRuleById(@PathVariable Integer id,HttpServletRequest request)  {
       CouponRule couponRule= (CouponRule) couponRuleService.getPromotionById(id,"couponRule");
        System.out.println(couponRule);
        couponRuleService.deletePromotionById(couponRule);
            couponRule= (CouponRule) couponRuleService.getPromotionById(id,"couponRule");
        if(couponRule.getRealObj()==null){
            return ResponseUtil.ok("删除成功");
        }
        else {
            return ResponseUtil.fail(713,"优惠券规则删除失败");
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
     * @param couponRuleId
     * @return
     */
    @PostMapping("/coupons")
    public Object addCoupon(@RequestParam Integer couponRuleId,HttpServletRequest request) throws GetCouponFailException {
//        try {
            Integer userId = Integer.valueOf(request.getHeader("userId"));
            Coupon coupon = couponService.addCoupon(couponRuleId,userId);    //直接传的coupon，参数都有
            if (coupon == null) {
                return ResponseUtil.badArgumentValue();
            } else {
                return ResponseUtil.ok(coupon);
            }
//        } catch (CouponRuleNotFoundException e) {
//            return ResponseUtil.fail(714,"优惠券领取失败");
//        }

    }



    /**
     * 查看订单可用优惠券
     * @param cartItems
     * @return
     */
    @GetMapping("/coupons/availableCoupons")
    public List<Coupon> getAvailableCoupons(@RequestBody List<CartItem> cartItems, HttpServletRequest request) {
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
    public List<GrouponRuleVo> getGroupRules(@RequestParam Integer goodsId, @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer limit)  {
        PageHelper.startPage(page,limit);
        List<GrouponRule> grouponRules= (List<GrouponRule>) grouponService.listPromotionRuleOfTypeWithGoods("grouponRule");
        List<GrouponRuleVo>  grouponRuleVos=new ArrayList<GrouponRuleVo>();
        GrouponRulePo grouponRulePos=new GrouponRulePo();
        for(GrouponRule g:grouponRules) {
        GrouponRuleVo grouponRuleVo=new GrouponRuleVo();
        grouponRuleVo.setGoodsPo(g.getGoodsPo());
        grouponRuleVo.setGrouponRulePo(g.getRealObj());
        grouponRuleVos.add(grouponRuleVo);

        }
      return grouponRuleVos;

    }

    /**
     * 新建团购规则
     * @param grouponRulePo
     * @return 标准组GrouponRulePo
     */
    @PostMapping("/grouponRules")
    public Object addGrouponRule(@RequestBody GrouponRulePo grouponRulePo) {
        GrouponRule grouponRule=new GrouponRule(grouponRulePo);
        grouponRule=(GrouponRule) grouponService.addPromotion(grouponRule);
        grouponRulePo=grouponRule.getRealObj();
        if(grouponRulePo!=null) {
            return ResponseUtil.ok(grouponRulePo);
        } else {
            return ResponseUtil.fail(722,"团购规则添加失败");
        }
    }

    /**
     * 通过id获得团购规则
     * @param id
     * @return 标准组GrouponRuleVo
     */
    @GetMapping("/admin/grouponRules/{id}")
    public Object findGroupRuleById(@PathVariable Integer id)  {
        GrouponRule grouponRule=(GrouponRule) grouponService.getPromotionById(id,"grouponRule");
        GrouponRulePo grouponRulePo=grouponRule.getRealObj();
        GrouponRuleVo grouponRuleVo=new GrouponRuleVo();

        Object retObj = goodsFeign.getGoodsById(grouponRule.getGoodsId());
        GoodsPo goodsPo=JacksonUtil.getBack(retObj, GoodsPo.class);

        grouponRuleVo.setGoodsPo(goodsPo);
        grouponRuleVo.setGrouponRulePo(grouponRulePo);
        if(grouponRuleVo!=null)
        {return ResponseUtil.ok(grouponRuleVo);}
        else {return ResponseUtil.fail(710,"该团购规则是无效团购规则");}
    }

    /**
     * 管理员修改团购规则
     * @param id
     * @param grouponRulePo
     * @return 标准组GrouponRulePo
     */
    @PutMapping("/grouponRules/{id}")
    public Object modifyGrouponRuleById(@PathVariable Integer id,@RequestBody GrouponRulePo grouponRulePo) {
        GrouponRule grouponRule=new GrouponRule(grouponRulePo);
        grouponRule=(GrouponRule) grouponService.updatepromotionRule(grouponRule);
        GrouponRuleVo grouponRuleVo=new GrouponRuleVo();

        Object retObj = goodsFeign.getGoodsById(grouponRule.getGoodsId());
        GoodsPo goodsPo=JacksonUtil.getBack(retObj, GoodsPo.class);

        grouponRuleVo.setGoodsPo(goodsPo);
        grouponRuleVo.setGrouponRulePo(grouponRule.getRealObj());
        if(grouponRuleVo==null){
            return ResponseUtil.fail(721,"团购规则修改失败");
        }
        else {
            return ResponseUtil.ok(grouponRuleVo);
        }
    }

    /**
     * 管理员通过id删除团购规则
     * @param id
     * @return 无
     */
    @DeleteMapping("/grouponRules/{id}")
    public Object deleteGroupRule(@PathVariable Integer id) throws GrouponRuleDeleteFailException, CouponRuleDeleteFailException, PresaleRuleDeleteFailException, GrouponRuleUpdateFailException {

        PromotionRule promotionRule= null;
        try {
            promotionRule = grouponService.getPromotionById(id,"grouponRule");
        } catch (PresaleRuleUnValidException e) {
            throw new GrouponRuleDeleteFailException();
        }
//        if(promotionRule==null) {return ResponseUtil.fail(723,"团购规则删除失败");}
//        else {
            grouponService.deletePromotionById(promotionRule);

        return ResponseUtil.ok();
//        }
    }

    /**
     *经协商，加上这条url，用来获取团购商品列表/ 管理员可以看到除了删除的所有团购
     * @return 标准组List<GrouponRuleVo>
     */
    @GetMapping("/admin/grouponGoods")
    public List<GrouponRuleVo> getAllGrouponGoods(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit){
        List<GrouponRule> promotionRules= (List<GrouponRule>) grouponService.listPromotionRuleOfType("grouponRule");

        List<GrouponRuleVo> grouponRuleVos=new ArrayList<GrouponRuleVo>();
        for(GrouponRule g: promotionRules)
        {
            GrouponRuleVo grouponRuleVo=new GrouponRuleVo();
            grouponRuleVo.setGrouponRulePo(g.getRealObj());

            Object retObj = goodsFeign.getGoodsById(g.getGoodsId());
            GoodsPo goodsPo=JacksonUtil.getBack(retObj, GoodsPo.class);

            grouponRuleVo.setGoodsPo(goodsPo);
            grouponRuleVos.add(grouponRuleVo);
        }
        return grouponRuleVos;
    }

    /**
     * 用户查看单个优惠券规则
     * @return GrouponRuleVo
     */
    @GetMapping("/grouponRules/{id}")
    public Object getGrouponRulesById(@PathVariable Integer id) throws GrouponRuleUnValidException {
        GrouponRule promotionRule= null;
        try {
            promotionRule = (GrouponRule) grouponService.getPromotionById(id,"grouponRule");
        } catch (PresaleRuleUnValidException e) {
            throw new GrouponRuleUnValidException();
        }
        GrouponRuleVo grouponRuleVo=new GrouponRuleVo();

        Object retObj = goodsFeign.getGoodsById(promotionRule.getGoodsId());
        GoodsPo goodsPo=JacksonUtil.getBack(retObj, GoodsPo.class);

        grouponRuleVo.setGoodsPo(goodsPo);
        if(grouponRuleVo==null) {
            return ResponseUtil.fail(720,"该团购规则是无效团购规则");
        }
        else {
            return ResponseUtil.ok(grouponRuleVo);
        }
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
        List<GrouponRuleVo> grouponRuleVos=new ArrayList<>();

        for(PromotionRule promotionRule:promotionRules){
            GrouponRuleVo grouponRuleVo=new GrouponRuleVo();
            GrouponRule grouponRule=(GrouponRule)promotionRule;
            grouponRuleVo.setGrouponRulePo(grouponRule.getRealObj());
            grouponRuleVo.setGoodsPo(grouponRule.getGoodsPo());
            grouponRuleVos.add(grouponRuleVo);
        }
        return ResponseUtil.ok(grouponRuleVos);
    }
    /*

          ***********
          **********预售********************
          * ************
          *
     */
    /**
     * 管理员根据条件查看商品的预售信息
     * @param page
     * @param limit
     * @return return List<PresaleRuleVo>
     */
    @GetMapping("/presaleRules")
    public Object getAllPresaleRules(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer limit,
                                                @RequestParam Integer goodsId)  {
        PageHelper.startPage(page,limit);
        List<? extends PromotionRule> presaleRules= presaleService.listPresaleRuleByGoodsId(goodsId);
        List<GrouponRuleVo> grouponRuleVos=new ArrayList<>();

        for(PromotionRule promotionRule:presaleRules){
            GrouponRuleVo grouponRuleVo=new GrouponRuleVo();
            GrouponRule grouponRule=(GrouponRule)promotionRule;
            grouponRuleVo.setGrouponRulePo(grouponRule.getRealObj());
            grouponRuleVo.setGoodsPo(grouponRule.getGoodsPo());
            grouponRuleVos.add(grouponRuleVo);
        }
        return ResponseUtil.ok(presaleRules);

    }

    /**
     * 管理员查看预售商品列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/admin/presaleRules")
    public Object getAllPresaleRules(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer limit) {
        List<? extends PromotionRule> presaleRules = presaleService.listPromotionRuleOfTypeWithGoods("presaleRule");

        List<PresaleRuleVo> presaleRuleVos = new ArrayList<>();

        for (PromotionRule promotionRule : presaleRules) {
            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
            PresaleRule presaleRule = (PresaleRule) promotionRule;
            presaleRuleVo.setPresaleRule(presaleRule);
            presaleRuleVo.setGoodsPo(presaleRule.getGoodsPo());
            presaleRuleVos.add(presaleRuleVo);
        }
        return ResponseUtil.ok(presaleRules);
    }

    /**
     * 管理员发布预售信息
     * @param presaleRule//body中包含startTime; adEndTime; finalStartTime; endTime; statusCode; goodsId; deposit,,finalPayment
     * @return
     */
    @PostMapping("/presaleRules")
    public Object addPresaleRule(@RequestBody PresaleRule presaleRule) throws PresaleRuleAddFailException, GrouponRuleAddFailException, CouponRuleAddFailException {
        PresaleRule presaleRule1=(PresaleRule)presaleService.addPromotion(presaleRule);

        return ResponseUtil.ok(presaleRule1);
    }

    /**
     * 管理员修改预售信息
     * @param presaleRule
     * @param id
     * @return
     */
    @PutMapping("/presaleRules/{id}")
    public Object updatePresaleRuleById(@RequestBody PresaleRule presaleRule,@PathVariable Integer id) throws PresaleRuleUnValidException, PresaleRuleUpdateFailException, CouponRuleUpdateFailException, GrouponRuleUpdateFailException {
        presaleRule.setId(id);
        PresaleRule presaleRule1=(PresaleRule) presaleService.updatepromotionRule(presaleRule);

        return ResponseUtil.ok(presaleRule1);
    }

    /**
     * 查看预售信息详情
     * @param id
     * @return
     */
    @GetMapping("/presaleRules/{id}")
    public Object getPresaleRuleById(@PathVariable Integer id) throws PresaleRuleUnValidException {
        PresaleRule presaleRule=(PresaleRule) presaleService.getPromotionById(id,"presaleRule");

        return ResponseUtil.ok(presaleRule);
    }

    /**
     * 管理员通过id删除预售信息
     * @param id
     * @return
     */
    @DeleteMapping("/presaleRules/{id}")
    public Object deletePresaleRuleById(@PathVariable Integer id) throws PresaleRuleDeleteFailException, CouponRuleDeleteFailException, GrouponRuleDeleteFailException, GrouponRuleUpdateFailException {
        PresaleRule presaleRule= null; //为什么会有name这个参数
        try {
            presaleRule = (PresaleRule)presaleService.getPromotionById(id,"presaleRule");
        } catch (PresaleRuleUnValidException e) {
            throw new PresaleRuleDeleteFailException();
        }
        if(presaleRule!=null) {
          presaleService.deletePromotionById(presaleRule);
          return ResponseUtil.ok();
        }
        else {
            throw new PresaleRuleDeleteFailException();
        }
    }

    /**
     * 用户查看预售商品列表，只能查看上架的
     * @param page
     * @param limit
     * @return List<PresaleRuleVo>
     */
    @GetMapping("/presaleGoods")
    public Object getOnsalePresaleRules(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer limit){
        List<? extends PromotionRule> presaleRules = presaleService.listPromotionRuleOfTypeInprocessWithGoods("presaleRule");

        List<GrouponRuleVo> grouponRuleVos = new ArrayList<>();

        for (PromotionRule promotionRule : presaleRules) {
            GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
            GrouponRule grouponRule = (GrouponRule) promotionRule;
            grouponRuleVo.setGrouponRulePo(grouponRule.getRealObj());
            grouponRuleVo.setGoodsPo(grouponRule.getGoodsPo());
            grouponRuleVos.add(grouponRuleVo);
        }
        return ResponseUtil.ok(presaleRules);

    }


    /**
     * 返回payment
     * @param order
     * @return
     */
    @PostMapping("/discount/orders")
    public Object getPayment(Order order){
        Order orderRes=presaleService.getPayment(order);
        return ResponseUtil.ok(orderRes);
    }

    /**
     * 获取商品当前的团购规则
     * @param id
     * @return
]     */
    @GetMapping("/goods/{id}/grouponRule")
    public Object getInProcessGrouponRuleByGoodsId(@PathVariable Integer id) {
        PromotionRule promotionRule=grouponService.listCurrentPromotionByGoodsId(id);
        if(promotionRule==null){
            return ResponseUtil.ok(null);
        }
        else if(promotionRule instanceof GrouponRule){
            return ResponseUtil.ok((GrouponRule)promotionRule);
        }
        else {
            return ResponseUtil.ok(null);
        }
    }

    /**
     * 获取商品当前的预售规则
     * @param id
     * @return
     */
    @GetMapping("/goods/{id}/presaleRule")
    public Object getInProcessPresaleRuleByGoodsId(@PathVariable Integer id){
        PromotionRule promotionRule=grouponService.listCurrentPromotionByGoodsId(id);
        if(promotionRule==null){
            return ResponseUtil.ok(null);
        }
        else if(promotionRule instanceof PresaleRule){
            return ResponseUtil.ok((GrouponRule)promotionRule);
        }
        else {
            return ResponseUtil.ok(null);
        }
    }



}
