package org.example.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Represents a user in the system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "entity_schema")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_seq")
    @SequenceGenerator(schema = "entity_schema",
            name = "user_seq",
            sequenceName = "entity_schema.user_id_seq",
            allocationSize = 1)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @Column(name = "age")
    @Min(value = 18, message = "Age should be greater than or equal to 18")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    /**
     * Constructs a new User.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param email    the email of the user
     * @param age      the age of the user
     * @param role     the role of the user (CUSTOMER, MANAGER, ADMIN)
     * @param orders   the orders of the user
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
