package com.example.spring_security_demo.validation;

import com.example.spring_security_demo.web.model.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PasswordMatchesValidator.class);
    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final User user = (User) obj;
        logger.debug("Validating password matches for user: {}", user.getEmail());
        logger.info(user.toString());
        return user.getPassword()
                .equals(user.getPasswordConfirmation());
    }
}
