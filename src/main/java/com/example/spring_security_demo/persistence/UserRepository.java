package com.example.spring_security_demo.persistence;

import com.example.spring_security_demo.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
