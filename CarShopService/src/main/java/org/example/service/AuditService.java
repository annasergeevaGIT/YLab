package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.domain.model.AuditLog;
import org.example.repository.AuditRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
     * @param action   the action performed by the user
     */
    public void logAction(int userId, String action) {
        AuditLog logEntry = new AuditLog();
        logEntry.setAction(action);
        logEntry.setUserId(userId);
        auditRepository.create(logEntry);
    }

    /**
     * Retrieves all audit logs.
     *
     * @return the list of all audit logs
     */
    public List<AuditLog> getAllLogs() {
        return auditRepository.findAll();
    }

    /**
     * Exports all audit logs to a file.
     *
     * @throws IOException if an I/O error occurs
     */
    public void exportLogs() throws IOException {
        List<AuditLog> logs = getAllLogs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("audit_logs.txt"))) {
            for (AuditLog log : logs) {
                writer.write(String.format("ID: %d, Timestamp: %s, Action: %s, UserID: %d%n",
                        log.getId(), log.getTimestamp(), log.getAction(), log.getUserId()));
            }
        } catch (IOException e) {
            throw new IOException("Error writing logs to file: " + e.getMessage(), e);
        }
    }
}
