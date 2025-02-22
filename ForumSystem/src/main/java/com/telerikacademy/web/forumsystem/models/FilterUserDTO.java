package com.telerikacademy.web.forumsystem.models;

import java.util.Optional;

public class FilterUserDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String sortBy;
    private String sortOrder;

    public FilterUserDTO() {
    }

    public static boolean checkIfUserFilterIsEmpty(FilterUserDTO filterUserDTO) {
        return (filterUserDTO.getFirstName() == null || filterUserDTO.getFirstName().isEmpty()) &&
                (filterUserDTO.getLastName() == null || filterUserDTO.getLastName().isEmpty()) &&
                (filterUserDTO.getUsername() == null || filterUserDTO.getUsername().isEmpty()) &&
                (filterUserDTO.getEmail() == null || filterUserDTO.getEmail().isEmpty()) &&
                (filterUserDTO.getSortBy() == null || filterUserDTO.getSortBy().isEmpty()) &&
                (filterUserDTO.getSortOrder() == null || filterUserDTO.getSortOrder().isEmpty());

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
