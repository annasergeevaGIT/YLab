package service;

import model.UserRole;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        authService = new AuthService(userRepository);
    }

    @Test
    public void registerUser_success() {
        User user = new User("john_doe", "password", UserRole.CUSTOMER);
        when(userRepository.addUser(user)).thenReturn(true);

        boolean result = authService.registerUser("john_doe", "password", UserRole.CUSTOMER);

        assertThat(result).isTrue();
        verify(userRepository, times(1)).addUser(user);
    }

    @Test
    public void loginUser_success() {
        User user = new User("john_doe", "password", UserRole.CUSTOMER);
        when(userRepository.getUserByUsername("john_doe")).thenReturn(user);

        User result = authService.login("john_doe", "password");

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("john_doe");
    }
}

