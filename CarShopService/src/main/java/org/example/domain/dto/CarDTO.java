package org.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.example.domain.model.CarStatus;
import org.hibernate.validator.constraints.UniqueElements;

/**
 * Data Transfer Object for Car. To transfer info about a car between different layers.
 *
 */
public record CarDTO (
        @JsonProperty
        @NotEmpty(message = "Car ID cannot be empty")
        int id, // For update operations
        @NotEmpty(message = "Brand cannot be empty")
        String brand,
        @NotEmpty(message = "Model cannot be empty")
        String model,
        @PastOrPresent(message = "Year cannot be in the future")
        int year,
        @Positive(message = "Price must be a positive value")
        double price,
        @NotNull(message = "Status cannot be null")
        CarStatus status // This should match CarStatus enum values
){}
