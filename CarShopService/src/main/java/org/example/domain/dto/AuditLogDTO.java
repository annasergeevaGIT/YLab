package org.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for AuditLog. To transfer info about an audit log entry between different layers.
 */
public record AuditLogDTO(
        @JsonProperty
        Integer id, // Audit log ID, nullable for creation

        @JsonProperty
        @NotNull(message = "Timestamp cannot be null")
        @PastOrPresent(message = "Timestamp cannot be in the future")
        LocalDateTime timestamp, // The time when the action occurred

        @JsonProperty
        @NotEmpty(message = "Action cannot be empty")
        String action, // The action performed (e.g., "CREATE", "UPDATE", etc.)

        @JsonProperty
        @NotNull(message = "User cannot be null")
        int userId // The user who performed the action
) {}
