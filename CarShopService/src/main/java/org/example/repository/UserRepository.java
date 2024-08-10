package org.example.repository;

import java.util.ArrayList;
import org.example.model.User;
import java.util.*;
/**
 * Repository to store users.
 */
public class UserRepository {
    private  Map<Integer, User> users = new HashMap<>();
    /**
     * Adding a new user to the repo.
     * @param user to add
     */
    public void create(User user) {
        users.put(user.getId(), user);
    }

    /**
     * Find all users in the repo
     * @return array list containing users
     */
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Find user by ID
     * @param id
     * @return the user found, else null
     */
    public User findById(int id) {
        return users.values().stream()
                .filter(user -> user.getId() == id)
                .findFirst().orElse(null);
    }

    /**
     * Find user by username.
     * @param username
     * @return the user found, else null
     */
    public User findByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    /**
     * Update the existing user.
     * @param user to update
     */
    public void update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        }
    }

    /**
     * Delete user by ID.
     * @param id user ID
     * @return true if deleted
     */
    public boolean delete(int id) {
        return users.remove(id) != null;
    }
}
