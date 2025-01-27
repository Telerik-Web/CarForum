package com.telerikacademy.web.forumsystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "phone_numbers")
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_number_id")
    private int id;

    @Column(name = "phone_number")
    private String number;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    public PhoneNumber() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
