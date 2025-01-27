package com.telerikacademy.web.forumsystem.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.checkerframework.common.aliasing.qual.Unique;

public class UserDTO {

    @NotNull(message = "Firstname can't be empty.")
    @Size(min = 4, max = 32, message = "Firstname should be between 4 and 32 symbols.")
    private String firstName;

    @NotNull(message = "Lastname can't be empty.")
    @Size(min = 4, max = 32, message = "Lastname should be between 4 and 32 symbols.")
    private String lastName;

    @Email
    //move the unique check to the service - DONE
    @NotNull
    private String email;

    //do the admin and the block update in the user

    //make userDtoOut - DONE

    //na lacho premissionhelper go vkarai w user

    //phone number for admin/ and delete the username and password

    //phone number repository and service

    @NotNull
    private String password;

    private boolean isAdmin;

    private boolean isBlocked;

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
