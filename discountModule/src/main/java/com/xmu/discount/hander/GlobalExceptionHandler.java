package com.xmu.discount.hander;

import com.xmu.discount.exception.*;
import com.xmu.discount.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ln
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {



    /****************************************************
     * 生成代码
     ****************************************************/

    @ExceptionHandler(value = PresaleRuleAddFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object presaleRuleAddException()
    {
        return ResponseUtil.fail(732,"预售规则添加失败");
    }

    @ExceptionHandler(value = PresaleRuleDeleteFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object presaleRuleDeleteFailException ()
    {
        return ResponseUtil.fail(733,"预售规则删除失败");
    }

    @ExceptionHandler(value = PresaleRuleUpdateFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object presaleRuleUpdateFailException ()
    {
        return ResponseUtil.fail(731,"预售规则修改失败");
    }

    @ExceptionHandler(value = PresaleRuleUnValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object presaleRuleUnValidException ()
    {
        return ResponseUtil.fail(730,"该预售规则是无效团购规则");
    }

    /****************************************************
     * 生成代码
     ****************************************************/

    @ExceptionHandler(value = GrouponRuleAddFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object grouponRuleAddFailException()
    {
        return ResponseUtil.fail(722,"团购规则添加失败");
    }

    @ExceptionHandler(value = GrouponRuleDeleteFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object grouponRuleDeleteFailException ()
    {
        return ResponseUtil.fail(723,"团购规则删除失败");
    }

    @ExceptionHandler(value = GrouponRuleUpdateFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object grouponRuleUpdateFailException ()
    {
        return ResponseUtil.fail(721,"团购规则修改失败");
    }

    @ExceptionHandler(value = GrouponRuleUnValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object grouponRuleUnValidException ()
    {
        return ResponseUtil.fail(720,"该团购规则是无效团购规则");
    }


    /****************************************************
     * 生成代码
     ****************************************************/

    @ExceptionHandler(value = CouponRuleAddFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object couponRuleAddFailException()
    {
        return ResponseUtil.fail(712,"优惠券规则添加失败");
    }

    @ExceptionHandler(value = CouponRuleDeleteFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object couponRuleDeleteFailException ()
    {
        return ResponseUtil.fail(713,"优惠券规则删除失败");
    }

    @ExceptionHandler(value = CouponRuleUpdateFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object couponRuleUpdateFailException ()
    {
        return ResponseUtil.fail(711,"优惠券规则修改失败");
    }

    @ExceptionHandler(value = CouponRuleUnValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object couponRuleUnValidException ()
    {
        return ResponseUtil.fail(710,"该优惠券规则是无效优惠券规则");
    }

    @ExceptionHandler(value = SubmitOrderFailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object submitOrderFailException ()
    {
        return ResponseUtil.fail(710,"提交订单计算优惠金额时发生错误");
    }
}

