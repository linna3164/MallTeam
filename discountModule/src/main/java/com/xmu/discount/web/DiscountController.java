package com.xmu.discount.web;

import com.xmu.discount.domain.coupon.CouponRule;
import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.service.CouponService;
import com.xmu.discount.service.impl.PromotionServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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
    private PromotionServiceImpl promotionService;

    @Autowired
    @Qualifier("grouponServiceImpl")
    private  PromotionServiceImpl getGroupon;



    @Autowired
    private CouponService couponService;
    /**
     *管理员根据条件查找优惠券/adminList
     * @return List<couponRule>
     */
    @GetMapping("/couponRules")
    public Object adminList(String name, Short type, Short status,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
//                       @Sort @RequestParam(defaultValue = "add_time") String sort,
//                       @Order @RequestParam(defaultValue = "desc") String order);
                            @RequestParam(defaultValue = "add_time") String sort,
                            @RequestParam(defaultValue = "desc") String order){
        return null;
    };

    /**
     *查找某种优惠券被某个用户的领取情况/listuser
     *改动:修改参数名称(id->couponRuleId)
     */
    @GetMapping("/couponRule/{id}/user/{id}/coupon")
    public Object listuser(@Param("userId")Integer userId, @Param("couponRuleId")Integer couponRuleId, Short status,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
//                         @Sort @RequestParam(defaultValue = "add_time") String sort,
//                         @Order @RequestParam(defaultValue = "desc") String order);
                           @RequestParam(defaultValue = "add_time") String sort,
                           @RequestParam(defaultValue = "desc") String order){
        return null;
    };

    /**
     *添加一种优惠券规则/create
     */
    @PostMapping("/couponRules")
    public Object create(@RequestBody CouponRule couponRule){
        return null;
    };

    /**
     *查看一种优惠券规则/read
     */
    @GetMapping("/couponRules/{id}")
    public Object read(@PathVariable Integer couponRuleId){
        return couponService.findCouponById(couponRuleId);
    };

    /**
     *修改优惠券规则信息/update
     */
    @PutMapping("/couponRules/{id}")
    public Object update(@PathVariable Integer couponRuleId, @RequestBody CouponRule couponRule){
        return couponService.updateCouponRule(couponRule);
    };

    /**
     *删除一种优惠券规则/delete
     */
    @DeleteMapping("/couponRules/{id}")
    public Object delete(@PathVariable Integer couponRuleId, @RequestBody CouponRule couponRule){
        return couponService.deleteCouponRuleById(couponRuleId);
    };


    /**
     * 查看优惠券列表/couponRuleList
     *
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("/couponRules")
    public Object couponRuleList(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit,
//                     @Sort @RequestParam(defaultValue = "add_time") String sort,
//                     @Order @RequestParam(defaultValue = "desc") String order);
                                 @RequestParam(defaultValue = "add_time") String sort,
                                 @RequestParam(defaultValue = "desc") String order){
        return null;
    };

    /**
     * 查看自己的优惠券 /mylist
     *
     * @param userId
     * @param status
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("/couponRules/{id}")
    public Object mylist( Integer userId,
                         Short status,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer limit,
//                     @Sort @RequestParam(defaultValue = "add_time") String sort,
//                     @Order @RequestParam(defaultValue = "desc") String order);
                         @RequestParam(defaultValue = "add_time") String sort,
                         @RequestParam(defaultValue = "desc") String order){
        return null;
    };


    /**
     * 用户查看当前购物车下单商品订单可用优惠券/selectlist
     *
     * @param userId
     * @param cartItemIds
     * @return
     */
    @GetMapping("/couponRules/availableCoupons")
    public Object selectlist(@LoginUser Integer userId, List<Integer> cartItemIds){
        return null;
    };


    /**
     * 用户领取一种优惠券 /receive
     *
     * @param userId 用户ID
     * @param body 请求内容， { couponId: xxx }
     * @return 操作结果
     */
    @PostMapping("/couponRules")
    public Object receive(@LoginUser Integer userId, @PathVariable Integer couponRuleId)
    {
        return couponService.addCoupon(couponRuleId);
    };




    /**
     * 获取团购规则列表/list
     *
     * @param page 分页页数
     * @param limit 分页大小
     * @return 团购规则列表 List<GrouponRule>
     */
    @GetMapping("/goods/{id}/grouponRules")
    public Object grouponList(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer limit,
//                     @Sort @RequestParam(defaultValue = "add_time") String sort,
//                     @Order @RequestParam(defaultValue = "desc") String order),
//                              @RequestParam(defaultValue = "add_time") String sort,
//                              @RequestParam(defaultValue = "desc") String order,
                              @PathVariable Integer grouponRuleId){
        return discountService.getGrouponRules();
    };



    /**
     *修改团购规则信息/update
     * @return GrouponRule
     */
    @PutMapping("/grouponRules/{id}")
    public Object update(@RequestBody GrouponRule grouponRule,
                         @PathVariable Integer id)
    {
        return discountService.updateGrouponRuleById(grouponRule);
    };

    /**
     *创建一个新的团购规则/create
     *@return GrouponRule
     */
    @PostMapping("/grouponRules")
    public Object create(@RequestBody GrouponRule grouponRule){
            return  discountService.addGrouponRule(grouponRule);
    };

    /**
     *删除一个团购规则/delete
     * @return 无
     */
    @DeleteMapping("/grouponRules/{id}")
    public Object delete(@NotNull @PathVariable Integer grouponRuleId){
        return
    };

    /**
     * 获取团购规则列表详细信息/listRecord
     * @param grouponRuleId 团购活动规则ID
     * @return 团购活动详情 GrouponRule
     */
    @GetMapping("/grouponRules/{id}")
    public Object detail(@PathVariable Integer grouponRuleId);

//
//    @GetMapping("/gouponRules")
//    public Object groupGoodsList(@RequestParam(defaultValue = "1") Integer page,
//                                 @RequestParam(defaultValue = "10") Integer limit,
//                     @Sort @RequestParam(defaultValue = "add_time") String sort,
//                     @Order @RequestParam(defaultValue = "desc") String order);
//                                 @RequestParam(defaultValue = "add_time") String sort,
//                                 @RequestParam(defaultValue = "desc") String order);



    /**
     * 查看用户开团或入团情况/join
     *
     * @param userId 用户ID
     * @param showType 显示类型，如果是0，则是当前用户开的团购；否则，则是当前用户参加的团购
     * @return 用户开团或入团情况
     */
    @GetMapping("/grouponRules/{id}/joinInformation")
    public Object my(@LoginUser Integer userId, @RequestParam(defaultValue = "0") Integer showType);


    /**
     *内部接口:Order模块调用Discount模块，计算使用优惠券后的价格/calcDiscount
     */
    @GetMapping("/coupon/calcDiscount")
    public Object calcDiscount(List<Integer> cartIds, Integer couponId);

}
