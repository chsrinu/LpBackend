package com.lp.spring.exceptions;

public class PasswordLengthException extends Exception {
    private static final long serialVersionUID = 1L;

    public PasswordLengthException() {
    }

    public PasswordLengthException(String message) {
        super(message);
    }
}
