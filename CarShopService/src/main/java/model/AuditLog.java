package model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents an audit log entry.
 */
@Data
public class AuditLog {
    private int id;
    private User user;
    private String action;
    private LocalDateTime timestamp;

    /**
     * Constructs an AuditLog entry.
     *
     * @param id        the unique identifier for the audit log entry
     * @param user      the user who performed the action
     * @param action    the action performed by the user
     * @param timestamp the time the action was performed
     */
    public AuditLog(int id, User user, String action, LocalDateTime timestamp) {
        this.id = id;
        this.user = user;
        this.action = action;
        this.timestamp = timestamp;
    }
}
