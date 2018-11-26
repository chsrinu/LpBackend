package com.lp.spring.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.lp.spring.model.User;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class CachingServicesImpl implements CachingServices{
    //cache based on username and OPT MAX 8
    private static final Integer EXPIRE_MINS = 5;
    private LoadingCache<String, User> otpCache;
    public CachingServicesImpl(){
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, User>() {
            @Override
            public User load(String s) throws Exception {
                return null;
            }
        });
    }
    @Override
    public String cacheUser(User user) {
        String otp = generateOTP();
        otpCache.put(otp,user);
        return otp;
    }

    public String generateOTP(){
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return Integer.toString(otp);
    }
    public void clearOTP(String key){
        otpCache.invalidate(key);
    }

}
