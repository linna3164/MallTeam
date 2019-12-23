package com.xmu.discount.web;

import com.github.pagehelper.PageHelper;
import com.google.inject.internal.cglib.core.$ClassNameReader;
import com.xmu.discount.domain.coupon.Coupon;
import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.coupon.CouponRulePo;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import com.xmu.discount.domain.discount.PresaleRule;
import com.xmu.discount.domain.discount.PromotionRule;
import com.xmu.discount.domain.others.domain.*;
import com.xmu.discount.domain.vo.GrouponRuleVo;
import com.xmu.discount.domain.vo.GrouponRuleVo;
import com.xmu.discount.domain.vo.PresaleRuleVo;
import com.xmu.discount.exception.*;
import com.xmu.discount.inter.GoodsFeign;
import com.xmu.discount.inter.LogFeign;
import com.xmu.discount.service.impl.*;
import com.xmu.discount.util.JacksonUtil;
import com.xmu.discount.util.ResponseUtil;
import org.apache.catalina.util.RequestUtil;
import org.aspectj.apache.bcel.classfile.Module;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @create 2019/12/4 8:30
 * couponRule,coupon,goupon,gouponRule一律不加s,列表才加s
 */

@RestController
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

    @Autowired
    LogFeign logFeign;
    /**
     *  管理员查看所有的优惠券规则
     * @return
     */
    @GetMapping("/admin/couponRules")
    public Object getCouponRules(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer limit, HttpServletRequest request){
        PageHelper.startPage(page,limit);
        List<CouponRule> couponRules= (List<CouponRule>) couponRuleService.listPromotionRuleOfType("couponRule");
        List<CouponRulePo> couponRulePos=new ArrayList<CouponRulePo>();
       for(CouponRule c:couponRules)
       {
           couponRulePos.add(c.getRealObj());
       }

        return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),0,"管理员查看所有优惠券",1,null),couponRulePos);
    }

    /**
     * 管理员添加优惠券规则
     * @param couponRulePo
     * @return
     */
    @PostMapping("/couponRules")
    public Object addCouponRule(@RequestBody  CouponRulePo couponRulePo,HttpServletRequest request) throws PresaleRuleAddFailException, GrouponRuleAddFailException, CouponRuleAddFailException {
        CouponRule couponRule=new CouponRule(couponRulePo);
        couponRule = (CouponRule) couponRuleService.addPromotion((PromotionRule) couponRule);
        couponRulePo=couponRule.getRealObj();
        if(couponRulePo.getId()!=null) {
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),1,"管理员添加优惠券规则",1,couponRulePo.getId()),ResponseUtil.ok(couponRulePo));
        }
        else {
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),1,"管理员添加优惠券规则",0,null),ResponseUtil.fail(712,"优惠券规则添加失败"));
        }
    }

    /**
     * 通过id查找优惠券规则
     * @param id
     * @return
     */
    @GetMapping("/couponRules/{id}")
    public Object findCouponRuleById(@PathVariable Integer id,HttpServletRequest request)  {
        CouponRule couponRule= (CouponRule) couponRuleService.getPromotionById(id,"couponRule");
        CouponRulePo couponRulePo = couponRule.getRealObj();
        if(couponRulePo!=null) {
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),0,"通过id查找优惠券规则",1,id),ResponseUtil.ok(couponRulePo));
        }
        else {
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),0,"通过id查找优惠券规则",0,id),ResponseUtil.fail(710,"该优惠券规则是无效优惠券规则"));
        }
    }

    /**
     * 修改优惠券规则
     * @param id
     * @param couponRulePo
     * @return
     */
    @PutMapping("/couponRules/{id}")
    public Object updateCouponRuleById(@PathVariable Integer id,@RequestBody CouponRulePo couponRulePo,HttpServletRequest request) throws GrouponRuleUpdateFailException, PresaleRuleUpdateFailException, CouponRuleUpdateFailException, PresaleRuleUnValidException {
        CouponRule couponRule=new CouponRule(couponRulePo);
        couponRule.setId(id);
       couponRule= (CouponRule) couponRuleService.updatepromotionRule(couponRule);
       if(couponRule==null) {return ResponseUtil.fail(710,"该优惠券是无效优惠券规则，或在活动期间");}
       couponRulePo=couponRule.getRealObj();
       if(couponRulePo==null) {
           return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),2,"管理员修改优惠券规则",0,id),ResponseUtil.fail(711,"优惠券规则修改失败"));
       }
       else {
           return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),2,"管理员修改优惠券规则",1,id),ResponseUtil.ok(couponRulePo));
       }
    }

    /**
     *管理员通过id删除优惠券规则
     * @param id
     * @return
     */
    @DeleteMapping("/couponRules/{id}")
    public Object deleteCouponRuleById(@PathVariable Integer id,HttpServletRequest request) throws CouponRuleDeleteFailException, PresaleRuleDeleteFailException, GrouponRuleDeleteFailException, GrouponRuleUpdateFailException {

        CouponRule couponRule= (CouponRule) couponRuleService.getPromotionById(id,"couponRule");
        System.out.println(couponRule);
        if(couponRule==null){
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),3,"管理员通过id删除优惠券规则",0,id),ResponseUtil.fail(710,"该优惠券规则是无效优惠券规则"));
        }
        boolean success=couponRuleService.deletePromotionById(couponRule);
        if(success) {
             return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),3,"管理员通过id删除优惠券规则",1,id),ResponseUtil.ok("优惠券规则删除成功"));
        }
        else {
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),3,"管理员通过id删除优惠券规则",0,id),ResponseUtil.fail(713,"优惠券规则删除失败"));
        }

    }

    /**
     * 用户查看部分优惠券规则
     * @return
     */
    @GetMapping("/couponRules")
    public Object userGetCouponRules(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer limit,HttpServletRequest request){
        PageHelper.startPage(page,limit);
        List<CouponRule> couponRules= (List<CouponRule>) couponRuleService.listPromotionRuleOfType("couponRule");
        List<CouponRulePo> couponRulePos=new ArrayList<CouponRulePo>();
        for(CouponRule c:couponRules) {
            couponRulePos.add(c.getRealObj());
        }
        return ResponseUtil.ok(couponRulePos);
    }

    /**
     * ******************优惠券*************
     */

    /**
     * 用户查看不同状态状态所有优惠券
     * 查看不同状态的优惠券（未使用、已使用、已失效、已过期、）
     *(0,1,2,3)
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/coupons")
    public Object getCoupons(@RequestParam(required = false) Integer type, @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer limit, HttpServletRequest request){
        Integer userId = Integer.valueOf(request.getHeader("userId"));

        PageHelper.startPage(page,limit);
        if(type==0){
            return ResponseUtil.ok(couponService.listUnUsedCouponOfUser(userId));
        }

        else if(type==1){
            return ResponseUtil.ok(couponService.listUsedCouponOfUser(userId));
        }
        else if(type==2){
            return ResponseUtil.ok(couponService.listDisabledCouponOfUser(userId));
        }
        else if(type==3){
            return ResponseUtil.ok(couponService.listOverDueCouponOfUser(userId));
        }
        else {
            return ResponseUtil.ok(null);
        }
    }

    /**
     * 管理员添加优惠券（用户领取优惠券）
     * @param couponi
     * @return
     */
    @PostMapping("/coupons")
    public Object addCoupon(@RequestBody Coupon couponi,HttpServletRequest request) throws GetCouponFailException {

        Integer couponRuleId=couponi.getCouponRuleId();
        Integer userId = Integer.valueOf(request.getHeader("userId"));
        //直接传的couponRule
        Coupon coupon = couponService.addCoupon(couponRuleId,userId);
        if (coupon == null) {
            return ResponseUtil.fail(714,"用户领取优惠券失败");
        } else {
            List<Coupon> coupons=couponService.listUnUsedCouponOfUser(userId);
            coupon=coupons.get(coupons.size()-1);
            return ResponseUtil.ok(coupon);
        }
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
    public Object getGroupRules(@RequestParam (required = false)Integer goodsId, @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer limit,HttpServletRequest request)  {
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
      return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),0,"管理员查看某个规则的团购规则列表",1,null),grouponRuleVos);

    }

    /**
     * 新建团购规则
     * @param grouponRulePo
     * @return 标准组GrouponRulePo
     */
    @PostMapping("/grouponRules")
    public Object addGrouponRule(@RequestBody GrouponRulePo grouponRulePo,HttpServletRequest request)  throws CouponRuleAddFailException, GrouponRuleAddFailException, PresaleRuleAddFailException  {
        GrouponRule grouponRule=new GrouponRule(grouponRulePo);
        grouponRule=(GrouponRule) grouponService.addPromotion(grouponRule);
        grouponRulePo=grouponRule.getRealObj();
        if(grouponRulePo!=null) {
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),1,"管理员添加团购规则",1,grouponRulePo.getId()),ResponseUtil.ok(grouponRulePo));
        } else {
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),1,"管理员添加团购规则",0,grouponRulePo.getId()),ResponseUtil.fail(722,"团购规则添加/失败"));
        }
    }

    /**
     * 通过id获得团购规则
     * @param id
     * @return 标准组GrouponRuleVo
     */
    @GetMapping("/admin/grouponRules/{id}")
    public Object findGroupRuleById(@PathVariable Integer id, HttpServletRequest request) throws PresaleRuleUnValidException {
        GrouponRule grouponRule=(GrouponRule) grouponService.getPromotionById(id,"grouponRule");
        GrouponRulePo grouponRulePo=grouponRule.getRealObj();
        GrouponRuleVo grouponRuleVo=new GrouponRuleVo();

        Object retObj = goodsFeign.getGoodsById(grouponRule.getGoodsId());
        GoodsPo goodsPo=JacksonUtil.getBack(retObj, GoodsPo.class);

        grouponRuleVo.setGoodsPo(goodsPo);
        grouponRuleVo.setGrouponRulePo(grouponRulePo);
        return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),0,"管理员通过id查看团购规则",1,id),ResponseUtil.ok(grouponRuleVo));
    }

    /**
     * 管理员修改团购规则
     * @param id
     * @param grouponRulePo
     * @return 标准组GrouponRulePo
     */
    @PutMapping("/grouponRules/{id}")
    public Object modifyGrouponRuleById(@PathVariable Integer id,@RequestBody GrouponRulePo grouponRulePo,HttpServletRequest request) throws GrouponRuleUpdateFailException, PresaleRuleUpdateFailException, CouponRuleUpdateFailException, PresaleRuleUnValidException {
        GrouponRule grouponRule=new GrouponRule(grouponRulePo);
        grouponRule=(GrouponRule) grouponService.updatepromotionRule(grouponRule);
        GrouponRuleVo grouponRuleVo=new GrouponRuleVo();

        Object retObj = goodsFeign.getGoodsById(grouponRule.getGoodsId());
        GoodsPo goodsPo=JacksonUtil.getBack(retObj, GoodsPo.class);

        grouponRuleVo.setGoodsPo(goodsPo);
        grouponRuleVo.setGrouponRulePo(grouponRule.getRealObj());
        if(grouponRuleVo==null){
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),2,"管理员修改团购规则",0,id),ResponseUtil.fail(721,"团购规则修改失败"));
        }
        else {
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),2,"管理员修改团购规则",1,id),ResponseUtil.ok(grouponRuleVo));
        }
    }

    /**
     * 管理员通过id删除团购规则
     * @param id
     * @return 无
     */
    @DeleteMapping("/grouponRules/{id}")
    public Object deleteGroupRule(@PathVariable Integer id,HttpServletRequest request) throws GrouponRuleDeleteFailException, CouponRuleDeleteFailException, PresaleRuleDeleteFailException, GrouponRuleUpdateFailException {

        PromotionRule promotionRule=grouponService.getPromotionById(id,"grouponRule");
        if(promotionRule==null) {return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),3,"管理员通过id删除团购规则",0,id),ResponseUtil.fail(723,"团购规则删除失败"));}
        else {
            grouponService.deletePromotionById(promotionRule);
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),3,"管理员通过id删除团购规则",1,id),ResponseUtil.ok(grouponService.getPromotionById(id, "grouponRule")));
        }
    }

    /**
     *经协商，加上这条url，用来获取团购商品列表/ 管理员可以看到除了删除的所有团购
     * @return 标准组List<GrouponRuleVo>
     */
    @GetMapping("/admin/grouponGoods")
    public Object getAllGrouponGoods(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit,HttpServletRequest request){
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
        return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),0,"管理员获取团购商品列表",1,null),grouponRuleVos);
    }

    /**
     * 用户查看单个团购规则
     * @return GrouponRuleVo
     */
    @GetMapping("/grouponRules/{id}")
    public Object getGrouponRulesById(@PathVariable Integer id) throws GrouponRuleUnValidException {
        GrouponRule promotionRule = (GrouponRule) grouponService.getPromotionById(id,"grouponRule");

        if(promotionRule==null){
            throw new GrouponRuleUnValidException();
        }
        GrouponRuleVo grouponRuleVo=new GrouponRuleVo();

        Object retObj = goodsFeign.getGoodsById(promotionRule.getGoodsId());
        GoodsPo goodsPo=JacksonUtil.getBack(retObj, GoodsPo.class);

        grouponRuleVo.setGoodsPo(goodsPo);

        return ResponseUtil.ok(grouponRuleVo);
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
            Object retObj = goodsFeign.getGoodsById(promotionRule.getGoodsId());
            Goods goods=JacksonUtil.getBack(retObj, Goods.class);
            grouponRuleVo.setGoodsPo(new GoodsPo(goods));
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
                                                @RequestParam (required= false)Integer goodsId,HttpServletRequest request)  {
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
        return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),0,"管理员根据条件查看商品的预售信息",1,null),ResponseUtil.ok(presaleRules));

    }

    /**
     * 管理员查看预售商品列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/admin/presaleRules")
    public Object getAllPresaleRules(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer limit,HttpServletRequest request) {
        List<? extends PromotionRule> presaleRules = presaleService.listPromotionRuleOfTypeWithGoods("presaleRule");
        PageHelper.startPage(page,limit);
        List<PresaleRuleVo> presaleRuleVos = new ArrayList<>();

        for (PromotionRule promotionRule : presaleRules) {
            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
            PresaleRule presaleRule = (PresaleRule) promotionRule;
            presaleRuleVo.setPresaleRule(new PresaleRuleSt(presaleRule));
            presaleRuleVo.setGoodsPo(presaleRule.getGoodsPo());
            presaleRuleVos.add(presaleRuleVo);
        }
        return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),0,"管理员根据条件查看商品的预售信息",1,null),ResponseUtil.ok(presaleRules));
    }

        /**
         * 管理员发布预售信息
         * @param presaleRule//body中包含startTime; adEndTime; finalStartTime; endTime; statusCode; goodsId; deposit,,finalPayment
         * @return
         * @throws
         */
    @PostMapping("/presaleRules")
    public Object addPresaleRule(@RequestBody PresaleRule presaleRule,HttpServletRequest request) throws CouponRuleAddFailException, GrouponRuleAddFailException, PresaleRuleAddFailException {
        PresaleRule presaleRule1=(PresaleRule)presaleService.addPromotion(presaleRule);
        if(presaleRule1==null) {
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),1,"管理员发布预售信息",0,null),ResponseUtil.fail(732,"预售规则添加失败"));
        } else {
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),0,"管理员发布预售信息",1,presaleRule1.getId()),ResponseUtil.ok(presaleRule1));
        }
    }

    /**
     * 管理员修改预售信息
     * @param presaleRule
     * @param id
     * @return
     * @throws
     */
    @PutMapping("/presaleRules/{id}")
    public Object updatePresaleRuleById(@RequestBody PresaleRule presaleRule,@PathVariable Integer id,HttpServletRequest request) throws GrouponRuleUpdateFailException, PresaleRuleUpdateFailException, CouponRuleUpdateFailException, PresaleRuleUnValidException {
        presaleRule.setId(id);
        PresaleRule presaleRule1=(PresaleRule) presaleService.updatepromotionRule(presaleRule);
        if(presaleRule1==null) {
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),2,"管理员修改预售信息",0,id),ResponseUtil.fail(731,"预售规则修改失败"));
        } else {
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),2,"管理员修改预售信息",1,id),ResponseUtil.ok(presaleRule1));
        }
    }

    /**
     * 查看预售信息详情
     * @param id
     * @return
     * @throws
     */
    @GetMapping("/presaleRules/{id}")
    public Object getPresaleRuleById(@PathVariable Integer id)  {

        System.out.println("into presaleRule/id");
        PresaleRule presaleRule=(PresaleRule) presaleService.getPromotionById(id,"presaleRule");
        if(presaleRule!=null) {

            PresaleRuleVo presaleRuleVo=new PresaleRuleVo();
            presaleRuleVo.setPresaleRule(new PresaleRuleSt(presaleRule));
            Object retObj = goodsFeign.getGoodsById(presaleRule.getGoodsId());
            Goods goods=JacksonUtil.getBack(retObj, Goods.class);
            if(goods==null){
                presaleRuleVo.setGoodsPo(null);
            }
            else {
                presaleRuleVo.setGoodsPo(new GoodsPo(goods));
            }
            return ResponseUtil.ok(presaleRuleVo);
        } else {
            return ResponseUtil.fail(730,"该优惠券规则无效");
        }
    }

    @GetMapping("/admin/presaleRules/{id}")
    public Object adminGetPresaleRuleById(@PathVariable Integer id)  {

        System.out.println("into presaleRule/id");
        PresaleRule presaleRule=(PresaleRule) presaleService.getPromotionById(id,"presaleRule");
        if(presaleRule!=null) {

            PresaleRuleVo presaleRuleVo=new PresaleRuleVo();
            presaleRuleVo.setPresaleRule(new PresaleRuleSt(presaleRule));
            Object retObj = goodsFeign.getGoodsById(presaleRule.getGoodsId());
            Goods goods=JacksonUtil.getBack(retObj, Goods.class);
            if(goods==null){
                presaleRuleVo.setGoodsPo(null);
            }
            else {
                presaleRuleVo.setGoodsPo(new GoodsPo(goods));
            }
            presaleRuleVo.setGoodsPo(new GoodsPo(goods));
            return ResponseUtil.ok(presaleRuleVo);
        } else {
            return ResponseUtil.fail(730,"该优惠券规则无效");
        }
    }




    /**
     * 管理员通过id删除预售信息
     * @param id
     * @return
     * @throws
     */
    @DeleteMapping("/presaleRules/{id}")
    public Object deletePresaleRuleById(@PathVariable Integer id,HttpServletRequest request) throws GrouponRuleUpdateFailException, PresaleRuleDeleteFailException, CouponRuleDeleteFailException, GrouponRuleDeleteFailException {
        PresaleRule presaleRule=(PresaleRule)presaleService.getPromotionById(id,"presaleRule");
      if(presaleRule!=null) {
          boolean success=presaleService.deletePromotionById(presaleRule);
//          presaleRule = (PresaleRule) presaleService.getPromotionById(id, "presaleRule");
          if(!success){
              return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),0,"管理员删除预售规则",0,id),ResponseUtil.fail(733,"预售规则删除失败"));

          }
          return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),0,"管理员删除预售规则",1,id),ResponseUtil.ok());
      }
      else {
          return ResponseUtil.fail(733,"预售规则删除失败");
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
        PageHelper.startPage(page,limit);
        List<? extends PromotionRule> presaleRules = presaleService.listPromotionRuleOfTypeInprocessWithGoods("presaleRule");

        List<PresaleRuleVo> presaleRuleVos = new ArrayList<>();

        for (PromotionRule promotionRule : presaleRules) {
            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
            PresaleRule presaleRule = (PresaleRule) promotionRule;
            presaleRuleVo.setPresaleRule(new PresaleRuleSt(presaleRule));
            presaleRuleVo.setGoodsPo(presaleRule.getGoodsPo());
            presaleRuleVos.add(presaleRuleVo);
        }
        return ResponseUtil.ok(presaleRules);

    }


    /**
     * 返回payment
     * @param order
     * @return
     */
    @PostMapping("/orders")
    public Object getPayment(@RequestBody  Order order) throws SubmitOrderFailException {
        Order orderRes=presaleService.getPayment(order);
        return ResponseUtil.ok(orderRes);
    }

    /**
     * 获取商品当前的团购规则
     * @param
     * @return
]     */
    @GetMapping("/goods/{id}/grouponRule")
    public Object getInProcessGrouponRuleByGoodsId(@PathVariable Integer id)  {
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
     * @param
     * @return
     */
    @GetMapping("/goods/{id}/presaleRule")
    public Object getInProcessPresaleRuleByGoodsId(@PathVariable Integer id)  {
        PromotionRule promotionRule=presaleService.listCurrentPromotionByGoodsId(id);
        if(promotionRule==null){
            return ResponseUtil.ok(null);
        }
        else if(promotionRule instanceof PresaleRule){
            return ResponseUtil.ok((PresaleRule)promotionRule);
        }
        else {
            return ResponseUtil.ok(null);
        }
    }

    /**
     * 写日志，返回正确或者错误码
     * @param log 日志
     * @param retObj 返回码
     * @return 返回码
     */
    public Object returnResult(Log log, Object retObj)
    {
        try {
            logFeign.addLog(log);
            return retObj;
        } catch (Exception e2) {
            System.out.println("写日志失败");
            return retObj;
        }
    }


    /**
     * 设置团购活动失效
     * @param id
     * @return Return  responseUtil.ok()
     */
    @PostMapping("/grouponRules/{id/}invalid")
    public Object setGrouponRuleDisable(@PathVariable Integer id,HttpServletRequest request) throws PromotionRuleSetDisableException {

        GrouponRule grouponRule=(GrouponRule) grouponService.getPromotionById(id,"grouponRule");
        if(grouponRule==null){
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),2,"管理员设置团购活动失效",0,id),ResponseUtil.fail());
        }
        else{
            grouponService.setDisabled(grouponRule);
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),2,"管理员设置团购活动失效",1,id),ResponseUtil.ok());
        }
    }


    /**
     * 设置优惠券活动失效
     * @param id
     * @return Return  responseUtil.ok()
     */
    @PostMapping("/couponRules/{id}/invalid")
    public Object setCouponRuleDisable(@PathVariable Integer id,HttpServletRequest request) throws PromotionRuleSetDisableException {
        CouponRule couponRule=(CouponRule) couponRuleService.getPromotionById(id,"couponRule");
        if(couponRule==null){
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),2,"管理员设置优惠券规则失效",0,id),ResponseUtil.fail());
        }
        else{
            grouponService.setDisabled(couponRule);
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),2,"管理员设置优惠券规则失效",1,id),ResponseUtil.ok());
        }
    }

    /**
     * 设置预售活动失效
     * @param id
     * @return Return  responseUtil.ok()
     */
    @PostMapping("/presaleRules/{id}/invalid")
    public Object setPresaleRuleDisable(@PathVariable Integer id,HttpServletRequest request) throws PromotionRuleSetDisableException {
        PresaleRule presaleRule=(PresaleRule) presaleService.getPromotionById(id,"presaleRule");
        if(presaleRule==null){
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),2,"管理员设置预售活动失效",0,id),ResponseUtil.fail());
        }
        else{
            grouponService.setDisabled(presaleRule);
            return returnResult(new Log(Integer.valueOf(request.getHeader("userId")),request.getHeader("ip"),2,"管理员设置预售活动失效",1,id),ResponseUtil.ok());
        }
    }

}
