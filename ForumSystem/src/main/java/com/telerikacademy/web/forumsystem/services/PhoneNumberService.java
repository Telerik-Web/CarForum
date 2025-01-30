package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.models.PhoneNumber;
import com.telerikacademy.web.forumsystem.models.User;

public interface PhoneNumberService {
    PhoneNumber getByUser(User user);
    void create(PhoneNumber phoneNumber, User user, User userToAddPhoneNumber);
    void update(PhoneNumber existingPhoneNumber, PhoneNumber phoneNumber, User user, User userToUpdatePhoneNumber);
    void delete(PhoneNumber phoneNumber, User user);
}
