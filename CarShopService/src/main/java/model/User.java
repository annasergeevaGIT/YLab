package model;

import lombok.Data;

/**
 * Represents a user in the system.
 */
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private UserRole role;
    //private int orderCount;
    /**
     * Constructs a new User.
     *
     * @param id           the unique identifier for the user
     * @param username     the username of the user
     * @param password     the password of the user
     * @param role         the role of the user (CUSTOMER, MANAGER, ADMIN)
     //* @param orderCount   counting orders with status(COMPLETED) for particular user
     */

    public User(int id, String username, String password, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        //this.orderCount = orderCount;
    }
}

