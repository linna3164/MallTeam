package com.xmu.discount.exception;

public class PresaleRuleSetDisableFailException extends Exception {
    public PresaleRuleSetDisableFailException() {
    }

    public PresaleRuleSetDisableFailException(String message) {
        super(message);
    }

    public PresaleRuleSetDisableFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public PresaleRuleSetDisableFailException(Throwable cause) {
        super(cause);
    }

    public PresaleRuleSetDisableFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
