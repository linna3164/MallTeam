package com.xmu.discount.exception;

/**
 * @author lenovo
 */
public class PresaleRuleAddFailException extends Exception {
    public PresaleRuleAddFailException() {
    }

    public PresaleRuleAddFailException(String message) {
        super(message);
    }

    public PresaleRuleAddFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public PresaleRuleAddFailException(Throwable cause) {
        super(cause);
    }

    public PresaleRuleAddFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
