package org.example.service;

import org.example.model.AuditLog;
import org.example.model.User;
import org.example.repository.AuditRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing audit logs.
 */
public class AuditService {
    private AuditRepository auditRepository;


    /**
     * Constructs an AuditService.
     *
     * @param auditRepository the repository for audit logs
     */
    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;

    }

    /**
     * Logs an action performed by a user.
     *
     * @param user   the user who performed the action
     * @param action the action performed by the user
     */
    public void logAction(User user, String action) {
        AuditLog log = new AuditLog( user, action, LocalDateTime.now());
        auditRepository.create(log);
    }

    /**
     * Returns a list of audit log entries filtered by date, user, and action type.
     *
     * @param startDate the start date for filtering
     * @param endDate   the end date for filtering
     * @param user      the user to filter by (can be null)
     * @param action    the action type to filter by (can be null)
     * @return the list of filtered audit log entries
     */

    public List<AuditLog> filterLogs(LocalDateTime startDate, LocalDateTime endDate, User user, String action) {
        return auditRepository.findAll().stream()
                .filter(log -> (startDate == null || !log.getTimestamp().isBefore(startDate)) &&
                        (endDate == null || !log.getTimestamp().isAfter(endDate)) &&
                        (user == null || log.getUser().equals(user)) &&
                        (action == null || log.getAction().equals(action)))
                .collect(Collectors.toList());
    }

    /**
     *
     * @return all log files
     */
    public List<AuditLog> getAllLogs() {
        return auditRepository.findAll();
    }
}

