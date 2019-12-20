package com.xmu.discount.exception;

/**
 * @author lenovo
 */
public class GrouponRuleAddFailException extends Exception {

    public GrouponRuleAddFailException() {
    }

    public GrouponRuleAddFailException(String message) {
        super(message);
    }

    public GrouponRuleAddFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public GrouponRuleAddFailException(Throwable cause) {
        super(cause);
    }

    public GrouponRuleAddFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
