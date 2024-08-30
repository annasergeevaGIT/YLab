package org.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.example.domain.model.CarStatus;

/**
 * Data Transfer Object for Car. To transfer info about a car between different layers.
 */
public record CarDTO(
        @JsonProperty
        int id, // Car ID, no validation needed for primitive int

        @JsonProperty
        @NotEmpty(message = "Brand cannot be empty")
        String brand,

        @JsonProperty
        @NotEmpty(message = "Model cannot be empty")
        String model,

        @JsonProperty
        @PastOrPresent(message = "Year cannot be in the future")
        int year,

        @JsonProperty
        @Positive(message = "Price must be a positive value")
        double price,

        @JsonProperty
        @NotNull(message = "Status cannot be null")
        CarStatus status // This should match CarStatus enum values
) {}
