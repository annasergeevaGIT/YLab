package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents the Order in the system.
 */
@AllArgsConstructor
@Data
public class Order {
    private int id;
    private Car car;
    private User user;
    private OrderStatus status;

    /**
     *
     * @param id     the unique identifier for the user
     * @param car
     * @param user
     * @param status represents the order status (PENDING, APPROVED, CANCELLED, COMPLETED)
     */
}
