package com.xmu.discount.exception;

/**
 * @author lenovo
 */
public class CouponSetDisableFailException extends Exception {
    public CouponSetDisableFailException() {
    }

    public CouponSetDisableFailException(String message) {
        super(message);
    }

    public CouponSetDisableFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponSetDisableFailException(Throwable cause) {
        super(cause);
    }

    public CouponSetDisableFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
