package com.lp.spring.service;

import com.lp.spring.dao.UserRepository;
import com.lp.spring.exceptions.InvalidPasswordException;
import com.lp.spring.exceptions.PasswordLengthException;
import com.lp.spring.exceptions.RegistrationException;
import com.lp.spring.model.OTPObject;
import com.lp.spring.model.PasswordUpdate;
import com.lp.spring.model.User;
import com.lp.spring.model.UserRole;
import com.lp.spring.utility.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

@Service("defaultUserServices")
@PropertySource("classpath:application_properties")
public class UserServiceImpl implements UserService{
    private Logger LOG = Logger.getLogger(UserServiceImpl.class);
    @Autowired
    UserRepository userRepositoryImpl;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    CachingServices cachingServices;
    @Value("authUrl")
    String myAuthUrl;
    @Value("authKey")
    String myAuthkey;


    HashMap<String, String> otpMap = new HashMap<>();
    @Override
    public void verifyUser(User user) throws RegistrationException, PasswordLengthException, IOException {
        if(isUserAlreadyRegistered(user.getUserName()))
            throw new RegistrationException();
        passwordLengthValidation(user.getPassword());
        String myOtp = cachingServices.cacheUser(user);
        //send sms to user
        //myAuthUrl?authkey=myAuthkey&message=sms&sender="LockerPhone"&mobile=mobileNumber;
        sendSms(user,myOtp);
        //user.getRoles().add(new UserRole(CommonUtils.UserProperties.ROLE_USER, user));
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        //userRepositoryImpl.createUser(user);
    }
    @Override
    public void saveUserForValidOtp(OTPObject otpObject) {
        cachingServices.
    }


    private void sendSms(User user, String myOtp) throws IOException {
        String POST_URL = "http://control.msg91.com/api/sendotp.php";
        String sms = "Your verification code is ##OTP## "+myOtp;
        String mobileNumber = user.getUserName();
        String POST_PARAMS = "authkey="+myAuthkey+"&message="+sms+"&sender=LPhone&mobile="+mobileNumber;
        URL url = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        LOG.info("outputStream is "+os.toString());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        LOG.info(responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        // print result
        LOG.info(response.toString());
        if (responseCode == HttpURLConnection.HTTP_OK) {
            LOG.info("Post request is Successful");
        }else{
            LOG.info("Post didn't work");
        }
    }

    @Override
    public User readUser(String userName, boolean initializeListFlag) {
        return userRepositoryImpl.readUser(userName,initializeListFlag);
    }

    @Override
    public void deleteUser(String userName) throws UsernameNotFoundException{
        if(!isUserAlreadyRegistered(userName))
            throw new UsernameNotFoundException("Username: "+userName+" is not found");
        userRepositoryImpl.deleteUser(userName);
    }

    @Override
    public User loadUser(String userName) {
        return userRepositoryImpl.loadUser(userName);
    }

    @Override
    public HashMap<String, String> getUserProfile(String username) {
        User user = readUser(username,false);
        HashMap<String, String> userProfile = new HashMap<>();
        userProfile.put("userName",user.getUserName());
        userProfile.put("countryCode",user.getCountryCode());
        userProfile.put("firstName",user.getFirstName());
        userProfile.put("lastName", user.getLastName());
        return userProfile;
    }

    @Override
    public void changePassword(String username, PasswordUpdate passwordUpdate) throws PasswordLengthException, InvalidPasswordException {
        User user = readUser(username,false);
        if(passwordEncoder.matches(passwordUpdate.getCurrentPassword(),user.getPassword())){
            passwordLengthValidation(passwordUpdate.getNewPassword());
            user.setPassword(passwordEncoder.encode(passwordUpdate.getNewPassword()));
            userRepositoryImpl.updateUser(user);
        }else
            throw new InvalidPasswordException("Password Incorrect, please provide correct password to complete the operation");
    }



    private void passwordLengthValidation(String password) throws PasswordLengthException {
        CommonUtils.isLengthValid(password,"Password"
                ,CommonUtils.Validators.MIN_LENGTH_PASSWORD, CommonUtils.Validators.MAX_LENGTH_PASSWORD);
    }

    private boolean isUserAlreadyRegistered(String userName) {
        return readUser(userName,false) != null;
    }



}
