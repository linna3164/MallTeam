package com.xmu.discount.exception;

public class GrouponRuleDeleteFailException extends Exception
{
    public GrouponRuleDeleteFailException() {
    }

    public GrouponRuleDeleteFailException(String message) {
        super(message);
    }

    public GrouponRuleDeleteFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public GrouponRuleDeleteFailException(Throwable cause) {
        super(cause);
    }

    public GrouponRuleDeleteFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
