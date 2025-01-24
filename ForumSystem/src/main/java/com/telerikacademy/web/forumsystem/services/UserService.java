package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(User user, int id);

    User findByUsername(String username);

    User findByUsername(User user, String username);

    User findByEmail(User user, String email);

    User findByFirstname(User user, String firstName);

    void createUser(User user);

    void updateUser(User user, User userFromHeader, int id);

    void deleteUser(int id, User userFromHeader);
}
