package org.example.service;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.UserDTO;
import org.example.model.UserRole;
import org.example.model.User;
import org.example.repository.UserRepository;

/**
 * Service for authentication and authorization.
 */
public class AuthService {
    private final UserRepository userRepository;
    private final AuditService auditService;

    @Getter
    @Setter
    private User currentUser;

    /**
     * Constructs a new AuthService.
     *
     * @param userRepository the user repository
     * @param auditService the audit service
     */
    public AuthService(UserRepository userRepository, AuditService auditService) {
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

    /**
     * Registers a new user with the CUSTOMER role.
     *
     * @param userDTO the user data transfer object
     * @return true if registration is successful, false if user already exists
     */
    public boolean register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            return false; // User already exists.
        }
        User newUser = new User(userDTO.getUsername(), userDTO.getPassword(), UserRole.CUSTOMER, null);
        userRepository.create(newUser);
        auditService.logAction(newUser, "User registered");
        return true;
    }

    /**
     * Registers a root admin with the ADMIN role.
     *
     * @param userDTO the user data transfer object
     */
    public void registerAdmin(UserDTO userDTO) {
        // Check if admin user already exists
        if (userRepository.findByUsername(userDTO.getUsername()) == null) {
            User newUser = new User(userDTO.getUsername(), userDTO.getPassword(), UserRole.ADMIN, null);
            userRepository.create(newUser);
            auditService.logAction(newUser, "Admin registered");
        }
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
            auditService.logAction(currentUser, "User logged in");
            return user;
        }
        return null;
    }
}
