package com.telerikacademy.web.forumsystem.services;

import com.telerikacademy.web.forumsystem.exceptions.DuplicateEntityException;
import com.telerikacademy.web.forumsystem.exceptions.UnauthorizedOperationException;
import com.telerikacademy.web.forumsystem.models.PhoneNumber;
import com.telerikacademy.web.forumsystem.models.User;
import com.telerikacademy.web.forumsystem.repositories.PhoneNumberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.telerikacademy.web.forumsystem.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberServiceTests {

    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    @InjectMocks
    private PhoneNumberServiceImpl phoneNumberService;

    @Test
    public void getByUser_Should_CallRepository_When_UserIsAdmin(){
        // Arrange
        PhoneNumber mockPhoneNumber = createMockPhoneNumber();
        User mockUser = createMockAdmin();

        Mockito.when(phoneNumberRepository.getByUser(mockUser))
                .thenReturn(mockPhoneNumber);

        // Act
        phoneNumberService.getByUser(mockUser);

        // Assert
        Mockito.verify(phoneNumberRepository, Mockito.times(1))
                .getByUser(mockUser);
    }

    @Test
    public void getByUser_Should_Throw_When_UserNotAnAdmin(){
        // Arrange
        User mockUser = createMockUser();

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> phoneNumberService.getByUser(mockUser));
    }

    @Test
    public void create_Should_CallRepository_When_UserIsAdmin(){
        // Arrange
        User mockUser = createMockAdmin();
        User anotherUser = createMockAdmin();
        anotherUser.setId(2);
        PhoneNumber mockPhoneNumber = createMockPhoneNumber();

        // Act
        phoneNumberService.create(mockPhoneNumber, mockUser, anotherUser);

        // Assert
        Mockito.verify(phoneNumberRepository, Mockito.times(1))
                .create(mockPhoneNumber);
    }

    @Test
    public void create_Should_Throw_When_UserNotAnAdmin(){
        // Arrange
        User mockUser = createMockUser();
        User anotherUser = createMockAdmin();
        anotherUser.setId(2);
        PhoneNumber mockPhoneNumber = createMockPhoneNumber();

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> phoneNumberService.create(mockPhoneNumber, mockUser, anotherUser));
    }

    @Test
    public void create_Should_Throw_When_NumberOwnerNotAnAdmin(){
        // Arrange
        User mockUser = createMockAdmin();
        User anotherUser = createMockUser();
        anotherUser.setId(2);
        PhoneNumber mockPhoneNumber = createMockPhoneNumber();

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> phoneNumberService.create(mockPhoneNumber, mockUser, anotherUser));
    }

    @Test
    public void create_Should_Throw_When_UserAlreadyHasAPhoneNumber(){
        // Arrange
        User mockUser = createMockAdmin();
        User anotherUser = createMockAdmin();
        anotherUser.setId(2);
        PhoneNumber mockPhoneNumber = createMockPhoneNumber();
        anotherUser.setPhoneNumber(mockPhoneNumber);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> phoneNumberService.create(mockPhoneNumber, mockUser, anotherUser));
    }

    @Test
    public void update_Should_CallRepository_When_UserIsAdmin(){
        // Arrange

        PhoneNumber mockPhoneNumber = createMockPhoneNumber();
        PhoneNumber anotherPhoneNumber = createMockPhoneNumber();
        User mockUser = createMockAdmin();
        User anotherUser = createMockAdmin();
        anotherUser.setId(2);
        anotherUser.setPhoneNumber(anotherPhoneNumber);

        // Act
        phoneNumberService.update(mockPhoneNumber, anotherPhoneNumber, mockUser, anotherUser);

        // Assert
        Mockito.verify(phoneNumberRepository, Mockito.times(1))
                .update(anotherPhoneNumber);
    }

    @Test
    public void update_Should_Throw_When_UserNotAnAdmin(){
        // Arrange
        User mockUser = createMockUser();
        User anotherUser = createMockAdmin();
        anotherUser.setId(2);
        PhoneNumber mockPhoneNumber = createMockPhoneNumber();

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> phoneNumberService.update(mockPhoneNumber, mockPhoneNumber , mockUser, anotherUser));
    }

    @Test
    public void update_Should_Throw_When_NumberOwnerNotAnAdmin(){
        // Arrange
        User mockUser = createMockAdmin();
        User anotherUser = createMockUser();
        anotherUser.setId(2);
        PhoneNumber mockPhoneNumber = createMockPhoneNumber();

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> phoneNumberService.update(mockPhoneNumber, mockPhoneNumber , mockUser, anotherUser));
    }

    @Test
    public void delete_Should_CallRepository_When_UserIsAdmin(){
        // Arrange
        PhoneNumber mockPhoneNumber = createMockPhoneNumber();
        User mockUser = createMockAdmin();
        User anotherUser = createMockAdmin();
        anotherUser.setId(2);
        anotherUser.setPhoneNumber(mockPhoneNumber);

        Mockito.when(phoneNumberRepository.getByUser(anotherUser))
                .thenReturn(mockPhoneNumber);

        // Act
        phoneNumberService.delete(mockUser, anotherUser);

        // Assert
        Mockito.verify(phoneNumberRepository, Mockito.times(1))
                .delete(mockPhoneNumber);
    }

    @Test
    public void delete_Should_Throw_When_UserNotAnAdmin(){
        // Arrange
        User mockUser = createMockUser();
        User anotherUser = createMockAdmin();
        anotherUser.setId(2);

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> phoneNumberService.delete(mockUser, anotherUser));
    }
}
