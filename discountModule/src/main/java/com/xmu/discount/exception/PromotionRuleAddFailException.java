package com.xmu.discount.exception;

/**
 * @author lenovo
 */
public class PromotionRuleAddFailException extends Exception {
    public PromotionRuleAddFailException() {
    }

    public PromotionRuleAddFailException(String message) {
        super(message);
    }

    public PromotionRuleAddFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public PromotionRuleAddFailException(Throwable cause) {
        super(cause);
    }

    public PromotionRuleAddFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
