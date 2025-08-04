package com.example.spring_security_demo.persistence;

import com.example.spring_security_demo.web.model.User;

public interface UserRepository {
    Iterable<User> findAll();

    User save(User user);

    User findUser(Long id);

    void deleteUser(Long id);
}
