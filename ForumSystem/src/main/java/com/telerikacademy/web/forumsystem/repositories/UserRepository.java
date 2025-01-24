package com.telerikacademy.web.forumsystem.repositories;

import com.telerikacademy.web.forumsystem.models.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findById(int id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByFirstname(String firstName);

    void createUser(@Valid User user);

    void updateUser(@Valid User user, int id);

    void deleteUser(int id);
}
