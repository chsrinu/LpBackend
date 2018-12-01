package com.lp.spring.service;

import com.lp.spring.exceptions.MobileNumberVerificationException;
import com.lp.spring.model.User;

import java.io.IOException;

public interface OTPSenderService {
    void sendSms(User user, String otp) throws IOException, MobileNumberVerificationException;
}
