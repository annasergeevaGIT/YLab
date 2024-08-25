package org.example.service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.model.UserRole;
import org.example.domain.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for user management.
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
public class UserService {
    private UserRepository userRepository;
    /**
     * Gets all users.
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Gets a user by ID.
     * @param userID the user ID
     * @return the user, or null if not found
     */
    public User getUserById(int userID) {
        return userRepository.findById(userID);
    }

    /**
     * Updates the role of a user.
     * @param userId the user to update
     * @param newRole the new role
     */
    public void updateUserRole(int userId, UserRole newRole) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.setRole(newRole);
            userRepository.update(user);
        }
    }
    /**
     * Updates the role of a user.
     * @param username the user to update
     * @param newRole the new role
     */
    public boolean updateUserRoleByName(String username, UserRole newRole) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setRole(newRole);
            userRepository.update(user);
            return true;

        }
        log.error("Failed to update user role");
        return false;
    }

    /**
     * Adding a user in the user repository.
     */
    public void addUser(String username, String password,String email,  int age, UserRole role) {
        User newUser = new User(username, password, email, age, role, null);
        userRepository.create(newUser);
    }

    /**
     * Deletes the user with the specified ID.
     * @param id the ID of the user to delete
     */
    public void deleteUser(int id) {
        userRepository.delete(id);
    }

}
