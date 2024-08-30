package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.model.User;
import org.example.domain.model.UserRole;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for user management.
 */
@Slf4j
@RequiredArgsConstructor // Constructor injection for UserRepository
@Service
public class UserService {

    private final UserRepository userRepository; // Dependency injection via constructor

    /**
     * Gets all users.
     *
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Gets a user by ID.
     *
     * @param userId the user ID
     * @return the user, or null if not found
     */
    public User getUserById(int userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            log.warn("User with ID {} not found", userId);
        }
        return user;
    }

    /**
     * Checks if a user exists by ID.
     *
     * @param userId the user ID
     * @return true if the user exists, false otherwise
     */
    public boolean userExists(int userId) {
        return userRepository.findById(userId) != null;
    }

    /**
     * Updates the role of a user by ID.
     *
     * @param userId the user ID to update
     * @param newRole the new role to assign
     */
    public void updateUserRole(int userId, UserRole newRole) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.setRole(newRole);
            userRepository.update(user);
            log.info("Updated role for user ID {} to {}", userId, newRole);
        } else {
            log.warn("User with ID {} not found for role update", userId);
        }
    }

    /**
     * Updates the role of a user by username.
     *
     * @param username the username of the user to update
     * @param newRole the new role to assign
     * @return true if the update was successful, false otherwise
     */
    public boolean updateUserRoleByName(String username, UserRole newRole) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setRole(newRole);
            userRepository.update(user);
            log.info("Updated role for user with username {} to {}", username, newRole);
            return true;
        } else {
            log.error("Failed to update user role for username {}", username);
            return false;
        }
    }

    /**
     * Adds a new user to the repository.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @param email the email of the new user
     * @param age the age of the new user
     * @param role the role of the new user
     */
    public void addUser(String username, String password, String email, int age, UserRole role) {
        User newUser = new User(username, password, email, age, role, null);
        userRepository.create(newUser);
        log.info("Added new user with username {}", username);
    }

    /**
     * Deletes the user with the specified ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUser(int id) {
        if (userExists(id)) {
            userRepository.delete(id);
            log.info("Deleted user with ID {}", id);
        } else {
            log.warn("User with ID {} not found for deletion", id);
        }
    }
}
