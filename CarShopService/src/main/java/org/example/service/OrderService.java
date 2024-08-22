package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.OrderDTO;
import org.example.model.*;
import org.example.repository.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Service for managing orders.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderService {
    private OrderRepository orderRepository;
    private CarRepository carRepository;
    private UserRepository userRepository;
    private AuditService auditService;


    /**
     * Get all existing orders
     * @return the repository for orders
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Creates a new order.
     *
     * @param carId     the ID of the car to order
     * @param customerId the ID of the customer placing the order
     * @return the created order if successful, else throws an exception
     */
    public void createOrder(int carId, int customerId) {
        Car car = carRepository.findById(carId);
        User user = userRepository.findById(customerId);

        if (car != null && user != null && "AVAILABLE".equalsIgnoreCase(String.valueOf(car.getStatus()))) {
            Order newOrder = new Order(car, user, OrderStatus.PENDING);
            orderRepository.create(newOrder);

            // Mark the car as unavailable and update its status in the repository
            car.setStatus(CarStatus.RESERVED);
            carRepository.update(car);
            // Log the creation of the order
            auditService.logAction(user, "Created order for car: " + car.getBrand() + " " + car.getModel());
        } else {
            throw new IllegalArgumentException("Car or customer not found.");
        }
    }

    /**
     * Update the existing order.
     *
     * @param orderId order ID
     * @param status  new status of the order (AVAILABLE, RESERVED, SOLD)
     */

    public void updateOrderStatus(int orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId);
        User user = order.getUser();
        if (order != null) {
            order.setStatus(status);
            orderRepository.update(order);
            // Log
            auditService.logAction(user, "Updated order status to " + status + " for order ID: " + orderId);
            if (status == OrderStatus.CANCELLED) {
                order.getCar().setStatus(CarStatus.AVAILABLE);
            } else if (status == OrderStatus.COMPLETED) {
                order.getCar().setStatus(CarStatus.SOLD);
                //Add the completed order to the users list of orders
                List<Order> userOrders = user.getOrders();
                if (userOrders == null) {
                    userOrders = new ArrayList<Order>();
                }
                userOrders.add(order);
                user.setOrders(userOrders);
            }
            carRepository.update(order.getCar());
            auditService.logAction(user, "Updated car, set new status " + order.getCar().getStatus());
        }
    }

    /**
     * Cancel the order.
     *
     * @param orderId order ID
     */
    public void cancelOrder(int orderId) {
        Order order = orderRepository.findById(orderId);
        User user = order.getUser();
        if (order != null) {
            order.setStatus(OrderStatus.CANCELLED);
            order.getCar().setStatus(CarStatus.AVAILABLE);
            orderRepository.update(order);
            carRepository.update(order.getCar());
            auditService.logAction(user, "Cancelled order ID: " + orderId + "User ID:" + user.getId()); // Audit log
        }
    }

    public Order getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId);
        return order;
    }
}
