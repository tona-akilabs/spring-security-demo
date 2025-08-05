package com.example.spring_security_demo.web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomerDetailsService extends UserDetailsService {
    UserDetails loadCustomerByPhoneNumber(String phoneNumber) throws UsernameNotFoundException;
}
