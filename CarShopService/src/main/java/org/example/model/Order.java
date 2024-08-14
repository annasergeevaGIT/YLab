package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents the Order in the system.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private int id;
    private Car car;
    private User user;
    private OrderStatus status;
    private LocalDateTime createdAt; // New field for storing the order's creation time

    /**
     * Constructs a new Order with the specified car, user, and status.
     * The creation time is set to the current time.
     *
     * @param car      the car associated with the order
     * @param user     the user who placed the order
     * @param status   the order's status
     */
    public Order(Car car, User user, OrderStatus status) {
        this.car = car;
        this.user = user;
        this.status = status;
        this.createdAt = LocalDateTime.now(); // Automatically set the creation time to now
    }
}
