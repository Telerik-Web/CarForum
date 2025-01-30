package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.exceptions.DuplicateEntityException;
import com.telerikacademy.web.forumsystem.exceptions.EntityNotFoundException;
import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
import com.telerikacademy.web.forumsystem.models.FilterUserOptions;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static com.telerikacademy.web.forumsystem.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getAll_Should_CallRepository(){
        // Arrange
        FilterUserOptions filterOptions = new FilterUserOptions("MockFirstName", "MockLastName",
                "MockUsername", "mockemail@example.com", "email", "desc");

        Mockito.when(userRepository.getAll(filterOptions))
                .thenReturn(createMockUserList());

        // Act
        userService.getAll(filterOptions);

        // Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .getAll(Mockito.any(FilterUserOptions.class));
    }

    @Test
    public void getById_Should_ReturnUser_When_MatchExists(){
        // Arrange
        User mockUser = createMockAdmin();

        Mockito.when(userRepository.getById(1))
                .thenReturn(mockUser);

        // Act
        User result = userService.getById(mockUser, 1);

        // Assert
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("MockUsername", result.getUsername());
    }

    @Test
    public void getById_Should_Throw_When_UserNotFound(){
        // Arrange
        User mockUser = createMockAdmin();

        Mockito.when(userRepository.getById(Mockito.anyInt()))
                .thenThrow(EntityNotFoundException.class);

        // Act, Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getById(mockUser, Mockito.anyInt()));
    }

    @Test
    public void getById_Should_Throw_When_UserNotAnAdmin(){
        // Arrange
        User mockUser = createMockUser();

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> userService.getById(mockUser, 0));
    }

    @Test
    public void getByUsername_Should_ReturnUser_When_MatchExists(){
        // Arrange
        User mockUser = createMockUser();

        Mockito.when(userRepository.getByUsername(mockUser.getUsername()))
                .thenReturn(mockUser);

        // Act
        User result = userService.getByUsername(mockUser.getUsername());

        // Assert
        Assertions.assertEquals(mockUser.getId(), result.getId());
        Assertions.assertEquals(mockUser.getUsername(), result.getUsername());
    }

    @Test
    public void getByUsername_Should_Throw_When_UserNotFound(){
        // Arrange
        Mockito.when(userRepository.getByUsername(Mockito.anyString()))
                .thenThrow(EntityNotFoundException.class);

        // Act, Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getByUsername(Mockito.anyString()));
    }

    @Test
    public void alterAdminPermissions_Should_CallRepository_When_ValidParameters(){
        // Arrange
        User mockUser = createMockUser();
        User mockAdmin = createMockAdmin();

        Mockito.when(userRepository.getById(1))
                .thenReturn(mockUser);

        // Act
        userService.alterAdminPermissions(1, mockAdmin, true);

        // Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .alterAdminPermissions(mockUser);
    }

    @Test
    public void alterAdminPermissions_Should_Throw_When_UserNotAnAdmin(){
        // Arrange
        User mockUser = createMockUser();
        User mockUserToUpdate = createMockUser();
        mockUserToUpdate.setId(2);

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> userService.alterAdminPermissions(1, mockUser, true));
    }

    @Test
    public void alterAdminPermissions_Should_Throw_When_UserBlocked(){
        // Arrange
        User mockUser = createMockBlockedUser();
        User mockUserToUpdate = createMockUser();
        mockUserToUpdate.setId(2);

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> userService.alterAdminPermissions(1, mockUser, true));
    }

    @Test
    public void alterBlock_Should_CallRepository_When_ValidParameters(){
        // Arrange
        User mockAdmin = createMockAdmin();
        User mockUser = createMockUser();

        Mockito.when(userRepository.getById(1))
                .thenReturn(mockUser);

        // Act
        userService.alterBlock(1, mockAdmin, true);

        // Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .alterBlock(mockUser);
    }

    @Test
    public void alterBlock_Should_Throw_When_UserNotAnAdmin(){
        // Arrange
        User mockUser = createMockUser();
        User mockUserToUpdate = createMockUser();
        mockUserToUpdate.setId(2);

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> userService.alterBlock(1, mockUser, true));
    }

    @Test
    public void alterBlock_Should_Throw_When_UserBlocked(){
        // Arrange
        User mockUser = createMockBlockedUser();
        User mockUserToUpdate = createMockUser();
        mockUserToUpdate.setId(2);

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> userService.alterBlock(1, mockUser, true));
    }

    @Test
    public void create_Should_CallRepository_When_ValidParameters(){
        // Arrange
        User mockUser = createMockUser();
        Mockito.when(userRepository.getByEmail(Mockito.anyString()))
                .thenThrow(EntityNotFoundException.class);

        // Act
        userService.create(mockUser);

        // Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .create(mockUser);
    }

    @Test
    public void create_Should_Throw_When_EmailExists(){
        // Arrange
        User mockUser = createMockUser();
        Mockito.when(userRepository.getByEmail(Mockito.anyString()))
                .thenReturn(mockUser);


        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> userService.create(mockUser));
    }

    @Test
    public void update_Should_CallRepository_When_ValidParameters(){
        // Arrange
        User mockUser = createMockUser();
        User mockUserToUpdate = createMockUser();

        Mockito.when(userRepository.getByEmail(Mockito.anyString()))
                .thenThrow(EntityNotFoundException.class);

        Mockito.when(userRepository.getById(Mockito.anyInt()))
                .thenReturn(mockUserToUpdate);

        // Act
        userService.update(mockUser, mockUserToUpdate, 1);

        // Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .update(mockUser, 1);
    }

    @Test
    public void update_Should_Throw_When_UsernameExists(){
        // Arrange
        User mockUser = createMockUser();
        User anotherUser = createMockUser();


        Mockito.when(userRepository.getByEmail(Mockito.anyString()))
                .thenReturn(mockUser);

        Mockito.when(userRepository.getById(Mockito.anyInt()))
                .thenReturn(mockUser);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> userService.update(mockUser, anotherUser, 1));
    }

    @Test
    public void delete_Should_CallRepository_When_ValidParameters(){
        // Arrange
        User mockUser = createMockUser();

        Mockito.when(userRepository.getById(Mockito.anyInt()))
                .thenReturn(mockUser);

        // Act
        userService.delete(Mockito.anyInt(), mockUser);

        // Assert
        Mockito.verify(userRepository, Mockito.times(1))
                .delete(Mockito.anyInt());
    }

    @Test
    public void delete_Should_Throw_When_UserNotCreatorOrAdmin(){
        // Arrange
        User mockUser = createMockUser();
        User anotherUser = createMockUser();
        anotherUser.setId(2);

        Mockito.when(userRepository.getById(Mockito.anyInt()))
                .thenReturn(mockUser);

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> userService.delete(Mockito.anyInt(), anotherUser));
    }


}
