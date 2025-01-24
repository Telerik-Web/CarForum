package com.telerikacademy.web.forumsystem.mappers;

import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.models.UserDto;
import com.telerikacademy.web.forumsystem.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //create
    public User fromDto(UserDto userDto) {
        return dtoToObject(userDto);
    }

    //update
    public User fromDto(UserDto userDto, int id) {
        User user = dtoToObject(userDto);
        user.setId(id);
        return user;
    }

    private User dtoToObject(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAdmin(userDto.isAdmin());
        user.setBlocked(userDto.isBlocked());
        return user;
    }
}
