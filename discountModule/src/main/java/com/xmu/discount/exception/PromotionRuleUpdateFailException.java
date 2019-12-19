package com.xmu.discount.exception;

public class PromotionRuleUpdateFailException extends Exception{
    public PromotionRuleUpdateFailException() {
    }

    public PromotionRuleUpdateFailException(String message) {
        super(message);
    }

    public PromotionRuleUpdateFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public PromotionRuleUpdateFailException(Throwable cause) {
        super(cause);
    }

    public PromotionRuleUpdateFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
