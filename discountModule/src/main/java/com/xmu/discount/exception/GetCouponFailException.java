package com.xmu.discount.exception;

public class GetCouponFailException extends Exception {
    public GetCouponFailException() {
    }

    public GetCouponFailException(String message) {
        super(message);
    }

    public GetCouponFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetCouponFailException(Throwable cause) {
        super(cause);
    }

    public GetCouponFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
