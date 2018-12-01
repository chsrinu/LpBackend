package com.lp.spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lp.spring.exceptions.MobileNumberVerificationException;
import com.lp.spring.model.OtpApiResponse;
import com.lp.spring.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@PropertySource("classpath:application_properties")
public class OTPSenderServiceImpl implements OTPSenderService {
    private Logger LOG = Logger.getLogger(OTPSenderServiceImpl.class);
    @Value("${authUrl}")
    String myAuthUrl;
    @Value("${authKey}")
    String myAuthkey;
    @Override
    public void sendSms(User user, String otp) throws IOException, MobileNumberVerificationException {
        //String POST_URL = "http://control.msg91.com/api/sendotp.php";
        String sms = "Your verification code is ##OTP##."+otp;
        String cc = user.getCountryCode();
        LOG.info("authUrl:"+myAuthUrl+" "+myAuthkey);
        String mobileNumber = user.getUserName();
        String POST_PARAMS = "authkey="+myAuthkey+"&message="+sms+"&sender=LPhone&mobile="+cc+mobileNumber+"&otp="+otp;
        URL url = new URL(myAuthUrl);
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
        OtpApiResponse res = new ObjectMapper().readValue(response.toString(), OtpApiResponse.class);
        LOG.info(res.getType()+" : "+res.getMessage());
        if (res.getType().equals("success")) {
            LOG.info("Post request is Successful");
        }else{
            LOG.info(res.getType()+" : "+res.getMessage());
            throw new MobileNumberVerificationException(res.getMessage());
        }
    }
}
