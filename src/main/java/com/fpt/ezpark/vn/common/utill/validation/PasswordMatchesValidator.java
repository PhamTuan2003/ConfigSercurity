package com.fpt.ezpark.vn.common.utill.validation;

import org.springframework.stereotype.Component;

import com.fpt.ezpark.vn.model.entity.User;
import com.fpt.ezpark.vn.model.DTO.request.UserRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return false;
        }

        String password;

        if (obj instanceof User user) {
            password = user.getPassword();
        } else if (obj instanceof UserRequestDTO userRequestDTO) {
            password = userRequestDTO.getPassword();
        } else {
            return false;
        }

        return true;
    }
}
