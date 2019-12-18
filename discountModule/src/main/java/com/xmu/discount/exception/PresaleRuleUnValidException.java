package com.xmu.discount.exception;

public class PresaleRuleUnValidException extends Exception {

    public PresaleRuleUnValidException() {
    }

    public PresaleRuleUnValidException(String message) {
        super(message);
    }

    public PresaleRuleUnValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public PresaleRuleUnValidException(Throwable cause) {
        super(cause);
    }

    public PresaleRuleUnValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
