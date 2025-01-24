//business layer

package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.controllers.AuthorizationHelper;
import com.telerikacademy.web.forumsystem.exceptions.DuplicateEntityException;
import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByFirstname(String firstName) {
        return userRepository.findByFirstname(firstName);
    }

    @Override
    public User createUser(User user) {
        boolean exists = true;

        try {
            userRepository.findByEmail(user.getEmail());
        } catch (EntityNotFoundException e) {
            exists = false;
        }

        if (exists) {
            throw new DuplicateEntityException("User", "email", user.getEmail());
        }

        userRepository.createUser(user);
        return user;
    }

    @Override
    public User updateUser(User user, User userFromHeader, int id) {
        boolean exists = true;

        if (!user.isAdmin() || !userFromHeader.equals(user)) {
            throw new UnauthorizedOperationException("You do not have permission to update this user");
        }

        try {
            User newUser = userRepository.findByUsername(user.getUsername());
            if (newUser.getId() == user.getId()) {
                exists = false;
            }
        } catch (EntityNotFoundException e) {
            exists = false;
        }

        if (exists) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }

        userRepository.updateUser(user, id);
        return user;
    }

    @Override
    public void deleteUser(int id, User userFromHeader) {
        User user = userRepository.findById(id);

        if (!user.isAdmin() || !userFromHeader.equals(user)) {
            throw new UnauthorizedOperationException("You do not have permission to delete this user");
        }
        userRepository.deleteUser(id);
    }
}
