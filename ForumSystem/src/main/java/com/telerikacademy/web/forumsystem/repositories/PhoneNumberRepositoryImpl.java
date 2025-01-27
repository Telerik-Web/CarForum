package com.telerikacademy.web.forumsystem.repositories;

import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.models.PhoneNumber;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class PhoneNumberRepositoryImpl implements PhoneNumberRepository {

    private final SessionFactory sessionFactory;

    public PhoneNumberRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PhoneNumber getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            PhoneNumber phoneNumber = session.get(PhoneNumber.class, id);
            if (phoneNumber == null) {
                throw new EntityNotFoundException("PhoneNumber", id);
            }
            return phoneNumber;
        }
    }
}
