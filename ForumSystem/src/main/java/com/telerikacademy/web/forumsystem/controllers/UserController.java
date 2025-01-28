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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
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

    @GetMapping
    public List<UserDTOOut> getAllUsers(@RequestParam(required = false) String firstName,
                                  @RequestParam(required = false) String lastName,
                                  @RequestParam(required = false) String username,
                                  @RequestParam(required = false) String sortBy,
                                  @RequestParam(required = false) String orderBy) {
        FilterUserOptions filterOptions = new FilterUserOptions(firstName, lastName, username, sortBy, orderBy);
        return userMapper.toDTOOut(userService.findAll(filterOptions));
    }

    @GetMapping("/{id}")
    public User getUserById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authorizationHelper.tryGetUser(headers);
            return userService.findById(user, id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/search/username")
    public User getUsersByUsername(@RequestHeader HttpHeaders headers, @RequestParam String username) {
        try {
            User user = authorizationHelper.tryGetUser(headers);
            return userService.findByUsername(user, username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/search/email")
    public User getUsersByEmail(@RequestHeader HttpHeaders headers, @RequestParam String email) {
        try {
            User user = authorizationHelper.tryGetUser(headers);
            return userService.findByEmail(user, email);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/search/firstName")
    public User getUsersByFirstname(@RequestHeader HttpHeaders headers, @RequestParam String firstName) {
        try {
            User user = authorizationHelper.tryGetUser(headers);
            return userService.findByFirstname(user, firstName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

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

    @PostMapping
    public UserDTOOut createUser(@RequestBody UserDTO userDto) {
        try {
            User user = userMapper.fromDto(userDto);
            userService.createUser(user);
            return userMapper.fromDtoOut(userDto);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/{id}")
    public PhoneNumber createPhoneNumber(@RequestHeader HttpHeaders headers, @PathVariable int id,
                                         @RequestBody PhoneNumberDTO phoneNumberDto) {
        try {
            PhoneNumber phoneNumber = phoneNumberMapper.map(phoneNumberDto);
            User user = authorizationHelper.tryGetUser(headers);
            User userToAddPhoneNumber = userService.findById(user, id);
            phoneNumberService.create(phoneNumber, user, userToAddPhoneNumber);
            return phoneNumber;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public UserDTOOut updateUser(@RequestHeader HttpHeaders headers, @PathVariable int id,
                                 @RequestBody UserDTO userDto) {
        try {
            User userFromHeader = authorizationHelper.tryGetUser(headers);
            User user = userMapper.fromDto(userDto, id);
            userService.updateUser(user, userFromHeader, id);
            return userMapper.fromDtoOut(userDto);
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

    @DeleteMapping("/{id}")
    public void deleteUser(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User userFromHeader = authorizationHelper.tryGetUser(headers);
            userService.deleteUser(id, userFromHeader);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}