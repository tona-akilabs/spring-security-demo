package com.example.spring_security_demo.web.config.otp;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class PhoneOtpAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final AuthenticationSuccessHandler successHandler;

    public PhoneOtpAuthenticationFilter(AuthenticationManager authenticationManager,
                                        AuthenticationSuccessHandler successHandler) {
        super("/login/otp"); // This is fine; internally avoids deprecated class
        setAuthenticationManager(authenticationManager);
        this.successHandler = successHandler;
        setAuthenticationSuccessHandler(successHandler); // 👈 attach here
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String phoneNumber = request.getParameter("phoneNumber");
        String otp = request.getParameter("otp");

        PhoneOtpAuthenticationToken token = new PhoneOtpAuthenticationToken(phoneNumber, otp);
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        logger.warn("❌ OTP authentication failed: {}");

        // Return custom JSON response (or redirect)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + failed.getMessage() + "\"}");
    }
}
