package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.AuditLog;
import org.example.model.User;
import org.example.repository.AuditRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing audit logs.
 */
@AllArgsConstructor
@Data
public class AuditService {
    private AuditRepository auditRepository;

    /**
     * Logs an action performed by a user.
     *
     * @param user   the user who performed the action
     * @param action the action performed by the user
     */
    public void logAction(User user, String action) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setUser(user);
        log.setTimestamp(LocalDateTime.now());
        auditRepository.create(log);
    }

    /**
     *
     * @return all log files
     */
    public List<AuditLog> getAllLogs() {
        return auditRepository.findAll();
    }
}

