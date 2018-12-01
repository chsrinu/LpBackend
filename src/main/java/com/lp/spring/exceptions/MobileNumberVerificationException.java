package com.lp.spring.exceptions;

public class MobileNumberVerificationException extends Exception {
    public MobileNumberVerificationException() {
    }

    public MobileNumberVerificationException(String message) {
        super(message);
    }
}
