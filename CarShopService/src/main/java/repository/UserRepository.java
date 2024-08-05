package repository;

import java.util.ArrayList;
import model.User;
import java.util.*;
/**
 * Repository to store users.
 */
public class UserRepository {
    private  List<User> users = new ArrayList<>();
    private  int nextID = 1;

    /**
     * Find user by ID
     * @param id
     * @return the user found, else null
     */
    public User findById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst().orElse(null);
    }

    /**
     * Find user by username.
     * @param username
     * @return the user found, else null
     */
    public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    /**
     * Adding a new user to the repo.
     * @param user to add
     */
    public void save(User user) {
        users.add(user);
    }

    /**
     * Update the existing user.
     * @param user to update
     */
    public void update(User user) {
        User existingUser = findById(user.getId());
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setRole(user.getRole());
        }
    }

    /**
     * Find all users in the repo
     * @return array list containing users
     */
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    /**
     * Generate next ID
     * @return
     */
    public int getNextId(){
        return nextID++;
    }

    /**
     * Delete user by ID.
     * @param id user ID
     * @return true if deleted
     */
    public boolean deleteById(int id) {
        return users.removeIf(user -> user.getId() == id);
    }
}
