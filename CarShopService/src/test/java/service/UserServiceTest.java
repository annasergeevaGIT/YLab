package service;


import model.User;
import model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User(1, "user1", "password1", UserRole.CUSTOMER);
        User user2 = new User(2, "user2", "password2", UserRole.MANAGER);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();

        assertThat(users).hasSize(2);
        assertThat(users).contains(user1, user2);
    }

    @Test
    void testGetUserById() {
        User user = new User(1, "user1", "password1", UserRole.CUSTOMER);
        when(userRepository.findById(1)).thenReturn(user);

        User foundUser = userService.getUserById(1);

        assertThat(foundUser).isEqualTo(user);
    }
}
