package com.telerikacademy.web.forumsystem.models;


import jakarta.validation.constraints.NotNull;

public class PhoneNumberDTO {

    @NotNull
    private String number;

    @NotNull
    private User createdBy;

    public PhoneNumberDTO() {
    }

    public PhoneNumberDTO(String number, User createdBy) {
        this.number = number;
        this.createdBy = createdBy;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User user_id) {
        this.createdBy = user_id;
    }
}
