package com.telerikacademy.web.forumsystem.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotEmpty(message = "Firstname can't be empty.")
    @Size(min = 4, max = 32, message = "Firstname should be between 4 and 32 symbols.")
    private String firstName;

    @NotEmpty(message = "Lastname can't be empty.")
    @Size(min = 4, max = 32, message = "Lastname should be between 4 and 32 symbols.")
    private String lastName;

    @Email
    @NotNull
    private String email;

    @NotEmpty(message = "Password can't be empty.")
    private String password;

    @NotNull
    private String username;

    public UserDTO() {
    }

    public UserDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
