package com.example.spring_security_demo.web.listener;

import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticationSuccessListener.class);
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        logger.info("✅ Login success: " + authentication.getName());
        // You can also log IP, roles, etc. here
    }
}
