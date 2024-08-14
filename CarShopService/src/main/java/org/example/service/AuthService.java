package org.example.service;

import lombok.Getter;
import org.example.model.UserRole;
import org.example.model.User;
import org.example.repository.UserRepository;
/**
 * Service for authentication and authorization.
 */
public class AuthService {
    private UserRepository userRepository;
    private AuditService auditService;
    /**
     * -- GETTER --
     *  Gets the currently logged-in user.
     *
     * @return the currently logged-in user
     */
    @Getter
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
            return false; // User already exists.
        }
        User newUser = new User(username, password, UserRole.CUSTOMER,null);
        userRepository.create(newUser);
        auditService.logAction(newUser, "Login");
        return true;
    }
    /**
     * Registers a root admin with the ADMIN role.
     *
     * @param username the username
     * @param password the password
     */
    public void registerAdmin(String username, String password) {
        User newUser = new User(username, password, UserRole.ADMIN,null);
        userRepository.create(newUser);
        auditService.logAction(newUser, "Register Admin"); // write log file
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
            auditService.logAction(currentUser, "User logged in"); // write log file
            return user;
        }
        return null;
    }
    /**
     * Retrieves the currently logged-in user.
     *
     * @return the currently logged-in user
     */
    public User getCurrentUser() {
        return currentUser;
    }
}

