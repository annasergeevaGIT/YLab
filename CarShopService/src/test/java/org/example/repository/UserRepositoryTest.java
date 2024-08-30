package org.example.repository;

import org.example.domain.model.Order;
import org.example.domain.model.User;
import org.example.domain.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest extends BaseRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository = new UserRepository();
    }

    @Test
    @DisplayName("Should create and find a user by ID")
    public void testCreateAndFindById() {
        List<Order> orders = new ArrayList<>();  // Assuming Order has a default constructor or similar
        User user = new User("anna", "pw123", UserRole.CUSTOMER, orders);
        userRepository.create(user);

        User foundUser = userRepository.findById(user.getId());
        assertNotNull(foundUser);
        assertEquals("anna", foundUser.getUsername());
    }

    @Test
    @DisplayName("Should find all users in the repository")
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
    @DisplayName("Should find a user by username")
    public void testFindByUsername() {
        List<Order> orders = new ArrayList<>();
        User user = new User("new_user", "pw789", UserRole.ADMIN, orders);
        userRepository.create(user);

        User foundUser = userRepository.findByUsername("new_user");
        assertNotNull(foundUser);
        assertEquals(UserRole.ADMIN, foundUser.getRole());
    }

    @Test
    @DisplayName("Should update a user")
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
    @DisplayName("Should delete a user")
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
