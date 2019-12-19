package com.xmu.discount.exception;

public class CouponRuleUpdateFailException extends Exception {
    public CouponRuleUpdateFailException() {
    }

    public CouponRuleUpdateFailException(String message) {
        super(message);
    }

    public CouponRuleUpdateFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponRuleUpdateFailException(Throwable cause) {
        super(cause);
    }

    public CouponRuleUpdateFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
