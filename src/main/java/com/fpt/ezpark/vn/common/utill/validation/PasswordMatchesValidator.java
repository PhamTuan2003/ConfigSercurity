package com.fpt.ezpark.vn.common.utill.validation;

import com.fpt.ezpark.vn.model.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements  ConstraintValidator<PasswordMatches, User> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user == null) {
            return false;
        }
        String password = user.getPassword();
        String confirm = user.getPasswordConfirmation();

        if (password == null || confirm == null) {
            return false;
        }
        return password.equals(confirm);
    }
}
