package com.example.spring_security_demo.web.service;

import com.example.spring_security_demo.persistence.UserRepository;
import com.example.spring_security_demo.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsPasswordService implements UserDetailsPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        System.out.println("🔥 updatePassword called for: " + user.getUsername());
        User entity = userRepository.findByEmail(user.getUsername());
        entity.setPassword(newPassword);
        userRepository.save(entity);
        return user;
    }
}
