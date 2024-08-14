package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents an audit log entry.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuditLog {
    private int id;
    private User user;
    private String action;
    private LocalDateTime timestamp;

    /**
     * Constructs an AuditLog entry.
     *
     * @param user      the user who performed the action
     * @param action    the action performed by the user
     * @param timestamp the time the action was performed
     */
    public AuditLog(User user, String action, LocalDateTime timestamp) {
        this.user = user;
        this.action = action;
        this.timestamp = timestamp;
    }
}
