package com.telerikacademy.web.forumsystem.mappers;

import com.telerikacademy.web.forumsystem.models.*;
import com.telerikacademy.web.forumsystem.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        User user = dtoToObjectUpdate(userDto);
        user.setId(id);
        return user;
    }

    //create
    private User dtoToObject(UserDTO userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAdmin(false);
        user.setBlocked(false);
        return user;
    }

    //update
    private User dtoToObjectUpdate(UserDTO userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    private UserDTOOut dtoOutToObject(UserDTO userDto) {
        UserDTOOut user = new UserDTOOut();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
//        user.setAdmin(userDto.isAdmin());
//        user.setBlocked(userDto.isBlocked());
        return user;
    }

    public List<UserDTOOut> toDTOOut(List<User> userList){
        List<UserDTOOut> userDto = new ArrayList<>();
        for (User user : userList){
            UserDTOOut userDtoOut = new UserDTOOut();
            userDtoOut.setId(user.getId());
            userDtoOut.setFirstName(user.getFirstName());
            userDtoOut.setLastName(user.getLastName());
            userDtoOut.setUsername(user.getUsername());
            userDtoOut.setEmail(user.getEmail());
//            userDtoOut.setBlocked(user.isBlocked());
//            userDtoOut.setAdmin(user.isAdmin());
            userDto.add(userDtoOut);
        }

        return userDto;
    }

}
