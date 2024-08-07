package org.example.service;

import org.example.model.UserRole;
import org.example.model.User;
import org.example.service.AuditService;
import org.example.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private AuditService auditService;
    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        authService = new AuthService(userRepository, auditService);
    }

    @Test
    void testRegister() {
        User user = new User(0, "testUser", "password", UserRole.CUSTOMER);
        authService.register("testUser", "password");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testLogin() {
        User user = new User(1, "testUser", "password", UserRole.CUSTOMER);
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        User result = authService.login("testUser", "password");

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testUser");
    }

    @Test
    void testLoginWithInvalidCredentials() {
        User user = new User(1, "testUser", "password", UserRole.CUSTOMER);
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        User result = authService.login("testUser", "wrongPassword");

        assertThat(result).isNull();
    }

    @Test
    void testLoginWithNonExistentUser() {
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(null);
        User result = authService.login("nonExistentUser", "password");
        assertThat(result).isNull();
    }
}
