package com.example.spring_security_demo.web.service;

import com.example.spring_security_demo.persistence.CustomerRepository;
import com.example.spring_security_demo.web.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomCustomerDetailsService implements CustomerDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadCustomerByPhoneNumber(username);
    }

    @Override
    public UserDetails loadCustomerByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Phone number not found: " + phoneNumber));

        return new org.springframework.security.core.userdetails.User(
                customer.getPhoneNumber(),
                "", // No password required for OTP-based auth
                true,
                true, true, true,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
