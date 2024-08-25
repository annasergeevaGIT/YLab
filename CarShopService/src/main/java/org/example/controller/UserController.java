package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.model.User;
import org.example.domain.model.UserRole;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling user management actions.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor // Inject dependencies through the constructor
public class UserController {

    private final UserService userService;

    /**
     * Get all users registered in the system with their roles.
     *
     * @return list of all users
     */
    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Change the role of a user by ID.
     *
     * @param userId  the ID of the user
     * @param newRole the new role to assign
     * @return response entity with status
     */
    @PutMapping("/{userId}/role")
    public ResponseEntity<String> changeUserRole(@PathVariable int userId, @RequestParam String newRole) {
        UserRole role = UserRole.valueOf(newRole.toUpperCase());
        userService.updateUserRole(userId, role);
        return new ResponseEntity<>("User role is updated.", HttpStatus.OK);
    }

    /**
     * Add a new user.
     *
     * @param user the user to add
     * @return response entity with status
     */
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.addUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getAge(), user.getRole());
        return new ResponseEntity<>("User is added.", HttpStatus.CREATED);
    }

    /**
     * Remove a user by ID.
     *
     * @param userId the ID of the user to remove
     * @return response entity with status
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User is removed.", HttpStatus.NO_CONTENT);
    }
}
