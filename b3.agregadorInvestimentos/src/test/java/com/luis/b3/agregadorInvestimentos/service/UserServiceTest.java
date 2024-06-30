package com.luis.b3.agregadorInvestimentos.service;

import com.luis.b3.agregadorInvestimentos.controller.dto.CreateUserDto;
import com.luis.b3.agregadorInvestimentos.domain.User;
import com.luis.b3.agregadorInvestimentos.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;


    @Nested
    class createUser {

        @Test
        void shouldCreateAUserWithSuccess() {
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "password",
                    "email@email.com",
                    Instant.now(),
                    null
            );
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "password"
            );
            var output = userService.createUser(input);
            assertNotNull(output);
            assertEquals(input.getUsername(), userArgumentCaptor.getValue().getUsername());
            assertEquals(input.getPassword(), userArgumentCaptor.getValue().getPassword());
            assertEquals(input.getEmail(), userArgumentCaptor.getValue().getEmail());

        }

        @Test
        void shouldThrowExceptionWhenErrorOccurs() {
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "password"
            );
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

    @Nested
    class getUserById {
        @Test
        void shouldGetUserByIdWithSuccessWhenOptinalIisPresent() {
            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "password",
                    "email@email.com",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());
            //Act
            var output = userService.getUserById(user.getId().toString());
            //Assert
            assertTrue(output.isPresent());
            assertEquals(user.getId(), uuidArgumentCaptor.getValue());
        }

        @Test
        void shouldGetUserByIdWithSuccessWhenOptinalIisEmpty() {
            //Arrange
            var userId =  UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());
            //Act
            var output = userService.getUserById(userId.toString());
            //Assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class listUsers {

        @Test
        void shouldReturnAllUsersWithSucess() {
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "password",
                    "email@email.com",
                    Instant.now(),
                    null
            );

            var userList =  List.of(user);
            doReturn(userList)
                    .when(userRepository)
                    .findAll();
            var output = userService.listUsers();
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }


}