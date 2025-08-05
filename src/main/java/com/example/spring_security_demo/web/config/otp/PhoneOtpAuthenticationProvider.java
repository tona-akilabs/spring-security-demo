package com.example.spring_security_demo.web.config.otp;

import com.example.spring_security_demo.persistence.CustomerRepository;
import com.example.spring_security_demo.web.model.Customer;
import com.example.spring_security_demo.web.service.CustomCustomerDetailsService;
import com.example.spring_security_demo.web.service.CustomerDetailsService;
import com.example.spring_security_demo.web.service.OtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhoneOtpAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(PhoneOtpAuthenticationProvider.class);
    @Autowired
    private OtpService otpService;

    /*@Autowired
    private UserDetailsService userDetailsService;*/

    @Autowired
    // @Qualifier("customCustomerDetailsService")
    private CustomerDetailsService customerDetailsService;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNumber = authentication.getPrincipal().toString();
        String otp = authentication.getCredentials().toString();

        if (!otpService.validateOtp(phoneNumber, otp)) {
            throw new BadCredentialsException("Invalid OTP");
        }
        logger.info("✅ OTP validated successfully for phone: {}", phoneNumber);

        // UserDetails userDetails = customerDetailsService.loadCustomerByPhoneNumber(phoneNumber);
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new BadCredentialsException("Phone number not found: " + phoneNumber));
        return new PhoneOtpAuthenticationToken(customer, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneOtpAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
