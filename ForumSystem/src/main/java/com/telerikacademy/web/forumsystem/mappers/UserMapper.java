package com.telerikacademy.web.forumsystem.mappers;

import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.models.UserDTO;
import com.telerikacademy.web.forumsystem.models.UserDTOOut;
import com.telerikacademy.web.forumsystem.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //create
    public User fromDto(UserDTO userDto) {
        return dtoToObject(userDto);
    }

    public UserDTOOut fromDtoOut(UserDTO userDto) {
        return dtoOutToObject(userDto);
    }

    //update
    public User fromDto(UserDTO userDto, int id) {
        User user = dtoToObject(userDto);
        user.setId(id);
        return user;
    }

    private User dtoToObject(UserDTO userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAdmin(userDto.isAdmin());
        user.setBlocked(userDto.isBlocked());
        return user;
    }

    private UserDTOOut dtoOutToObject(UserDTO userDto) {
        UserDTOOut user = new UserDTOOut();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setAdmin(userDto.isAdmin());
        user.setBlocked(userDto.isBlocked());
        return user;
    }


}
