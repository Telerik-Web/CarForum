//business layer

package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.exceptions.DuplicateEntityException;
import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
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
    public User findById(User user, int id) {
        if (!user.isAdmin()) {
            throw new UnauthorizedOperationException("You do not have permission to access this resource");
        }
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsername(User user, String username) {
        if (!user.isAdmin()) {
            throw new UnauthorizedOperationException("You do not have permission to access this resource");
        }
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(User user, String email) {
        if (!user.isAdmin()) {
            throw new UnauthorizedOperationException("You do not have permission to access this resource");
        }
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByFirstname(User user, String firstName) {
        if (!user.isAdmin()) {
            throw new UnauthorizedOperationException("You do not have permission to access this resource");
        }
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
