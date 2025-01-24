package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.models.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(@Valid User user);

    User updateUser(@Valid User user, int id);

    void deleteUser(int id);
}
