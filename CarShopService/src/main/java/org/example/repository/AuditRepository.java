package org.example.repository;

import org.example.model.AuditLog;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing audit logs.
 */
public class AuditRepository {
    private List<AuditLog> logs = new ArrayList<>();
    private int nextId = 1;

    /**
     * Saves an audit log entry to the repository.
     *
     * @param log the audit log entry to save
     */
    public void save(AuditLog log) {
        log = new AuditLog(getNextId(), log.getUser(), log.getAction(), log.getTimestamp());
        logs.add(log);
    }

    /**
     * Returns a list of all audit log entries.
     *
     * @return the list of audit log entries
     */
    public List<AuditLog> findAll() {
        return logs;
    }

    /**
     * Gets the next audit log ID.
     *
     * @return the next audit log ID
     */
    public int getNextId() {
        return nextId++;
    }

}
