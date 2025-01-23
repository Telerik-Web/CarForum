package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(int id);
    User findByUsername(String username);
}
