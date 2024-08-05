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
     * Constructs a new Order with the specified id, car, customer, and status.
     *
     * @param id       the order's ID
     * @param car      the car associated with the order
     * @param user     the user who placed the order
     * @param status   the order's status
     */
}
