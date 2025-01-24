//business layer

package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.exceptions.DuplicateEntityException;
import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public User updateUser(User user, int id) {
        boolean exists = true;

        try {
            User newUser = userRepository.findByUsername(user.getUsername());
            if (newUser.getId() == user.getId()) {
                exists = false;
            }
        } catch (EntityNotFoundException e) {
            exists = false;
        }
        throw new EntityNotFoundException("User", "user", user.getUsername());
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id);
        userRepository.deleteUser(id);
    }
}
