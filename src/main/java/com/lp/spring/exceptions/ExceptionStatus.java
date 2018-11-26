package com.lp.spring.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExceptionStatus {
    REGISTRATION_EXCEPTION(100,"User Already existing"),
    PASSWORD_LENGTH_EXCEPTION(100,"Password length should be 8-15"),
    INVALID_CREDENTIALS(101,"Invalid credentials"),
    MISSING_CREDENTIALS(102,"Credentials Missing"),
    ACCESS_DENIED(103,"You don't have Access to this URL"),
    BAD_REQUEST(104, "Please check your request before sending"),
    UNKNOWN_EXCEPTION(105,"Unable to process now, Please try later"),
    INCORRECT_PASSWORD(106,"Password provided is Invalid");

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private ExceptionStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
