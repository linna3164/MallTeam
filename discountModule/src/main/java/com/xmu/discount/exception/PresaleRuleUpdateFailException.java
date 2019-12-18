package com.xmu.discount.exception;

public class PresaleRuleUpdateFailException extends Exception{
    public PresaleRuleUpdateFailException() {
    }

    public PresaleRuleUpdateFailException(String message) {
        super(message);
    }

    public PresaleRuleUpdateFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public PresaleRuleUpdateFailException(Throwable cause) {
        super(cause);
    }

    public PresaleRuleUpdateFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
