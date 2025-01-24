//persistence layer

package com.telerikacademy.web.forumsystem.repositories;

import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From User", User.class).list();
        }
    }

    @Override
    public User findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", id);
            }
            return user;
        }
    }

    @Override
    public User findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> user = session.createQuery("From User Where username = :username", User.class);
            user.setParameter("username", username);
            return user
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("User", "username", username));
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> user = session.createQuery("From User where email = :email", User.class);
            user.setParameter("email", email);
            return user
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("User", "email", email));
        }
    }

    @Override
    public User findByFirstname(String firstName) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> user = session.createQuery("from User where firstName = :firstname", User.class);
            user.setParameter("firstName", firstName);
            return user
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("User", "firstname", firstName));
        }
    }

    @Override
    public void createUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateUser(User user, int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteUser(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(findById(id));
            session.getTransaction().commit();
        }
    }
}
