package com.example.spring_security_demo.web.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    private final Map<String, String> otpCache = new ConcurrentHashMap<>();

    public void generateOtp(String phoneNumber) {
        String otp = String.valueOf(new Random().nextInt(899999) + 100000);
        otpCache.put(phoneNumber, otp);

        // send to user via SMS (Twilio, etc.)
        System.out.println("Send OTP to " + phoneNumber + ": " + otp);
    }

    public boolean validateOtp(String phoneNumber, String otp) {
        String validOtp = otpCache.get(phoneNumber);
        return validOtp != null && validOtp.equals(otp);
    }

    public void clearOtp(String phoneNumber) {
        otpCache.remove(phoneNumber);
    }
}
