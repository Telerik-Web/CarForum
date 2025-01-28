package com.telerikacademy.web.forumsystem.models;


import jakarta.validation.constraints.NotNull;

public class PhoneNumberDTO {

    @NotNull
    private String number;

    @NotNull
    private String createdBy;

    public PhoneNumberDTO() {
    }

    public PhoneNumberDTO(String number, String createdBy) {
        this.number = number;
        this.createdBy = createdBy;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String username) {
        this.createdBy = username;
    }
}
