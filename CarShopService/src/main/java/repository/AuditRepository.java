package repository;

import model.AuditLog;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing audit logs.
 */
public class AuditRepository {
    private List<AuditLog> auditLogs = new ArrayList<>();
    private int nextId = 1;

    /**
     * Saves an audit log entry to the repository.
     *
     * @param log the audit log entry to save
     */
    public void save(AuditLog log) {
        log = new AuditLog(getNextId(), log.getUser(), log.getAction(), log.getTimestamp());
        auditLogs.add(log);
    }

    /**
     * Returns a list of all audit log entries.
     *
     * @return the list of audit log entries
     */
    public List<AuditLog> findAll() {
        return auditLogs;
    }

    /**
     * Gets the next audit log ID.
     *
     * @return the next audit log ID
     */
    public int getNextId() {
        return nextId++;
    }
    /**
     * Exports the audit logs to a text file.
     *
     * @param filename the name of the file to export to
     * @throws IOException if an I/O error occurs
     */
    public void exportLogs(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (AuditLog log : auditLogs) {
                writer.write(log.toString() + "\n");
            }
        }
    }
}
