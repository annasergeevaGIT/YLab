package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.*;
import org.example.repository.AuditRepository;
import org.example.repository.*;
import org.example.util.AuditLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service to perform search in the car, user and order repositories.
 */
@AllArgsConstructor
@Data
public class SearchService {
    private CarRepository carRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private AuditRepository auditRepository;

    /**
     * Search cas by different parameters.
     *
     * @param brand
     * @param model
     * @param year
     * @param price
     * @param status car status (AVAILABLE, RESERVED, SOLD)
     * @return the list of cars found
     */
    public List<Car> searchCars(String brand, String model, Integer year, Double price, CarStatus status) {
        return carRepository.findAll().stream()
                .filter(car -> (brand == null || car.getBrand().equalsIgnoreCase(brand)) &&
                        (model == null || car.getModel().equalsIgnoreCase(model)) &&
                        (year == null || car.getYear() == year) &&
                        (price == null || car.getPrice() == price) &&
                        (status == null || car.getStatus() == status))
                .collect(Collectors.toList());
    }

    /**
     * Search Orders by different parameters.
     *
     * @param customerId user ID
     * @param status     order status (PENDING, APPROVED, CANCELLED, COMPLETED)
     * @param carId      car ID
     * @return the list of orders found
     */
    public List<Order> searchOrders(Integer customerId, OrderStatus status, Integer carId) {
        return orderRepository.findAll().stream()
                .filter(order -> (customerId == null || order.getUser().getId() == customerId) &&
                        (status == null || order.getStatus() == status) &&
                        (carId == null || order.getCar().getId() == carId))
                .collect(Collectors.toList());
    }

    /**
     * Search users by role.
     *
     * @param role user role (ADMIN, MANAGER, CUSTOMER)
     * @return the list of users found
     */
    public List<User> searchUsers(Integer userId, String username, UserRole role, Integer orderCount) {
        return userRepository.findAll().stream()
                .filter(user -> (userId == null || user.getId() == userId) &&
                        (username == null || user.getUsername().equalsIgnoreCase(username)) &&
                        (role == null || user.getRole() == role) &&
                        (orderCount == null || user.getOrders().size() == orderCount))
                .collect(Collectors.toList());
    }

    /**
     * Gets the purchase count for a user. *should be updated to stream
     *
     * @param userId the ID of the user
     * @return the number of purchases made by the user
     */
    public int getPurchaseCount(int userId) {
        return (int) orderRepository.findAll().stream()
                .filter(order -> order.getUser().getId() == userId && order.getStatus() == OrderStatus.COMPLETED)
                .count();
    }

    /**
     * Returns a list of audit log entries filtered by date, user, and action type.
     *
     * @param startDate the start date for filtering
     * @param endDate   the end date for filtering
     * @param user      the user to filter by (can be null)
     * @param action    the action type to filter by (can be null)
     * @return the list of filtered audit log entries
     */
    public List<AuditLog> filterLogs(LocalDateTime startDate, LocalDateTime endDate, User user, String action) {
        return auditRepository.findAll().stream()
                .filter(log -> (startDate == null || !log.getTimestamp().isBefore(startDate)) &&
                        (endDate == null || !log.getTimestamp().isAfter(endDate)) &&
                        (user == null || log.getUser().equals(user)) &&
                        (action == null || log.getAction().equalsIgnoreCase(action)))
                .collect(Collectors.toList());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}