package service;

import model.UserRole;
import model.User;
import repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserRole(int userId, UserRole newRole) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.setRole(newRole);
            userRepository.update(user);
        }
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public int getNextId() {
        return userRepository.getNextId();
    }

    public User getUserById(int userID) {
        return userRepository.findById(userID);
    }
}
