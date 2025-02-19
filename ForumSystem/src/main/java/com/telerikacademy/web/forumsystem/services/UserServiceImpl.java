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
    public long getUserCount() {
        return userRepository.getUserCount();
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

    @Override
    public void alterAdminPermissions(int id, User user, boolean isAdmin) {
        checkIfAdmin(user);
        checkIfBlocked(user);

        User userToUpdate = userRepository.getById(id);

        if (isAdmin) {
            userToUpdate.setAdmin(true);
        }
        if (!isAdmin) {
            userToUpdate.setAdmin(false);
        }
        userRepository.alterAdminPermissions(userToUpdate);
    }

    @Override
    public void alterBlock(int id, User user, boolean isBlocked) {
        checkIfAdmin(user);
        checkIfBlocked(user);

        User userToUpdate = userRepository.getById(id);

        if (isBlocked) {
            userToUpdate.setBlocked(true);
        }
        if (!isBlocked) {
            userToUpdate.setBlocked(false);
        }
        userRepository.alterBlock(userToUpdate);
    }

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

        userRepository.create(user);
    }

    @Override
    public void update(User user, User userFromHeader, int id) {
        boolean exists = true;
        checkIfCreatorOrAdminForUser(userFromHeader, user);
        try {
            userRepository.getByEmail(user.getEmail());
        } catch (EntityNotFoundException e) {
            exists = false;
        }

        User user2 = userRepository.getById(id);
        user.setId(user2.getId());
        user.setUsername(user2.getUsername());
        user.setAdmin(user2.isAdmin());
        user.setEmail(user2.getEmail());
        user.setBlocked(user2.isBlocked());
        if (user2.getPhoneNumber() != null) {
            user.setPhoneNumber(user2.getPhoneNumber());
        }

        if (exists) {
            throw new DuplicateEntityException("User", "email", user.getEmail());
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
