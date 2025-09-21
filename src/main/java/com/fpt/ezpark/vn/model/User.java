package com.fpt.ezpark.vn.model;

import com.fpt.ezpark.vn.common.utill.validation.PasswordMatches;
import com.fpt.ezpark.vn.common.utill.validation.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@PasswordMatches
@Getter
@Setter
@EqualsAndHashCode(exclude = "passwordConfirmation")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Email
    @NotEmpty(message = "Email is required.")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ValidPassword
    @NotEmpty(message = "Password is required.")
    @Column(name = "password")
    private String password;

    @Transient
    @NotEmpty(message = "Password confirmation is required.")
    private String passwordConfirmation;

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + "]";
    }
}
