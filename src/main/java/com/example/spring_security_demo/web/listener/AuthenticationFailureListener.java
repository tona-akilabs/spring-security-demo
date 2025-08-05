package com.example.spring_security_demo.web.listener;

import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

//@Component
public class AuthenticationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticationFailureListener.class);

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        Authentication authentication = event.getAuthentication();
        Exception exception = event.getException();
        logger.error("❌ Login failed for " + authentication.getName() + ": " + exception.getMessage());
    }
}
