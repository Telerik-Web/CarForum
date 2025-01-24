package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.models.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByFirstname(String firstName);

    User createUser(User user);

    User updateUser(User user, User userFromHeader, int id);

    void deleteUser(int id, User userFromHeader);
}
