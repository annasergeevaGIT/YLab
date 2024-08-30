package org.example.repository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.config.jdbc.DatabaseService;
import org.example.domain.model.Order;
import org.example.domain.model.OrderStatus;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Repository
public class OrderRepository implements Crud<Order>{
    private static final DatabaseService databaseService = new DatabaseService();
    private Connection connection;

    public OrderRepository() {
        try {
            this.connection = databaseService.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Connection error: " + e.getMessage());
        }
    }

    /**
     * Saves an order to the PostgreSQL database.
     *
     * @param order the order to save
     */
    @Override
    public void create(Order order) {

        String sql = "INSERT INTO entity_schema.orders (user_id, car_id, order_status, created_at) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getUserId());
            stmt.setInt(2, order.getCarId());
            stmt.setString(3, order.getStatus().toString());
            stmt.setTimestamp(4, Timestamp.valueOf(order.getCreatedAt()));
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving order to database: " + e.getMessage(), e);
        }
    }

    /**
     * Returns a list of all orders from the PostgreSQL database.
     *
     * @return the list of orders
     */
    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM entity_schema.orders";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setCarId(rs.getInt("car_id"));
                order.setStatus(OrderStatus.valueOf(rs.getString("order_status")));
                order.setCreatedAt(rs.getTimestamp("order_date").toLocalDateTime());
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
    @Override
    public Order findById(int id) {
        Order order = null;
        String sql = "SELECT * FROM entity_schema.orders WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setCarId(rs.getInt("car_id"));
                    order.setStatus(OrderStatus.valueOf(rs.getString("order_status")));
                    order.setCreatedAt(rs.getTimestamp("order_date").toLocalDateTime());
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
    @Override
    public void update(Order order) {
        String sql = "UPDATE entity_schema.orders SET user_id = ?, car_id = ?, order_status = ?, order_date = ?, total = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order.getUserId());
            stmt.setInt(2, order.getCarId());
            stmt.setString(3, order.getStatus().toString());
            stmt.setTimestamp(4, Timestamp.valueOf(order.getCreatedAt()));
            stmt.setInt(6, order.getId());
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
    @Override
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