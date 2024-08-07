package org.example.service;

import org.example.model.UserRole;
import org.example.model.User;
import org.example.repository.UserRepository;
/**
 * Service for authentication and authorization.
 */
public class AuthService {
    private UserRepository userRepository;
    private AuditService auditService;
    private User currentUser;

    /**
     * Constructs a new AuthService.
     * @param userRepository the user repository
     * @param auditService the audit service
     */
    public AuthService(UserRepository userRepository, AuditService auditService ) {
        this.userRepository = userRepository;
        this.auditService = auditService;
    }
    /**
     * Registers a new user with the CUSTOMER role.
     *
     * @param username the username
     * @param password the password
     */
    public boolean register(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            return false; // Пользователь уже существует
        }
        User newUser = new User(userRepository.getNextId(), username, password, UserRole.CUSTOMER);
        userRepository.save(newUser);
        auditService.logAction(newUser, "Login");
        return true;
    }
    /**
     * Registers a root admin with the ADMIN role.
     *
     * @param username the username
     * @param password the password
     */
    public boolean registerAdmin(String username, String password) {
        User newUser = new User(userRepository.getNextId(), username, password, UserRole.ADMIN);
        userRepository.save(newUser);
        auditService.logAction(newUser, "Register Admin");
        return true;
    }
    /**
     * Authenticates a user with the given username and password.
     *
     * @param username the username
     * @param password the password
     * @return User if authentication is successful, null otherwise
     */
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return user;

        }
        return null;
    }
    /**
     * Gets the currently logged-in user.
     *
     * @return the currently logged-in user
     */
    public User getCurrentUser() {
        return currentUser;
    }
}

