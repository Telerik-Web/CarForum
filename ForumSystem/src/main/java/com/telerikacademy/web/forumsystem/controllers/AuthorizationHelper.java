package com.telerikacademy.web.forumsystem.controllers;

import com.telerikacademy.web.forumsystem.exceptions.AuthorizationException;
import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthorizationHelper {

    private static final String AUTHORIZATION = "Authorization";
    public static final String AUTHENTICATION_ERROR = "The requested resource requires authentication.";
    public static final String INVALID_AUTHENTICATION_ERROR = "Invalid authentication";

    private final UserService userService;

    @Autowired
    public AuthorizationHelper(UserService userService) {
        this.userService = userService;
    }

    public User tryGetUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION)) {
            throw new AuthorizationException(AUTHENTICATION_ERROR);
        }

        String userInfo = headers.getFirst(AUTHORIZATION);
        String username = getUsername(userInfo);
        String password = getPassword(userInfo);

        try {

            User user = userService.findByUsername(username);

            if(!user.getPassword().equals(password)){
                throw new UnauthorizedOperationException(INVALID_AUTHENTICATION_ERROR);
            }

            return user;

        } catch (EntityNotFoundException e) {
            throw new UnauthorizedOperationException(INVALID_AUTHENTICATION_ERROR);
        }
    }

    private String getUsername(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");

        if (firstSpace == -1) {
            throw new AuthorizationException(AUTHENTICATION_ERROR);
        }
        return userInfo.substring(0, firstSpace);

    }

    private String getPassword(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");

        if (firstSpace == -1) {
            throw new AuthorizationException(AUTHENTICATION_ERROR);
        }
        return userInfo.substring(firstSpace + 1);

    }
}
