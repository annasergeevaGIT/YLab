package service;
import model.UserRole;
import model.User;
import repository.UserRepository;
import java.util.List;

/**
 * Service for user management.
 */
public class UserService {
    private UserRepository userRepository;

    /**
     * Constructs a new UserService.
     *
     * @param userRepository the user repository
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
     * @param userID the user ID
     * @return the user, or null if not found
     */
    public User getUserById(int userID) {
        return userRepository.findById(userID);
    }

    /**
     * Updates the role of a user.
     *
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
     * Adding a user in the user repository.
     * @param user the user to add
     */
    public void addUser(User user) {
        userRepository.save(user);
    }

    /**
     * Generates the next user ID.
     * @return
     */
    public int getNextId() {
        return userRepository.getNextId();
    }

    /**
     * Deletes the user with the specified ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

}
