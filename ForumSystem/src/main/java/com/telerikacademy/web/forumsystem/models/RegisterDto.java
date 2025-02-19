package com.telerikacademy.web.forumsystem.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class RegisterDto extends LoginDto {
    @NotEmpty(message = "Password confirmation cannot be empty!")
    private String passwordConfirm;

    @NotEmpty(message = "First name cannot be empty!")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty!")
    private String lastName;

    @NotEmpty(message = "Email cannot be empty!")
    @Email(message = "Email must be valid!")
    private String email;

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
