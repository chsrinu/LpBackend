package com.lp.spring.service;

import com.lp.spring.exceptions.InvalidPasswordException;
import com.lp.spring.exceptions.PasswordLengthException;
import com.lp.spring.exceptions.RegistrationException;
import com.lp.spring.model.OTPObject;
import com.lp.spring.model.PasswordUpdate;
import com.lp.spring.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public interface UserService {
    void verifyUser(User user) throws RegistrationException, PasswordLengthException, IOException;
    User readUser(String userName,  boolean initializeListFlag);
    void deleteUser(String userName);
    User loadUser(String userName);
    HashMap<String, String> getUserProfile(String username);
    void changePassword(String username, PasswordUpdate passwordUpdate) throws PasswordLengthException, InvalidPasswordException;
    void saveUserForValidOtp(OTPObject otpObject);
}
