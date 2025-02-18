//presentation layer

package com.telerikacademy.web.forumsystem.controllers.rest;

import com.telerikacademy.web.forumsystem.exceptions.DuplicateEntityException;
import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
import com.telerikacademy.web.forumsystem.helpers.AuthenticationHelper;
import com.telerikacademy.web.forumsystem.mappers.PhoneNumberMapper;
import com.telerikacademy.web.forumsystem.mappers.UserMapper;
import com.telerikacademy.web.forumsystem.models.*;
import com.telerikacademy.web.forumsystem.services.PhoneNumberService;
import com.telerikacademy.web.forumsystem.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @GetMapping("/{userId}")
    @SecurityRequirement(name = "authHeader")
    public User getById(@RequestHeader HttpHeaders headers, @PathVariable int userId) {
        try {
            User user = authorizationHelper.tryGetUser(headers);
            return userService.getById(user, userId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

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
    @PutMapping("/{userId}")
    @SecurityRequirement(name = "authHeader")
    public UserDTOOut update(@RequestHeader HttpHeaders headers, @PathVariable int userId,
                             @RequestBody UserDTO userDto) {
        try {
            User userFromHeader = authorizationHelper.tryGetUser(headers);
            User user = userMapper.fromDto(userDto, userId);
            user.setUsername(getById(headers, userId).getUsername());
            userService.update(user, userFromHeader, userId);
            return userMapper.fromDtoOut(getById(headers, userId));
            //return userDto;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Operation(summary = "Alter admin permissions", description = "Changes user permissions " +
            "if isAdmin is true, promotes the user to admin, else removes admin permissions")
    @PatchMapping("/{userId}/admin")
    @SecurityRequirement(name = "authHeader")
    public void alterAdminPermissions(@PathVariable int userId, @RequestHeader HttpHeaders headers,
                                      @RequestParam boolean isAdmin) {
        try {
            User user = authorizationHelper.tryGetUser(headers);
            userService.alterAdminPermissions(userId, user, isAdmin);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Operation(summary = "Block or unblock user", description =
            "If isBlocked is true, blocks user from interaction, else unblocks an user")
    @PatchMapping("/{userId}/block")
    @SecurityRequirement(name = "authHeader")
    public void alterBlock(@PathVariable int userId, @RequestHeader HttpHeaders headers, @RequestParam boolean isBlocked) {
        try {
            User user = authorizationHelper.tryGetUser(headers);
            userService.alterBlock(userId, user, isBlocked);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Operation(summary = "Deletes an user by Id", description = "Deletes an user by their unique ID")
    @DeleteMapping("/{userId}")
    @SecurityRequirement(name = "authHeader")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int userId) {
        try {
            User userFromHeader = authorizationHelper.tryGetUser(headers);
            userService.delete(userId, userFromHeader);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/phone")
    @SecurityRequirement(name = "authHeader")
    public PhoneNumber getByUser(@RequestHeader HttpHeaders headers) {
        try{
            User user = authorizationHelper.tryGetUser(headers);
            return phoneNumberService.getByUser(user);
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch(UnauthorizedOperationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    @Operation(summary = "Create a PhoneNumber", description = "Creates a phoneNumber, for a user, while checking " +
            "if the creator is an admin and if the user the phone is assigned to is an admin")
    @PostMapping("/phone/{userId}")
    @SecurityRequirement(name = "authHeader")
    public PhoneNumber createPhoneNumber(@RequestHeader HttpHeaders headers, @PathVariable int userId,
                                         @RequestBody PhoneNumberDTO phoneNumberDto) {
        try {
            PhoneNumber phoneNumber = phoneNumberMapper.map(phoneNumberDto);
            User user = authorizationHelper.tryGetUser(headers);
            User userToAddPhoneNumber = userService.getById(user, userId);
            phoneNumberService.create(phoneNumber, user, userToAddPhoneNumber);
            return phoneNumber;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Operation(summary = "Update a PhoneNumber", description = "Updates a phone number for a user, " +
            "while checking if the user is an admin.")
    @PutMapping("/phone/{userId}")
    @SecurityRequirement(name = "authHeader")
    public PhoneNumber updatePhoneNumber(@RequestHeader HttpHeaders headers, @PathVariable int userId,
                                         @RequestBody PhoneNumberDTO phoneNumberDto) {
        try {
            PhoneNumber phoneNumber = phoneNumberMapper.map(phoneNumberDto);
            User user = authorizationHelper.tryGetUser(headers);
            User userToUpdatePhoneNumber = userService.getById(user, userId);
            PhoneNumber existingPhoneNumber = phoneNumberService.getByUser(userToUpdatePhoneNumber);
            phoneNumberService.update(existingPhoneNumber, phoneNumber, user, userToUpdatePhoneNumber);
            return phoneNumber;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Operation(summary = "Delete a PhoneNumber", description = "Deletes a phone number for a user, while " +
            "checking if the user is an admin.")
    @DeleteMapping("/phone/{userId}")
    @SecurityRequirement(name = "authHeader")
    public void deletePhoneNumber(@RequestHeader HttpHeaders headers, @PathVariable int userId) {
        try {
            User user = authorizationHelper.tryGetUser(headers);
            User userToDeletePhoneNumber = userService.getById(user, userId);
            phoneNumberService.delete(user, userToDeletePhoneNumber);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}