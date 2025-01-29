package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.models.FilterUserOptions;
import com.telerikacademy.web.forumsystem.models.User;

import java.util.List;

public interface UserService {
    List<User> getAll(FilterUserOptions filterOptions);

    User getById(User user, int id);

    User getByUsername(String username);

//    User findByUsername(User user, String username);
//
//    User findByEmail(User user, String email);
//
//    User findByFirstname(User user, String firstName);

    void create(User user);

    void update(User user, User userFromHeader, int id);

    void delete(int id, User userFromHeader);
}
