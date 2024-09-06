package org.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.model.CarStatus;

/**
 * Data Transfer Object for Car. To transfer info about a car between different layers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
        @JsonProperty
        @NotEmpty(message = "Brand cannot be empty")
        private String brand;

        @JsonProperty
        @NotEmpty(message = "Model cannot be empty")
        private String model;

        @JsonProperty
        @PastOrPresent(message = "Year cannot be in the future")
        private int year;

        @JsonProperty
        @Positive(message = "Price must be a positive value")
        private double price;

        @JsonProperty
        @NotNull(message = "Status cannot be null")
        private CarStatus status; // This should match CarStatus enum values
}