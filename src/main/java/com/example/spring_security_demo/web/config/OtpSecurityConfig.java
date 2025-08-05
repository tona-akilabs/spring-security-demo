package com.example.spring_security_demo.web.config;

import com.example.spring_security_demo.web.config.otp.CustomAuthenticationSuccessHandler;
import com.example.spring_security_demo.web.config.otp.PhoneOtpAuthenticationFilter;
import com.example.spring_security_demo.web.config.otp.PhoneOtpAuthenticationProvider;
import com.example.spring_security_demo.web.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class OtpSecurityConfig {
    @Autowired
    private PhoneOtpAuthenticationProvider otpAuthProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                //.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/otp", "/send-otp").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(otpAuthProvider)
                .addFilterBefore(phoneOtpAuthenticationFilter(http), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService); // Set service
        return authBuilder.build(); // ✅ Call build on the builder
    }

    @Bean
    public PhoneOtpAuthenticationFilter phoneOtpAuthenticationFilter(HttpSecurity http) throws Exception {
        return new PhoneOtpAuthenticationFilter(authManager(http), successHandler);
    }
}
