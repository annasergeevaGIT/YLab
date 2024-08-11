package org.example.repository;

import org.example.model.AuditLog;
import org.example.util.IDGenerator;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditRepository {

    private Connection connection;
    private static final String SEQUENCE_NAME = "entity_schema.audit_logs_id_seq";

    public AuditRepository() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Connection error: " + e.getMessage(), e);
        }
    }

    /**
     * Saves an audit log entry to the PostgreSQL database.
     *
     * @param log the audit log entry to save
     */
    public void create(AuditLog log) {
        int newId = IDGenerator.getNextId(SEQUENCE_NAME);
        log.setId(newId);

        String sql = "INSERT INTO entity_schema.audit_logs (id, timestamp, action, user_id, details) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, log.getId());
            stmt.setTimestamp(2, Timestamp.valueOf(log.getTimestamp()));
            stmt.setString(3, log.getAction());
            stmt.setInt(4, log.getUser().getId());

            stmt.executeUpdate();
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
}
