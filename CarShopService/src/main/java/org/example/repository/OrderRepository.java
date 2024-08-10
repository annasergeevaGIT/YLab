package org.example.repository;

import org.example.model.Order;

import java.util.*;
/**
 * Repository for managing orders.
 */
public class OrderRepository {
    private Map<Integer,Order> orders = new HashMap<>();

    /**
     * Saves an order to the repository.
     *
     * @param order the order to save
     */
    public void create(Order order) {
        orders.put(order.getId(), order);
    }

    /**
     * Returns a list of all orders.
     *
     * @return the list of orders
     */
    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    public Order findById(int id) {
        return orders.get(id);
    }

    public void update(Order order) {
        Order existingOrder = findById(order.getId());
        if (existingOrder != null) {
            existingOrder.setStatus(order.getStatus());
        }
    }
    public void delete(int id) {
        orders.remove(id);
    }
}


