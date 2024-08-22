package org.example.repository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.util.AuditLog;
import org.example.config.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class AuditRepository {

    private Connection connection;
    private static final DatabaseService databaseService = new DatabaseService();

    public AuditRepository() {
        try {
            this.connection = databaseService.getConnection();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Connection error: " + e.getMessage(), e);
        }
    }

    /**
     * Saves an audit log entry to the PostgreSQL database.
     *
     * @param log the audit log entry to save
     */
    public void create(AuditLog log) {
        String sql = "INSERT INTO entity_schema.audit_logs (timestamp, action, user_id) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(log.getTimestamp()));
            stmt.setString(2, log.getAction());
            stmt.setInt(3, log.getUser().getId());

            stmt.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    log.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating audit log failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving audit log to database: " + e.getMessage(), e);
        }
    }
    /**
     * Returns a list of all audit log entries from the PostgreSQL database.
     *
     * @return the list of audit log entries
     */
    public List<AuditLog> findAll() {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM entity_schema.audit_logs";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AuditLog log = new AuditLog();
                log.setId(rs.getInt("id"));
                log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                log.setAction(rs.getString("action"));
                log.getUser().setId(rs.getInt("user_id"));

                logs.add(log);
            }
        } catch (SQLException e) {
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
    public AuditLog findById(int id) {
        AuditLog log = null;
        String sql = "SELECT * FROM entity_schema.audit_logs WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    log = new AuditLog();
                    log.setId(rs.getInt("id"));
                    log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                    log.setAction(rs.getString("action"));
                    log.getUser().setId(rs.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving audit log from database: " + e.getMessage(), e);
        }

        return log;
    }

    /**
     * Update an existing audit log in the PostgreSQL database.
     *
     * @param log the audit log to update
     */
    public void update(AuditLog log) {
        String sql = "UPDATE entity_schema.audit_logs SET timestamp = ?, action = ?, user_id = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(log.getTimestamp()));
            stmt.setString(2, log.getAction());
            stmt.setInt(3, log.getUser().getId());
            stmt.setInt(4, log.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating audit log in database: " + e.getMessage(), e);
        }
    }

    /**
     * Delete an audit log by its ID from the PostgreSQL database.
     *
     * @param id the ID of the audit log to delete
     */
    public void delete(int id) {
        String sql = "DELETE FROM entity_schema.audit_logs WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No audit log found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting audit log from database: " + e.getMessage(), e);
        }
    }
}
