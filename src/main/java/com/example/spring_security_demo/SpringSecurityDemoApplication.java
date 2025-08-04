package com.example.spring_security_demo;

import com.example.spring_security_demo.persistence.InMemoryUserRepository;
import com.example.spring_security_demo.persistence.UserRepository;
import com.example.spring_security_demo.web.config.AppSecurityConfig;
import com.example.spring_security_demo.web.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan("com.example.spring_security_demo.web")
public class SpringSecurityDemoApplication {

	@Bean
	public UserRepository userRepository() {
		return new InMemoryUserRepository();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Converter<String, User> messageConverter() {
		return new Converter<String, User>() {
			@Override
			public User convert(String id) {
				return userRepository().findUser(Long.valueOf(id));
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoApplication.class, args);
	}

}
