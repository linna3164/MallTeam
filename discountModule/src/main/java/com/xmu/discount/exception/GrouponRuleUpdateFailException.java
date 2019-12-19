package com.xmu.discount.exception;

public class GrouponRuleUpdateFailException extends Exception {
    public GrouponRuleUpdateFailException() {
    }

    public GrouponRuleUpdateFailException(String message) {
        super(message);
    }

    public GrouponRuleUpdateFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public GrouponRuleUpdateFailException(Throwable cause) {
        super(cause);
    }

    public GrouponRuleUpdateFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
