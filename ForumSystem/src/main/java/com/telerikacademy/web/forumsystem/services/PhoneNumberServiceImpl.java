package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.models.PhoneNumber;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.repositories.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.telerikacademy.web.forumsystem.helpers.PermissionHelper.*;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public PhoneNumberServiceImpl(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @Override
    public PhoneNumber getByUser(User user) {
        checkIfAdmin(user);
        return phoneNumberRepository.getByUser(user);
    }

    @Override
    public void create(PhoneNumber phoneNumber, User user, User userToAddPhoneNumber) {
        checkIfAdmin(user);
        checkIfAdmin(userToAddPhoneNumber);
        phoneNumberRepository.create(phoneNumber);
    }

    @Override
    public void update(PhoneNumber phoneNumber, User user) {
        checkIfAdmin(user);
        phoneNumberRepository.update(phoneNumber);
    }

    @Override
    public void delete(PhoneNumber phoneNumber, User user) {
        checkIfAdmin(user);
        phoneNumberRepository.delete(phoneNumber);
    }
}
