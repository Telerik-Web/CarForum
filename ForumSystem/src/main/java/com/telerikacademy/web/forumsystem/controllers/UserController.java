//presentation layer

package com.telerikacademy.web.forumsystem.controllers;

import com.telerikacademy.web.forumsystem.exceptions.DuplicateEntityException;
import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
import com.telerikacademy.web.forumsystem.mappers.PhoneNumberMapper;
import com.telerikacademy.web.forumsystem.mappers.UserMapper;
import com.telerikacademy.web.forumsystem.models.*;
import com.telerikacademy.web.forumsystem.services.PhoneNumberService;
import com.telerikacademy.web.forumsystem.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "APIs for managing users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationHelper authorizationHelper;
    private final PhoneNumberService phoneNumberService;
    private final PhoneNumberMapper phoneNumberMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, AuthenticationHelper authenticationHelper, PhoneNumberService phoneNumberService, PhoneNumberMapper phoneNumberMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authorizationHelper = authenticationHelper;
        this.phoneNumberService = phoneNumberService;
        this.phoneNumberMapper = phoneNumberMapper;
    }

    @Operation(summary = "Returns all users", description = "Returns all users with their proper fields.")
    @GetMapping
    public List<UserDTOOut> getAll(@RequestParam(required = false) String firstName,
                                   @RequestParam(required = false) String lastName,
                                   @RequestParam(required = false) String username,
                                   @RequestParam(required = false) String email,
                                   @RequestParam(required = false) String sortBy,
                                   @RequestParam(required = false) String orderBy) {
        FilterUserOptions filterOptions = new FilterUserOptions(firstName, lastName,
                username, email, sortBy, orderBy);
        return userMapper.toDTOOut(userService.getAll(filterOptions));
    }

    @Operation(summary = "Get user by ID", description = "Fetches a user by their unique ID")
    @GetMapping("/{id}")
    public User getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authorizationHelper.tryGetUser(headers);
            return userService.getById(user, id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

//    @GetMapping("/search/username")
//    public User getUsersByUsername(@RequestHeader HttpHeaders headers, @RequestParam String username) {
//        try {
//            User user = authorizationHelper.tryGetUser(headers);
//            return userService.findByUsername(user, username);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        } catch (UnauthorizedOperationException e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
//        }
//    }
//
//    @GetMapping("/search/email")
//    public User getUsersByEmail(@RequestHeader HttpHeaders headers, @RequestParam String email) {
//        try {
//            User user = authorizationHelper.tryGetUser(headers);
//            return userService.findByEmail(user, email);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        } catch (UnauthorizedOperationException e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
//        }
//    }
//
//    @GetMapping("/search/firstName")
//    public User getUsersByFirstname(@RequestHeader HttpHeaders headers, @RequestParam String firstName) {
//        try {
//            User user = authorizationHelper.tryGetUser(headers);
//            return userService.findByFirstname(user, firstName);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        } catch (UnauthorizedOperationException e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
//        }
//    }

//    @GetMapping("/search")
//    public User searchUser(@RequestParam String type, @RequestParam String value) {
//        try {
//            switch (type) {
//                case "username":
//                    return userService.findByUsername(value);
//                case "email":
//                    return userService.findByEmail(value);
//                case "firstName":
//                    return userService.findByFirstname(value);
//                default:
//                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, type);
//            }
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
//    }

    @Operation(summary = "Create a User", description = "Create a user with unique all its fields.")
    @PostMapping
    public UserDTOOut create(@RequestBody UserDTO userDto) {
        try {
            User user = userMapper.fromDto(userDto);
            userService.create(user);
            return userMapper.fromDtoOut(userDto);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Operation(summary = "Updates user by an Id", description = "Updates the desired fields in an User")
    @PutMapping("/{id}")
    public UserDTOOut update(@RequestHeader HttpHeaders headers, @PathVariable int id,
                             @RequestBody UserDTO userDto) {
        try {
            User userFromHeader = authorizationHelper.tryGetUser(headers);
            User user = userMapper.fromDto(userDto, id);
            user.setUsername(getById(headers, id).getUsername());
            userService.update(user, userFromHeader, id);
            return userMapper.fromDtoOut(userDto);
            //return userDto;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

//    @PutMapping("/{id}/admin")
//    public User updateUser(/*@RequestHeader HttpHeaders headers,*/ @PathVariable int id, @RequestBody UserDTO userDto) {
//        try {
//            User userFromHeader = authorizationHelper.tryGetUser(headers);
//            User user = userMapper.fromDto(userDto);
//            userService.updateUser(user, userFromHeader, id);
//            return user;
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        } catch (DuplicateEntityException e) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
//        } catch (UnauthorizedOperationException e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
//        }
//    }

    @Operation(summary = "Alter admin permissions", description = "Changes user permissions " +
            "if isAdmin is true, promotes the user to admin, else removes admin permissions")
    @PatchMapping("/{id}/admin")
    public void alterAdminPermissions(@PathVariable int id, @RequestHeader HttpHeaders headers, @RequestParam boolean isAdmin) {
        try {
            User user = authorizationHelper.tryGetUser(headers);
            userService.alterAdminPermissions(id, user, isAdmin);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Operation(summary = "Block or unblock user", description =
            "If isBlocked is true, blocks user from interaction, else unblocks an user")
    @PatchMapping("/{id}/block")
    public void alterBlock(@PathVariable int id, @RequestHeader HttpHeaders headers, @RequestParam boolean isBlocked) {
        try {
            User user = authorizationHelper.tryGetUser(headers);
            userService.alterBlock(id, user, isBlocked);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Operation(summary = "Deletes an user by Id", description = "Deletes an user by their unique ID")
    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User userFromHeader = authorizationHelper.tryGetUser(headers);
            userService.delete(id, userFromHeader);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    @Operation(summary = "Create a PhoneNumber", description = "Creates a phoneNumber, for a user, while checking " +
            "if the creator is an admin and if the user the phone is assigned to is an admin")
    @PostMapping("/{id}")
    public PhoneNumber createPhoneNumber(@RequestHeader HttpHeaders headers, @PathVariable int id,
                                         @RequestBody PhoneNumberDTO phoneNumberDto) {
        try {
            PhoneNumber phoneNumber = phoneNumberMapper.map(phoneNumberDto);
            phoneNumber.setCreatedBy(authorizationHelper.tryGetUser(headers));
            User user = authorizationHelper.tryGetUser(headers);
            User userToAddPhoneNumber = userService.getById(user, id);
            phoneNumberService.create(phoneNumber, user, userToAddPhoneNumber);
            return phoneNumber;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (UnsupportedOperationException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
    }
}