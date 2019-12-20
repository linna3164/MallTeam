package com.xmu.discount.exception;

/**
 * @author lenovo
 */
public class SubmitOrderFailException extends Exception {
    public SubmitOrderFailException() {
    }

    public SubmitOrderFailException(String message) {
        super(message);
    }

    public SubmitOrderFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubmitOrderFailException(Throwable cause) {
        super(cause);
    }

    public SubmitOrderFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
