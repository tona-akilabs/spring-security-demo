package com.example.spring_security_demo.web.listener;

import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticationListener.class);

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        Authentication authentication = event.getAuthentication();
        if (event instanceof AuthenticationSuccessEvent) {
            logger.info("✅ Login success: " + authentication.getName());
        } else if (event instanceof AbstractAuthenticationFailureEvent failureEvent) {
            Exception exception = failureEvent.getException();
            logger.error("❌ Login failed for " + authentication.getName() + ": " + exception.getMessage());
        }
    }
}
