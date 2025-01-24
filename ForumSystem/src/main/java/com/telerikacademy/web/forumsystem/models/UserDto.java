package com.telerikacademy.web.forumsystem.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.checkerframework.common.aliasing.qual.Unique;

public class UserDto {

    @NotNull(message = "Firstname can't be empty.")
    @Size(min = 4, max = 32, message = "Firstname should be between 4 and 32 symbols.")
    private String firstName;

    @NotNull(message = "Lastname can't be empty.")
    @Size(min = 4, max = 32, message = "Lastname should be between 4 and 32 symbols.")
    private String lastName;

    @Email
    @Unique
    @NotNull
    private String email;

    @NotNull
    private String password;

    private boolean isAdmin;

    private boolean isBlocked;

    public UserDto() {
    }

    public UserDto(String firstName, String lastName, String email, String password) {
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
