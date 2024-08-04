package repository;

import model.Order;

import java.util.ArrayList;
import java.util.List;
/**
 * Repository for managing orders.
 */
public class OrderRepository {
    private List<Order> orders = new ArrayList<>();
    private int nextId = 1;

    /**
     * Returns a list of all orders.
     *
     * @return the list of orders
     */
    public List<Order> findAll() {
        return orders;
    }

    public Order findById(int id) {
        return orders.stream().filter(order -> order.getId() == id).findFirst().orElse(null);
    }
    /**
     * Saves an order to the repository.
     *
     * @param order the order to save
     */
    public void save(Order order) {
        order.setId(nextId++);
        orders.add(order);
    }

    public void update(Order order) {
        Order existingOrder = findById(order.getId());
        if (existingOrder != null) {
            existingOrder.setStatus(order.getStatus());
        }
    }

    /**
     * Gets the next order ID.
     *
     * @return the next order ID
     */
    public int getNextId() {
        return nextId;
    }
}


