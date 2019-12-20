package com.xmu.discount.exception;

/**
 * @author lenovo
 */
public class CouponRuleUnValidException extends Exception {
    public CouponRuleUnValidException() {
    }

    public CouponRuleUnValidException(String message) {
        super(message);
    }

    public CouponRuleUnValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponRuleUnValidException(Throwable cause) {
        super(cause);
    }

    public CouponRuleUnValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
