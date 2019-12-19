package com.xmu.discount.exception;

public class CouponRuleDeleteFailException extends Exception {
    public CouponRuleDeleteFailException() {
    }

    public CouponRuleDeleteFailException(String message) {
        super(message);
    }

    public CouponRuleDeleteFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponRuleDeleteFailException(Throwable cause) {
        super(cause);
    }

    public CouponRuleDeleteFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
