package org.example.repository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.model.AuditLog;
import org.example.config.jdbc.DatabaseService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Repository
public class AuditRepository implements Crud<AuditLog> {

    private final Connection connection;

    // Inject DatabaseService via constructor
    public AuditRepository(DatabaseService databaseService) {
        try {
            this.connection = databaseService.getConnection();
        } catch (Exception e) {
            log.error("Failed to establish database connection: {}", e.getMessage(), e);
            throw new RuntimeException("Connection error: " + e.getMessage(), e);
        }
    }

    /**
     * Saves an audit log entry to the PostgreSQL database.
     *
     * @param logEvent the audit log entry to save
     */
    @Override
    public void create(AuditLog logEvent) {
        String sql = "INSERT INTO entity_schema.audit_logs (timestamp, action, user_id) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(logEvent.getTimestamp())); // Set timestamp first
            stmt.setString(2, logEvent.getAction()); // Then action
            stmt.setInt(3, logEvent.getUserId()); // Finally, user_id

            stmt.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    logEvent.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating audit log failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            log.error("Error saving audit log to database: {}", e.getMessage(), e);
            throw new RuntimeException("Error saving audit log to database: " + e.getMessage(), e);
        }
    }

    /**
     * Returns a list of all audit log entries from the PostgreSQL database.
     *
     * @return the list of audit log entries
     */
    @Override
    public List<AuditLog> findAll() {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM entity_schema.audit_logs";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AuditLog log = new AuditLog();
                log.setId(rs.getInt("id"));
                log.setUserId(rs.getInt("user_id")); // Create and set User with ID
                log.setAction(rs.getString("action"));
                log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                logs.add(log);
            }
        } catch (SQLException e) {
            log.error("Error retrieving audit logs from database: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving audit logs from database: " + e.getMessage(), e);
        }
        return logs;
    }

    /**
     * Find an audit log by its ID in the PostgreSQL database.
     *
     * @param id the ID of the audit log to find
     * @return the audit log if found, otherwise null
     */
    @Override
    public AuditLog findById(int id) {
        AuditLog logEntry = null;
        String sql = "SELECT * FROM entity_schema.audit_logs WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    logEntry = new AuditLog();
                    logEntry.setId(rs.getInt("id"));
                    logEntry.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    logEntry.setAction(rs.getString("action"));
                    logEntry.setUserId(rs.getInt("user_id")); // Create and set User with ID
                }
            }
        } catch (SQLException e) {
            log.error("Error retrieving audit log from database: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving audit log from database: " + e.getMessage(), e);
        }

        return logEntry;
    }

    /**
     * Update an existing audit log in the PostgreSQL database.
     *
     * @param logEntity the audit log to update
     */
    @Override
    public void update(AuditLog logEntity) {
        String sql = "UPDATE entity_schema.audit_logs SET timestamp = ?, action = ?, user_id = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(logEntity.getTimestamp()));
            stmt.setString(2, logEntity.getAction());
            stmt.setInt(3, logEntity.getUserId());
            stmt.setInt(4, logEntity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Error updating audit log in database: {}", e.getMessage(), e);
            throw new RuntimeException("Error updating audit log in database: " + e.getMessage(), e);
        }
    }

    /**
     * Delete an audit log by its ID from the PostgreSQL database.
     *
     * @param id the ID of the audit log to delete
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM entity_schema.audit_logs WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No audit log found with ID: " + id);
            }
        } catch (SQLException e) {
            log.error("Error deleting audit log from database: {}", e.getMessage(), e);
            throw new RuntimeException("Error deleting audit log from database: " + e.getMessage(), e);
        }
    }
}
