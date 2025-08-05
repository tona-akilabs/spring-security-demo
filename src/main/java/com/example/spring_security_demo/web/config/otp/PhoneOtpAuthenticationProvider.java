package com.example.spring_security_demo.web.config.otp;

import com.example.spring_security_demo.web.service.OtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class PhoneOtpAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(PhoneOtpAuthenticationProvider.class);
    @Autowired
    private OtpService otpService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNumber = authentication.getPrincipal().toString();
        String otp = authentication.getCredentials().toString();

        if (!otpService.validateOtp(phoneNumber, otp)) {
            throw new BadCredentialsException("Invalid OTP");
        }
        logger.info("✅ OTP validated successfully for phone: {}", phoneNumber);

        UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNumber);
        return new PhoneOtpAuthenticationToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneOtpAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
