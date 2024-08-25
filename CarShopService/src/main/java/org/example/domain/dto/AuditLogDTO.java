package org.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.example.domain.model.CarStatus;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Car. To transfer info about a car between different layers.
 *
 */
public record AuditLogDTO (
        Integer id,
        LocalDateTime timestamp,
        String action,
        Integer userId
        ){}