package com.xmu.discount.exception;

/**
 * @author lenovo
 */
public class PromotionRuleDeleteFailException extends Exception {

    public PromotionRuleDeleteFailException() {
    }

    public PromotionRuleDeleteFailException(String message) {
        super(message);
    }

    public PromotionRuleDeleteFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public PromotionRuleDeleteFailException(Throwable cause) {
        super(cause);
    }

    public PromotionRuleDeleteFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
