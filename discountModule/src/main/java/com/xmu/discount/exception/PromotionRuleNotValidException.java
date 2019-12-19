package com.xmu.discount.exception;

public class PromotionRuleNotValidException extends Exception {
    public PromotionRuleNotValidException() {
    }

    public PromotionRuleNotValidException(String message) {
        super(message);
    }

    public PromotionRuleNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public PromotionRuleNotValidException(Throwable cause) {
        super(cause);
    }

    public PromotionRuleNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
