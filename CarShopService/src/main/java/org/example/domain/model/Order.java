package org.example.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

import java.time.LocalDateTime;


/**
 * Represents the Order in the system.
 */
@RequiredArgsConstructor
@Data
@Table(name = "orders", schema = "entity_schema")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_seq")
    @SequenceGenerator(schema = "entity_schema",
            name = "order_seq", // referencing GeneratedValue annotation
            sequenceName = "entity_schema.order_id_seq",  // name and schema
            allocationSize = 1)
    private int id;
    @Column(name = "car_id")
    @NotEmpty(message = "car ID could not be empty")
    private int carId;
    @Column(name = "user_id")
    @NotEmpty(message = "user ID could not be empty")
    private int userId;
    private OrderStatus status;
    private LocalDateTime createdAt; // New field for storing the order's creation time

    /**
     * Constructs a new Order with the specified car, user, and status.
     * The creation time is set to the current time.
     *
     * @param carId      the car associated with the order
     * @param userId     the user who placed the order
     * @param status   the order's status
     */
    public Order(int carId, int userId, OrderStatus status) {
        this.carId = carId;
        this.userId = userId;
        this.status = status;
        this.createdAt = LocalDateTime.now(); // Automatically set the creation time to now
    }
}
