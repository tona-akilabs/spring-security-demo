package com.example.spring_security_demo.web.service;

import com.example.spring_security_demo.persistence.CustomerRepository;
import com.example.spring_security_demo.persistence.UserRepository;
import com.example.spring_security_demo.validation.EmailExistsException;
import com.example.spring_security_demo.web.model.Customer;
import com.example.spring_security_demo.web.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService implements IUserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                true,
                true, true, true,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Override
    public User registerNewUser(final User user) throws EmailExistsException {
        if (emailExist(user.getEmail())) {
            throw new EmailExistsException("There is an account with that email address: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        final User user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public User updateExistingUser(User user) throws EmailExistsException {
        final Long id = user.getId();
        final String email = user.getEmail();
        final User emailOwner = userRepository.findByEmail(email);
        if (emailOwner != null && !id.equals(emailOwner.getId())) {
            throw new EmailExistsException("Email not available.");
        }
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }
}
