package com.telerikacademy.web.forumsystem.repositories;

import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.models.PhoneNumber;
import com.telerikacademy.web.forumsystem.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PhoneNumberRepositoryImpl implements PhoneNumberRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PhoneNumberRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PhoneNumber getByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<PhoneNumber> phone = session.createQuery(
                    "From PhoneNumber Where createdBy = :user_id", PhoneNumber.class);
            phone.setParameter("user_id", user.getId());
            return phone
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Phone", "userId",
                            String.valueOf(user.getId())));
        }
    }

    @Override
    public void create(PhoneNumber phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(phoneNumber);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(PhoneNumber phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(phoneNumber);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(PhoneNumber phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(phoneNumber);
            session.getTransaction().commit();
        }
    }
}
