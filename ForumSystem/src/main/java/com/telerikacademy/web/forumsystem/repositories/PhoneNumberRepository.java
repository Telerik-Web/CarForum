package com.telerikacademy.web.forumsystem.repositories;

import com.telerikacademy.web.forumsystem.models.PhoneNumber;
import com.telerikacademy.web.forumsystem.models.User;

public interface PhoneNumberRepository {
    PhoneNumber getByUser(User user);
    void create(PhoneNumber phoneNumber);
    void update(PhoneNumber phoneNumber);
    void delete(PhoneNumber phoneNumber);
}
