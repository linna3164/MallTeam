package com.xmu.discount.exception;

public class CouponNotFoundException extends Exception{

    public CouponNotFoundException() {
    }

    public CouponNotFoundException(String message) {
        super(message);
    }

    public CouponNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponNotFoundException(Throwable cause) {
        super(cause);
    }

    public CouponNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}