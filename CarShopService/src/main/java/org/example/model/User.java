package org.example.model;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Represents a user in the system.
 */
@AllArgsConstructor
@Data
public class User {
    private int id;
    private static int idCounter = 1;
    private String username;
    private String password;
    private UserRole role;
    private List<Order> orders;

    /**
     * Constructs a new User.
     *
     * @param username     the username of the user
     * @param password     the password of the user
     * @param role         the role of the user (CUSTOMER, MANAGER, ADMIN)
     * @param orders       the orders of the user
     */
    public User(String username, String password, UserRole role, List<Order> orders) {
        this.id = this.idCounter;
        this.idCounter++;
        this.username = username;
        this.password = password;
        this.role = role;
        this.orders = orders;
    }
}

