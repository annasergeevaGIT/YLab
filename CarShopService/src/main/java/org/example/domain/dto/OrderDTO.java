package org.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.example.domain.model.OrderStatus;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Order.
 */
public record OrderDTO(
        @JsonProperty
        int id, // Order ID; since it's a primitive, it cannot be null, so no validation needed

        @JsonProperty
        int carId, // Car associated with the order

        @JsonProperty
        int userId, // User who placed the order

        @JsonProperty
        @NotNull(message = "The status cannot be null")
        OrderStatus status, // The status of the order, should not be null

        @JsonProperty
        @PastOrPresent(message = "The date cannot be in the future")
        LocalDateTime createdAt // The creation date of the order
) {}
