package com.telerikacademy.web.forumsystem.repositories;

import com.telerikacademy.web.forumsystem.models.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findById(int id);
    User findByUsername(String username);
}
