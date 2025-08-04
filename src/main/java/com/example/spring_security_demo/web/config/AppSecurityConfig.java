package com.example.spring_security_demo.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class AppSecurityConfig {
    private final PasswordEncoder passwordEncoder;

    public AppSecurityConfig(PasswordEncoder passwordEncoder) {
        super();
        this.passwordEncoder = passwordEncoder;
    }

    //

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
        auth.
                inMemoryAuthentication().passwordEncoder(passwordEncoder).
                withUser("user").password(passwordEncoder.encode("pass")).
                roles("USER");
    } // @formatter:on

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {// @formatter:off

        http
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().authenticated())

                .formLogin((form) -> form
                        .loginPage("/login").permitAll()
                        .loginProcessingUrl("/doLogin"))

                .logout((logout) -> logout
                        .logoutUrl("/logout"))

                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    } // @formatter:on
}
