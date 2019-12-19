package com.xmu.discount.exception;

public class GrouponRuleUnValidException  extends Exception {
    public GrouponRuleUnValidException() {
    }

    public GrouponRuleUnValidException(String message) {
        super(message);
    }

    public GrouponRuleUnValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public GrouponRuleUnValidException(Throwable cause) {
        super(cause);
    }

    public GrouponRuleUnValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
