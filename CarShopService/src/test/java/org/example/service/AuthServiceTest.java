package org.example.service;

import org.example.model.UserRole;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("AuthService Tests")
public class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private AuditService auditService;

    @BeforeEach
    @DisplayName("Set up AuthService and repository mocks")
    void setUp() {
        userRepository = mock(UserRepository.class);
        auditService = mock(AuditService.class);
        authService = new AuthService(userRepository, auditService);
    }

    @Test
    @DisplayName("Test register() - Should register a new user and log the action")
    void testRegister() {
        when(userRepository.findByUsername("testUser")).thenReturn(null); // No existing user
        authService.register("testUser", "password");

        verify(userRepository, times(1)).create(any(User.class));
        verify(auditService, times(1)).logAction(any(User.class), eq("Login"));
    }

    @Test
    @DisplayName("Test register() - Should not register an existing user")
    void testRegisterExistingUser() {
        User existingUser = new User("testUser", "password", UserRole.CUSTOMER, null);
        when(userRepository.findByUsername("testUser")).thenReturn(existingUser);

        boolean result = authService.register("testUser", "password");

        assertThat(result).isFalse();
        verify(userRepository, never()).create(any(User.class));
        verify(auditService, never()).logAction(any(User.class), anyString());
    }

    @Test
    @DisplayName("Test login() - Should login successfully with correct credentials")
    void testLogin() {
        User user = new User("testUser", "password", UserRole.CUSTOMER, null);
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        User result = authService.login("testUser", "password");

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testUser");
        verify(auditService, times(1)).logAction(user, "User logged in");
    }

    @Test
    @DisplayName("Test login() - Should fail login with invalid credentials")
    void testLoginWithInvalidCredentials() {
        User user = new User("testUser", "password", UserRole.CUSTOMER, null);
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        User result = authService.login("testUser", "wrongPassword");

        assertThat(result).isNull();
        verify(auditService, never()).logAction(any(User.class), anyString());
    }

    @Test
    @DisplayName("Test login() - Should fail login for a non-existent user")
    void testLoginWithNonExistentUser() {
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(null);

        User result = authService.login("nonExistentUser", "password");

        assertThat(result).isNull();
        verify(auditService, never()).logAction(any(User.class), anyString());
    }
}
