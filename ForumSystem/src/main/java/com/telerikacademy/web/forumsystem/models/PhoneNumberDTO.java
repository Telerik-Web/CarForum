package com.telerikacademy.web.forumsystem.models;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PhoneNumberDTO {

    @NotNull
    @Size(min = 10, message = "Phone number length can't be less than 10 digits")
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
