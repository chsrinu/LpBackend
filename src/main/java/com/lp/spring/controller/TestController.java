package com.lp.spring.controller;

import com.lp.spring.exceptions.InvalidPasswordException;
import com.lp.spring.exceptions.PasswordLengthException;
import com.lp.spring.exceptions.RegistrationException;
import com.lp.spring.model.*;
import com.lp.spring.service.CallOrMessageLogService;
import com.lp.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeoutException;

@RestController
public class TestController {
    @Autowired
    UserService defaultUserServices;
    @Autowired
    CallOrMessageLogService callOrMessageLogService;

    @GetMapping("/register/verifyUser")
    public ResponseEntity verifyUser(@RequestBody User user) throws RegistrationException, PasswordLengthException, IOException {
        defaultUserServices.verifyUser(user);//check userExists or not if not send an otp
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/register/validateUser")
    public ResponseEntity validateUser(@RequestBody OTPObject otpObject){
        defaultUserServices.saveUserForValidOtp(otpObject);
        return new ResponseEntity(HttpStatus.OK);
        //if(defaultUserServices.isOtpValid(otpObject))
              //  defaultUserServices.saveUser(user);
           // return ResponseEntity.ok("User SuccessFully Registered");
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody User user) throws RegistrationException, PasswordLengthException {
//        defaultUserServices.createUser(user);
//        return ResponseEntity.ok("User SuccessFully Registered");
//    }

    @GetMapping("/login")
    public void userLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/dashBoard");
    }
    @GetMapping("/dashBoard")
    public ResponseEntity<HashMap<String,Set>> loadDashBoard(){
        return ResponseEntity.ok(callOrMessageLogService.loadUserData(getUserFromSecurityContext().getUsername()));
    }

    @PostMapping("/callLog")
    public ResponseEntity updateCallLog(@RequestBody CallEntity callEntity) {
            User user = defaultUserServices.readUser(getUserFromSecurityContext().getUsername(),true);
            callEntity.setUser(user);

            callOrMessageLogService.saveLog(callEntity);

            return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/messageLog")
    public ResponseEntity updateMessageLog(@RequestBody MessageEntity messageEntity) {
        User user = defaultUserServices.readUser(getUserFromSecurityContext().getUsername(), true);
        messageEntity.setUser(user);
        callOrMessageLogService.saveLog(messageEntity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<HashMap<String,String>> getUserProfileDetails(){
        HashMap<String,String> userProfile = defaultUserServices.getUserProfile(getUserFromSecurityContext().getUsername());
        return ResponseEntity.ok(userProfile);
    }
    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody PasswordUpdate passwordUpdate) throws PasswordLengthException, InvalidPasswordException {
        defaultUserServices.changePassword(getUserFromSecurityContext().getUsername(),passwordUpdate);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/admin/deleteUser")
    public ResponseEntity deleteUser(@RequestParam(value="userName")String userName){
        defaultUserServices.deleteUser(userName);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/deleteLog")
    public ResponseEntity deleteLog(@RequestParam(value="logId")String logId){
        callOrMessageLogService.deleteLog(logId);
        return new ResponseEntity(HttpStatus.OK);
    }

    private UserDetails getUserFromSecurityContext() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

}
/**
 * add hibernate validators - not needed
 * create a unique Id for each log like this 7416444746_1 - working
 * deleteLog using the Id created in step above - working
 * admin role for deleteUser endPoint- working
 * check password update is working or not -working
 * Write Unit TestCases using mockito for Service classes - done
 * minimum length of password validation added - working
 * OTP - generation and validation
 * push notifications in website
 *
 * */
