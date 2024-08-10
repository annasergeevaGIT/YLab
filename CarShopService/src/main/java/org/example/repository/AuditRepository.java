package org.example.repository;

import org.example.model.AuditLog;
import java.util.*;

/**
 * Repository for managing audit logs.
 */
public class AuditRepository {
    private Map<Integer,AuditLog> logs = new HashMap<>();

    /**
     * Saves an audit log entry to the repository.
     *
     * @param log the audit log entry to save
     */
    public void create(AuditLog log) {
        logs.put(log.getId(), log);
    }

    /**
     * Returns a list of all audit log entries.
     *
     * @return the list of audit log entries
     */
    public List<AuditLog> findAll() {
        return new ArrayList<>(logs.values());
    }

    /**
     * Gets the next audit log ID.
     *
     * @return the next audit log ID
     */
}
