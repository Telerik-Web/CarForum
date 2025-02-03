package com.telerikacademy.web.forumsystem.repositories;

import com.telerikacademy.web.forumsystem.models.FilterUserOptions;
import com.telerikacademy.web.forumsystem.models.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserRepository {
    List<User> getAll(FilterUserOptions filterOptions);

    User getById(int id);

    User getByUsername(String username);

    User getByEmail(String email);

    void alterAdminPermissions(User user);

    void alterBlock(User user);

    void create(@Valid User user);

    void update(@Valid User user, int id);

    void delete(int id);
}
