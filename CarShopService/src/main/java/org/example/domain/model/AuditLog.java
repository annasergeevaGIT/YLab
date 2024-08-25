package org.example.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import nonapi.io.github.classgraph.json.Id;

import java.time.LocalDateTime;

/**
 * Represents an audit log entry.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "audit_logs", schema = "entity_schema")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "log_seq")
    @SequenceGenerator(schema = "entity_schema",
            name = "log_seq", // referencing GeneratedValue annotation
            sequenceName = "entity_schema.audit_log_id_seq",  // name and schema
            allocationSize = 1)
    private int id;
    @Column(name = "user_id")
    @NotEmpty(message = "userId could not be empty")
    private int userId;
    @Column(name = "action")
    @NotEmpty(message = "action could not be empty")
    private String action;
    @Column(name = "timestamp")
    @PastOrPresent(message = "timestamp could not be in the future")
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
        this.timestamp = LocalDateTime.now(); // Automatically set current time;
    }

}
