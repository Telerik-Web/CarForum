//business layer

package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.exceptions.DuplicateEntityException;
import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.models.FilterUserOptions;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.telerikacademy.web.forumsystem.helpers.PermissionHelper.*;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll(FilterUserOptions filterOptions) {
        return userRepository.findAll(filterOptions);
    }

    @Override
    public User findById(User user, int id) {
        checkIfAdmin(user);
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsername(User user, String username) {
        checkIfAdmin(user);
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(User user, String email) {
        checkIfAdmin(user);
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByFirstname(User user, String firstName) {
        checkIfAdmin(user);
        return userRepository.findByFirstname(firstName);
    }

    @Override
    public void createUser(User user) {
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
    }

    @Override
    public void updateUser(User user, User userFromHeader, int id) {
        boolean exists = true;
        checkIfCreatorOrAdminForUser(userFromHeader, user);
        try {
            User newUser = userRepository.findByEmail(user.getEmail());
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
    }

    @Override
    public void deleteUser(int id, User userFromHeader) {
        User user = userRepository.findById(id);
        checkIfCreatorOrAdminForUser(userFromHeader, user);
        userRepository.deleteUser(id);
    }
}
