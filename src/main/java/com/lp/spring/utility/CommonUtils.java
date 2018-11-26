package com.lp.spring.utility;

import com.google.gson.Gson;
import com.lp.spring.exceptions.PasswordLengthException;
import com.lp.spring.model.ErrorResponse;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application_properties")
public class CommonUtils {

    public static class UserProperties {
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String PASSWORD = "password";
        public static final String COUNTRYCODE = "countryCode";
        public static final String ROLE_USER = "ROLE_USER";
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
    }
    public static class DiscriminatorColumns {
        public static final String LOGS_TYPE_CALL = "call";
        public static final String LOGS_TYPE_MESSAGE = "message";
    }
    public static class Validators {
        public static final int MAX_LENGTH_PASSWORD = 15;
        public static final int MIN_LENGTH_PASSWORD = 8;
    }
    public static String getJsonObject(ErrorResponse errorResponse){
        return new Gson().toJson(errorResponse);
    }

    public static void isLengthValid(String field,String fieldName,int minLength, int maxLength) throws PasswordLengthException {
        if(field.length()<minLength || field.length()>maxLength)
            throw new PasswordLengthException(fieldName+" length should be between "+minLength+" and "+maxLength);
    }
}
