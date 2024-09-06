package org.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.model.OrderStatus;

import java.time.LocalDate;

/**
 * Data Transfer Object for Order.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

        private int carId; // Car associated with the order

        private int userId; // User who placed the order

        @NotNull(message = "The status cannot be null")
        private OrderStatus status; // The status of the order, should not be null

        @PastOrPresent(message = "The date cannot be in the future")
        private LocalDate createdAt; // The creation date of the order
}
