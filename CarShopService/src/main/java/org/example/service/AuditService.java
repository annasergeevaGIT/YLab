package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.User;
import org.example.repository.AuditRepository;
import org.example.util.AuditLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing audit logs.
 */
@AllArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;

    /**
     * Logs an action performed by a user.
     *
     * @param user   the user who performed the action
     * @param action the action performed by the user
     */
    public void logAction(User user, String action) {
        AuditLog logEntry = new AuditLog();
        logEntry.setAction(action);
        logEntry.setUser(user);
        logEntry.setTimestamp(LocalDateTime.now());
        auditRepository.create(logEntry);
    }

    /**
     * Retrieves all audit logs.
     *
     * @return all log entries
     */
    public List<AuditLog> getAllLogs() {
        return auditRepository.findAll();
    }
}
