package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.repository.AuditRepository;
import org.example.domain.model.AuditLog;

import java.util.List;
import org.springframework.stereotype.Service;


/**
 * Service for managing audit logs.
 */
@AllArgsConstructor
@Data
@Service
public class AuditService {
    private final AuditRepository auditRepository;

    /**
     * Logs an action performed by a user.
     *
     * @param userId   the userId who performed the action
     * @param action the action performed by the user
     */
    public void logAction(int userId, String action) {
        AuditLog logEntry = new AuditLog();
        logEntry.setAction(action);
        logEntry.setUserId(userId);
        auditRepository.create(logEntry);
    }

    public List<AuditLog> getAllLogs() {
        return auditRepository.findAll();
    }
}

