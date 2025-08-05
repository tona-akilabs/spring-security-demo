package com.example.spring_security_demo.web.config;

import com.example.spring_security_demo.web.service.CustomUserDetailsPasswordService;
import com.example.spring_security_demo.web.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class AppSecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final CustomUserDetailsPasswordService customUserDetailsPasswordService;

    public AppSecurityConfig(PasswordEncoder passwordEncoder,
                             UserService userService,
                             CustomUserDetailsPasswordService customUserDetailsPasswordService) {
        super();
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.customUserDetailsPasswordService = customUserDetailsPasswordService;
    }

    //

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
                .userDetailsPasswordManager(customUserDetailsPasswordService);
    }*/ // @formatter:on

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {// @formatter:off

        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().authenticated())

                .formLogin((form) -> form
                        .loginPage("/login").permitAll()
                        .loginProcessingUrl("/doLogin")
                        .successHandler(customSuccessHandler()))

                .logout((logout) -> logout
                        .logoutUrl("/logout"))

                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    } // @formatter:on

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        // provider.setUserDetailsPasswordService(customUserDetailsPasswordService);
        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            // Custom logic, e.g., redirect to a specific page
            response.sendRedirect("/user");
        };
    }
}
