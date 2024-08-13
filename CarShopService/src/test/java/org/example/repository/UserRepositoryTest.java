package org.example.repository;

import org.example.model.Order;
import org.example.model.User;
import org.example.model.UserRole;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private static PostgreSQLContainer<?> postgreSQLContainer;
    private UserRepository userRepository;

    @BeforeAll
    public static void setUp() {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("postgres")
                .withUsername("root")
                .withPassword("root");
        postgreSQLContainer.start();

        // Override the connection properties with Testcontainers JDBC URL
        System.setProperty("database.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("database.username", postgreSQLContainer.getUsername());
        System.setProperty("database.password", postgreSQLContainer.getPassword());
    }

    @BeforeEach
    public void init() {
        userRepository = new UserRepository();
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    public void testCreateAndFindById() {
        List<Order> orders = new ArrayList<>();  // Assuming Order has a default constructor or similar
        User user = new User("anna", "pw123", UserRole.CUSTOMER, orders);
        userRepository.create(user);

        User foundUser = userRepository.findById(user.getId());
        assertNotNull(foundUser);
        assertEquals("anna", foundUser.getUsername());
    }

    @Test
    public void testFindAll() {
        List<Order> orders = new ArrayList<>();
        User user1 = new User("anna", "pw123", UserRole.CUSTOMER, orders);
        User user2 = new User("alla", "pw456", UserRole.MANAGER, orders);

        userRepository.create(user1);
        userRepository.create(user2);

        List<User> users = userRepository.findAll();
        assertNotNull(users);
        assertTrue(users.size() >= 2);  // Assuming other tests might add users as well
    }

    @Test
    public void testFindByUsername() {
        List<Order> orders = new ArrayList<>();
        User user = new User("new_user", "pw789", UserRole.ADMIN, orders);
        userRepository.create(user);

        User foundUser = userRepository.findByUsername("unique_user");
        assertNotNull(foundUser);
        assertEquals(UserRole.ADMIN, foundUser.getRole());
    }

    @Test
    public void testUpdate() {
        List<Order> orders = new ArrayList<>();
        User user = new User("anna", "pw123", UserRole.CUSTOMER, orders);
        userRepository.create(user);

        user.setUsername("anna_updated");
        user.setPassword("newpw");
        user.setRole(UserRole.MANAGER);
        userRepository.update(user);

        User updatedUser = userRepository.findById(user.getId());
        assertNotNull(updatedUser);
        assertEquals("anna_updated", updatedUser.getUsername());
        assertEquals("newpw", updatedUser.getPassword());
        assertEquals(UserRole.MANAGER, updatedUser.getRole());
    }

    @Test
    public void testDelete() {
        List<Order> orders = new ArrayList<>();
        User user = new User("new_user", "pw123", UserRole.CUSTOMER, orders);
        userRepository.create(user);

        boolean isDeleted = userRepository.delete(user.getId());
        assertTrue(isDeleted);

        User deletedUser = userRepository.findById(user.getId());
        assertNull(deletedUser);
    }
}
