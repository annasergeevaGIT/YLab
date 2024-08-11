package org.example.repository;

import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.util.IDGenerator;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private Connection connection;
    private static final String SEQUENCE_NAME = "entity_schema.orders_id_seq";

    public OrderRepository() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Connection error: " + e.getMessage());
        }
    }

    /**
     * Saves an order to the PostgreSQL database.
     *
     * @param order the order to save
     */
    public void create(Order order) {
        int newId = IDGenerator.getNextId(SEQUENCE_NAME);
        order.setId(newId);

        String sql = "INSERT INTO entity_schema.orders (id, user_id, status, created_at) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order.getId());
            stmt.setInt(2, order.getUser().getId());
            stmt.setString(3, order.getStatus().toString());
            stmt.setTimestamp(4, Timestamp.valueOf(order.getCreatedAt()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving order to database: " + e.getMessage(), e);
        }
    }

    /**
     * Returns a list of all orders from the PostgreSQL database.
     *
     * @return the list of orders
     */
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM entity_schema.orders";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.getUser().setId(rs.getInt("user_id"));
                order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving orders from database: " + e.getMessage(), e);
        }
        return orders;
    }

    /**
     * Find an order by its ID in the PostgreSQL database.
     *
     * @param id the ID of the order to find
     * @return the order if found, otherwise null
     */
    public Order findById(int id) {
        Order order = null;
        String sql = "SELECT * FROM entity_schema.orders WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt("id"));
                    order.getUser().setId(rs.getInt("user_id"));
                    order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                    order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving order from database: " + e.getMessage(), e);
        }
        return order;
    }

    /**
     * Update the existing order in the PostgreSQL database.
     *
     * @param order the order to update
     */
    public void update(Order order) {
        String sql = "UPDATE entity_schema.orders SET user_id = ?, status = ?, created_at = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order.getUser().getId());
            stmt.setString(2, order.getStatus().toString());
            stmt.setTimestamp(3, Timestamp.valueOf(order.getCreatedAt()));
            stmt.setInt(4, order.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating order in database: " + e.getMessage(), e);
        }
    }

    /**
     * Delete an order by its ID from the PostgreSQL database.
     *
     * @param id the ID of the order to delete
     */
    public void delete(int id) {
        String sql = "DELETE FROM entity_schema.orders WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No order found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting order from database: " + e.getMessage(), e);
        }
    }
}
