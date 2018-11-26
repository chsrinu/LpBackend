package com.lp.spring.controller;

import com.lp.spring.exceptions.ExceptionStatus;
import com.lp.spring.exceptions.InvalidPasswordException;
import com.lp.spring.exceptions.PasswordLengthException;
import com.lp.spring.exceptions.RegistrationException;
import com.lp.spring.model.ErrorResponse;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private Logger logger = Logger.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ErrorResponse> registrationException(RegistrationException e){
        return buildErrorResponse(ExceptionStatus.REGISTRATION_EXCEPTION,e);
    }
    @ExceptionHandler(PasswordLengthException.class)
    public ResponseEntity<ErrorResponse> passwordLengthExceeded(PasswordLengthException e){
        return buildErrorResponse(ExceptionStatus.PASSWORD_LENGTH_EXCEPTION,e);
    }
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> incorrectPassword(InvalidPasswordException e){
        return buildErrorResponse(ExceptionStatus.INCORRECT_PASSWORD, e);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception e){
        e.printStackTrace();
        return buildErrorResponse(ExceptionStatus.BAD_REQUEST,e);
    }

    private <T extends Exception> ResponseEntity<ErrorResponse> buildErrorResponse(ExceptionStatus status,T e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(status);
        errorResponse.setMessage(e.getMessage());
        logger.error(e.getMessage());
        if(e.getCause()!=null)
            errorResponse.setDetails(e.getCause().getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
