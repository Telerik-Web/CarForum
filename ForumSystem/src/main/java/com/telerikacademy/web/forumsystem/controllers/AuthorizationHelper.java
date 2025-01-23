package com.telerikacademy.web.forumsystem.controllers;

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
    private final UserService userService;

    @Autowired
    public AuthorizationHelper(UserService userService) {
        this.userService = userService;
    }

    public User tryGetUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION)) {
            //throw new UnauthorizedOperationException("You are not authorized to perform this action");
        }

        String username = headers.getFirst(AUTHORIZATION);

        try {
            return userService.findByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
