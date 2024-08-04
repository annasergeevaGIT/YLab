package service;

import model.CarStatus;
import model.UserRole;
import model.User;
import repository.UserRepository;

public class AuthService {
    private UserRepository userRepository;
    private AuditService auditService;
    private User loggedInUser;

    public AuthService(UserRepository userRepository, AuditService auditService ) {
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

    public boolean register(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            return false; // User already exist
        }
        User newUser = new User(userRepository.getNextId(), username, password, UserRole.CUSTOMER);
        userRepository.save(newUser);
        auditService.logAction(newUser, "Login");
        return true;
    }
    public boolean registerAdmin(String username, String password) {
        User newUser = new User(userRepository.getNextId(), username, password, UserRole.ADMIN);
        userRepository.save(newUser);
        return true;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

}

