package com.xmu.discount.exception;

public class CouponRuleAddFailException extends Exception {
    public CouponRuleAddFailException() {
    }

    public CouponRuleAddFailException(String message) {
        super(message);
    }

    public CouponRuleAddFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponRuleAddFailException(Throwable cause) {
        super(cause);
    }

    public CouponRuleAddFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
