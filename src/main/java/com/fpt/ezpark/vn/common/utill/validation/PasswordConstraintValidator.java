package com.fpt.ezpark.vn.common.utill.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.passay.*;

import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {


    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false; // hoặc cho phép null tùy use-case
        }

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new WhitespaceRule()
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        // Xóa lỗi mặc định, add message mới
        context.disableDefaultConstraintViolation();
        String messageTemplate = String.join("\n", validator.getMessages(result));
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation();

        return false;
    }
}
