package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.exceptions.DuplicateEntityException;
import com.telerikacademy.web.forumsystem.models.PhoneNumber;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.repositories.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.telerikacademy.web.forumsystem.helpers.PermissionHelper.*;
import static org.springframework.util.ObjectUtils.isEmpty;

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

        if (!isEmpty(userToAddPhoneNumber.getPhoneNumber())) {
            throw new DuplicateEntityException("This user already has a phone number");
        }

        phoneNumber.setCreatedBy(userToAddPhoneNumber);
        userToAddPhoneNumber.setPhoneNumber(phoneNumber);

        phoneNumberRepository.create(phoneNumber);
    }

    @Override
    public void update(PhoneNumber existingPhoneNumber, PhoneNumber phoneNumber, User user, User userToUpdatePhoneNumber) {
        checkIfAdmin(userToUpdatePhoneNumber);
        checkIfAdmin(user);

        phoneNumber.setCreatedBy(existingPhoneNumber.getCreatedBy());
        phoneNumber.setId(existingPhoneNumber.getId());

        phoneNumberRepository.update(phoneNumber);
    }

    @Override
    public void delete(User user , User userToDeletePhoneNumber) {
        checkIfAdmin(user);
        PhoneNumber phoneNumber = phoneNumberRepository.getByUser(userToDeletePhoneNumber);
        phoneNumberRepository.delete(phoneNumber);
    }
}
