package com.telerikacademy.web.forumsystem.models;

public class UserDTOOut {
    private String firstName;

    private String lastName;

    private String email;

    private boolean isAdmin;

    private boolean isBlocked;

    public UserDTOOut() {
    }

    public UserDTOOut(String firstName, String lastName, String email, boolean isAdmin, boolean isBlocked) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAdmin = isAdmin;
        this.isBlocked = isBlocked;
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
