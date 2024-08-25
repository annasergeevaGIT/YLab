package org.example.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import java.util.*;

/**
 * Represents a user in the system.
 */
@Data
@RequiredArgsConstructor
@Table(name = "users", schema = "entity_schema")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_seq")
    @SequenceGenerator(schema = "entity_schema",
            name = "user_seq", // referencing GeneratedValue annotation
            sequenceName = "entity_schema.user_id_seq",  // name and schema
            allocationSize = 1)
    private int id;
    private String username;
    private String password;
    private String email;
    private int age;
    private UserRole role;
    private List<Order> orders;

    /**
     * Constructs a new User.
     *
     * @param username     the username of the user
     * @param password     the password of the user
     * @param email        the email of the user
     * @param age          the age of the user
     * @param role         the role of the user (CUSTOMER, MANAGER, ADMIN)
     * @param orders       the orders of the user
     */

    public User(String username, String password, String email, int age, UserRole role, List<Order> orders) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.role = role;
        this.orders = orders;
    }

}

