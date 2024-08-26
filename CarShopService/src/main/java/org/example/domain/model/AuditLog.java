package org.example.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
@Entity
@Table(name = "audit_logs", schema = "entity_schema")
public class AuditLog {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "log_seq")
    @SequenceGenerator(schema = "entity_schema",
            name = "log_seq", // referencing GeneratedValue annotation
            sequenceName = "entity_schema.audit_log_id_seq",  // name and schema
            allocationSize = 1)
    private int id;

    @Column(name = "user_id")
    @NotNull(message = "User ID could not be null")
    private int userId;

    @Column(name = "action")
    @NotNull(message = "Action could not be null")
    private String action;

    @Column(name = "timestamp")
    @PastOrPresent(message = "Timestamp could not be in the future")
    private LocalDateTime timestamp;

    /**
     * Constructs an AuditLog entry.
     *
     * @param userId     the user who performed the action
     * @param action    the action performed by the user
     */
    public AuditLog(int userId, String action) {
        this.userId = userId;
        this.action = action;
        this.timestamp = LocalDateTime.now(); // Automatically set to the current time
    }
}
