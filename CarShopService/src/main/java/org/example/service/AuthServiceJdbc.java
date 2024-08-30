package org.example.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.dto.UserDTO;
import org.example.domain.model.UserRole;
import org.example.domain.model.User;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service for authentication and authorization.
 */
@Data
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceJdbc implements Authenticate {
    private final UserRepository userRepository;
    private final AuditService auditService;
    private User currentUser;
    private UserMapper userMapper;

    /**
     * Registers a new user with the CUSTOMER role.
     * @param userDTO the user data transfer object
     * @return true if registration is successful, false if user already exists
     */
    public boolean register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.username()) != null) {
            log.error("User already exists");
            return false;
        }
        User newUser = new User(userDTO.username(), userDTO.password(),userDTO.email(), userDTO.age(), UserRole.CUSTOMER, null);
        userRepository.create(newUser);
        auditService.logAction(newUser.getId(), "User registered");
        return true;
    }

    /**
     * Registers a root admin with the ADMIN role.
     * @param userDTO the user data transfer object
     */
    public void registerAdmin(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.username()) == null) {
            User newUser = new User(userDTO.username(), userDTO.password(), userDTO.email(), userDTO.age(), UserRole.ADMIN, null);
            userRepository.create(newUser);
            auditService.logAction(newUser.getId(), "Admin registered");
        }
    }

    /**
     * Authenticates a user with the given username and password.
     * @param username the username
     * @param password the password
     * @return UserDTO if authentication is successful, null otherwise
     */
    public UserDTO login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            auditService.logAction(currentUser.getId(), "User logged in");
            return userMapper.toDTO(user);
        }
        return null;
    }
}
