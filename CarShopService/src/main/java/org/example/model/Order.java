package org.example.model;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Represents the Order in the system.
 */
@AllArgsConstructor
@Data
public class Order {
    private int id;
    private static int idCounter = 1;
    private Car car;
    private User user;
    private OrderStatus status;

    /**
     * Constructs a new Order with the specified id, car, customer, and status.
     *
     * @param car      the car associated with the order
     * @param user     the user who placed the order
     * @param status   the order's status
     */
    public Order(Car car, User user, OrderStatus status) {
        this.id = idCounter;
        this.idCounter++;
        this.car = car;
        this.user = user;
        this.status = status;
    }


}
