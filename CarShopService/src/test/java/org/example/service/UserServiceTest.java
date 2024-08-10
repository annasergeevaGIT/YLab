package org.example.service;


import org.example.model.User;
import org.example.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.example.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    @DisplayName("Set up UserService and UserRepository Mocks")
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("Test getAllUsers() - Should return all users")
    void testGetAllUsers() {
        User user1 = new User(1, "user1", "password1", UserRole.CUSTOMER,null);
        User user2 = new User(2, "user2", "password2", UserRole.MANAGER,null);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();

        assertThat(users).hasSize(2);
        assertThat(users).contains(user1, user2);
    }

    @Test
    @DisplayName("Test getUserById() - Should return user by ID")
    void testGetUserById() {
        User user = new User(1, "user1", "password1", UserRole.CUSTOMER,null);
        when(userRepository.findById(1)).thenReturn(user);

        User foundUser = userService.getUserById(1);

        assertThat(foundUser).isEqualTo(user);
    }
}
