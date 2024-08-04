package service;

import model.*;
import repository.*;

import java.util.List;
/**
 * Service for managing orders.
 */
public class OrderService {
    private OrderRepository orderRepository;
    private CarRepository carRepository;
    private UserRepository userRepository;
    private AuditService auditService;

    /**
     * Constructs an OrderService.
     *
     * @param orderRepository the repository for orders
     * @param carRepository   the repository for cars
     * @param userRepository  the repository for users
     * @param auditService    the service for logging actions
     */

    public OrderService(OrderRepository orderRepository, CarRepository carRepository, UserRepository userRepository, AuditService auditService) {
        this.orderRepository = orderRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

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
     * @return the created order
     */
    public void createOrder(int carId, int customerId) {
        Car car = carRepository.findById(carId);
        User user = userRepository.findById(customerId);

        if (car != null && user != null && "AVAILABLE".equalsIgnoreCase(String.valueOf(car.getStatus()))) {
            Order newOrder = new Order(orderRepository.getNextId(), car, user, OrderStatus.PENDING);
            orderRepository.save(newOrder);

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
     * Update Order
     * @param orderId
     * @param status
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
            }
            carRepository.update(order.getCar());
        }
    }

    /**
     * Cancel Order
     * @param orderId
     */
    public void cancelOrder(int orderId) {
        Order order = orderRepository.findById(orderId);
        User user = order.getUser();
        if (order != null) {
            order.setStatus(OrderStatus.CANCELLED);
            order.getCar().setStatus(CarStatus.AVAILABLE);
            orderRepository.update(order);
            carRepository.update(order.getCar());
            // Log
            auditService.logAction(user, "Cancelled order ID: " + orderId);
        }
    }
}
