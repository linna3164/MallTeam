package com.xmu.discount.exception;

public class PresaleRuleDeleteFailException extends Exception {
    public PresaleRuleDeleteFailException() {
    }

    public PresaleRuleDeleteFailException(String message) {
        super(message);
    }

    public PresaleRuleDeleteFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public PresaleRuleDeleteFailException(Throwable cause) {
        super(cause);
    }

    public PresaleRuleDeleteFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
