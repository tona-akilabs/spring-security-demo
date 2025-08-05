package com.example.spring_security_demo.web.config.otp;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class PhoneOtpAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private final Object credentials;

    public PhoneOtpAuthenticationToken(String phoneNumber, String otp) {
        super(null);
        this.principal = phoneNumber;
        this.credentials = otp;
        setAuthenticated(false);
    }

    public PhoneOtpAuthenticationToken(UserDetails userDetails) {
        super(userDetails.getAuthorities());
        this.principal = userDetails;
        this.credentials = null;
        setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }
}
