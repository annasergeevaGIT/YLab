package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.service.DatabaseService;
import org.example.model.User;
import org.example.model.UserRole;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class UserRepository {
    private Connection connection;
    private static final String SEQUENCE_NAME = "entity_schema.users_id_seq";

    public UserRepository() {
        try {
            this.connection = DatabaseService.getConnection();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Connection error: " + e.getMessage(), e);
        }
    }

    /**
     * Adding a new user to the repository and the PostgreSQL database.
     *
     * @param user the user to add
     */
    public void create(User user) {

        String sql = "INSERT INTO entity_schema.users (username, password, role) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user to database: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieve all users from the PostgreSQL database.
     *
     * @return a list containing all users
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM entity_schema.users";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving users from database: " + e.getMessage(), e);
        }

        return users;
    }

    /**
     * Find a user by their ID in the PostgreSQL database.
     *
     * @param id the ID of the user to find
     * @return the user if found, otherwise null
     */
    public User findById(int id) {
        User user = null;
        String sql = "SELECT * FROM entity_schema.users WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(UserRole.valueOf(rs.getString("role")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user from database: " + e.getMessage(), e);
        }

        return user;
    }

    /**
     * Find a user by their username in the PostgreSQL database.
     *
     * @param username the username of the user to find
     * @return the user if found, otherwise null
     */
    public User findByUsername(String username) {
        User user = null;
        String sql = "SELECT * FROM entity_schema.users WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(UserRole.valueOf(rs.getString("role")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user from database: " + e.getMessage(), e);
        }

        return user;
    }

    /**
     * Update the existing user in the PostgreSQL database.
     *
     * @param user the user to update
     */
    public void update(User user) {
        String sql = "UPDATE entity_schema.users SET username = ?, password = ?, role = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().toString());
            stmt.setInt(4, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user in database: " + e.getMessage(), e);
        }
    }

    /**
     * Delete a user by their ID from the PostgreSQL database.
     *
     * @param id the ID of the user to delete
     * @return true if the user was deleted, otherwise false
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM entity_schema.users WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user from database: " + e.getMessage(), e);
        }
    }
}
