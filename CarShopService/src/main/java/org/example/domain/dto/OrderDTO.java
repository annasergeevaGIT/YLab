package org.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import org.example.domain.model.*;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Order.
 */
public record OrderDTO (
    @JsonProperty
    @NotEmpty(message = "Order ID cannot be empty")
    int id,
    @NotEmpty(message = "Car ID cannot be empty")
    int carId,
    @NotEmpty(message = "User ID cannot be empty")
    int userId,
    @NotEmpty(message = "The status cannot be empty")
    OrderStatus status,
    @PastOrPresent(message = "The date cannot be in the future")
    LocalDateTime createdAt
){}
