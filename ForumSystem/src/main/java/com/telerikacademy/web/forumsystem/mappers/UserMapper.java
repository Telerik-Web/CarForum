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
    public User fromDto(UserDTO userDto, int id) {
        User user = dtoToObjectUpdate(userDto);
        user.setId(id);
        return user;
    }

    //update
    private User dtoToObjectUpdate(UserDTO userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDTOOut fromDtoOut(UserDTO userDto) {
        return dtoOutToObject(userDto);
    }

    private UserDTOOut dtoOutToObject(UserDTO userDto) {
        UserDTOOut user = new UserDTOOut();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public UserDTOOut fromDtoOut(User user) {
        return dtoOutToObject(user);
    }

    private UserDTOOut dtoOutToObject(User user2) {
        UserDTOOut user = new UserDTOOut();
        user.setId(user2.getId());
        user.setFirstName(user2.getFirstName());
        user.setLastName(user2.getLastName());
        user.setUsername(user2.getUsername());
        user.setEmail(user2.getEmail());
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
            userDto.add(userDtoOut);
        }

        return userDto;
    }

    public User fromDto(RegisterDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        return user;
    }

}
