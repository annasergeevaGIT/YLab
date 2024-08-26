package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.dto.OrderDTO;
import org.example.domain.model.*;
import org.example.repository.CarRepository;
import org.example.repository.OrderRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Service for managing orders.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Service
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
     * @param orderDTO     the ID of the car to order
     * @return the created order if successful, else throws an exception
     */
    public Order createOrder(OrderDTO orderDTO) {
        Car car = carRepository.findById(orderDTO.carId());
        User user = userRepository.findById(orderDTO.userId());

        if (car != null && user != null && "AVAILABLE".equalsIgnoreCase(String.valueOf(car.getStatus()))) {
            Order newOrder = new Order(car.getId(), user.getId(), OrderStatus.PENDING);
            orderRepository.create(newOrder);

            // Mark the car as unavailable and update its status in the repository
            car.setStatus(CarStatus.RESERVED);
            carRepository.update(car);
            // Log the creation of the order
            auditService.logAction(user.getId(), "Created order for car: " + car.getBrand() + " " + car.getModel());
            return newOrder;
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

    public Order updateOrderStatus(int orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId);
        User user = userRepository.findById(orderId);
        Car car = carRepository.findById(order.getCarId());
        if (order != null) {
            order.setStatus(status);
            orderRepository.update(order);
            auditService.logAction(user.getId(), "Updated order status to " + status + " for order ID: " + orderId);
            if (status == OrderStatus.CANCELLED) {
                car.setStatus(CarStatus.AVAILABLE);
            } else if (status == OrderStatus.COMPLETED) {
                car.setStatus(CarStatus.SOLD);
                //Add the completed order to the users list of orders
                List<Order> userOrders = user.getOrders();
                if (userOrders == null) {
                    userOrders = new ArrayList<Order>();
                }
                userOrders.add(order);
                user.setOrders(userOrders);
            }
            carRepository.update(car);
            auditService.logAction(user.getId(), "Updated car, set new status " + car.getStatus());
            return order;
        }
        return null;
    }

    /**
     * Cancel the order.
     *
     * @param orderId order ID
     */
    public void cancelOrder(int orderId) {
        Order order = orderRepository.findById(orderId);
        User user = userRepository.findById(orderId);
        Car car = carRepository.findById(order.getCarId());
        if (order != null) {
            order.setStatus(OrderStatus.CANCELLED);
            car.setStatus(CarStatus.AVAILABLE);
            orderRepository.update(order);
            carRepository.update(car);
            auditService.logAction(user.getId(), "Cancelled order ID: " + orderId + "User ID:" + user.getId()); // Audit log
        }
    }

    public Order getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId);
        return order;
    }
}
