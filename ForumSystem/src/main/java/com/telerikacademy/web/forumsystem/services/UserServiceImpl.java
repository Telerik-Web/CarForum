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
    public List<User> getAll(FilterUserOptions filterOptions) {
        return userRepository.getAll(filterOptions);
    }

    @Override
    public User getById(User user, int id) {
        checkIfAdmin(user);
        return userRepository.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

//    @Override
//    public User findByUsername(User user, String username) {
//        checkIfAdmin(user);
//        return userRepository.findByUsername(username);
//    }
//
//    @Override
//    public User findByEmail(User user, String email) {
//        checkIfAdmin(user);
//        return userRepository.findByEmail(email);
//    }
//
//    @Override
//    public User findByFirstname(User user, String firstName) {
//        checkIfAdmin(user);
//        return userRepository.findByFirstname(firstName);
//    }

    @Override
    public void create(User user) {
        boolean exists = true;

        try {
            userRepository.getByEmail(user.getEmail());
        } catch (EntityNotFoundException e) {
            exists = false;
        }

        if (exists) {
            throw new DuplicateEntityException("User", "email", user.getEmail());
        }
//        user.setBlocked(false);
//        user.setAdmin(false);

        userRepository.create(user);
    }

    @Override
    public void update(User user, User userFromHeader, int id) {
        boolean exists = true;
        checkIfCreatorOrAdminForUser(userFromHeader, user);
        try {
            User newUser = userRepository.getByEmail(user.getEmail());
            if (newUser.getId() == user.getId()) {
                exists = false;
            }
        } catch (EntityNotFoundException e) {
            exists = false;
        }

        if (exists) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }

        userRepository.update(user, id);
    }

    @Override
    public void delete(int id, User userFromHeader) {
        User user = userRepository.getById(id);
        checkIfCreatorOrAdminForUser(userFromHeader, user);
        userRepository.delete(id);
    }
}
