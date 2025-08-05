package com.example.spring_security_demo.web.service;

import com.example.spring_security_demo.validation.EmailExistsException;
import com.example.spring_security_demo.web.model.User;

public interface IUserService {
    User registerNewUser(User user) throws EmailExistsException;

    User updateExistingUser(User user) throws EmailExistsException;
}
