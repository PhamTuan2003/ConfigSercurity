package com.fpt.ezpark.vn.model.DTO.request;

import com.fpt.ezpark.vn.common.utill.validation.PasswordMatches;
import com.fpt.ezpark.vn.common.utill.validation.ValidPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    @Email
    @NotEmpty(message = "Email is required.")
    private String email;

    @ValidPassword
    @NotEmpty(message = "Password is required.")
    private String password;

    @NotEmpty(message = "Password confirmation is required.")
    private String passwordConfirmation;
}
