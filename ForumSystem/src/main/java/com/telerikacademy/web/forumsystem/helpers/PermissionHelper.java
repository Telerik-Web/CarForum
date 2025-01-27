package com.telerikacademy.web.forumsystem.helpers;

import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
import com.telerikacademy.web.forumsystem.models.Post;
import com.telerikacademy.web.forumsystem.models.User;

public class PermissionHelper {
    private static final String AUTHORIZATION_PERMISSION_ERROR = "You don't have the permission to do this.";
    private static final String BLOCKED_USER_ERROR = "You are blocked and cannot perform that operation";

//    public static void checkIfCreator(User user, User userToUpdate) {
//        if (!(user.getId() == userToUpdate.getId())) {
//            throw new UnauthorizedOperationException(AUTHORIZATION_PERMISSION_ERROR);
//        }
//    }
//
    public static void checkIfAdmin(User user) {
        if (!user.isAdmin()) {
            throw new UnauthorizedOperationException(AUTHORIZATION_PERMISSION_ERROR);
        }
    }

    public static void checkIfCreatorOrAdminForUser(User user, User userToUpdate) {
        if (!(user.getId() == userToUpdate.getId()) || !user.isAdmin()) {
            throw new UnauthorizedOperationException(AUTHORIZATION_PERMISSION_ERROR);
        }
    }

    public static void checkIfCreatorOrAdmin(User user, Post post) {
        if (!(user.equals(post.getCreatedBy()) || user.isAdmin())) {
            throw new UnauthorizedOperationException(AUTHORIZATION_PERMISSION_ERROR);
        }
    }

    public static void checkIfCreatorOrAdmin(int userId, User user) {
        if (!(userId == user.getId() || user.isAdmin())) {
            throw new UnauthorizedOperationException(AUTHORIZATION_PERMISSION_ERROR);
        }
    }

    public static void checkIfBlocked(User user) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException(BLOCKED_USER_ERROR);
        }
    }
}
