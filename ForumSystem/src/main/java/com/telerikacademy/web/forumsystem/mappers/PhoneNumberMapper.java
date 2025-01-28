package com.telerikacademy.web.forumsystem.mappers;

import com.telerikacademy.web.forumsystem.models.PhoneNumber;
import com.telerikacademy.web.forumsystem.models.PhoneNumberDTO;
import com.telerikacademy.web.forumsystem.services.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberMapper {
    PhoneNumberService phoneNumberService;

    @Autowired
    public PhoneNumberMapper(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    public PhoneNumber map(PhoneNumberDTO phoneNumberDTO) {
        return dtoToObject(phoneNumberDTO);
    }

    private PhoneNumber dtoToObject(PhoneNumberDTO phoneNumberDTO) {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setNumber(phoneNumberDTO.getNumber());
        phoneNumber.setCreatedBy(phoneNumberDTO.getCreatedBy());
        return phoneNumber;
    }
}
