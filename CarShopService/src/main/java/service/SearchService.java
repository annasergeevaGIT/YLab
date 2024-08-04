package service;

import model.*;
import repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SearchService {
    private CarRepository carRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private AuditRepository auditRepository;

    public SearchService(CarRepository carRepository, OrderRepository orderRepository, UserRepository userRepository, AuditRepository auditRepository) {
        this.carRepository = carRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.auditRepository = auditRepository;
    }

    public List<Car> searchCars(String brand, String model, Integer year, Double price, CarStatus status) {
        return carRepository.findAll().stream()
                .filter(car -> (brand == null || car.getBrand().equalsIgnoreCase(brand)) &&
                        (model == null || car.getModel().equalsIgnoreCase(model)) &&
                        (year == null || car.getYear() == year) &&
                        (price == null || car.getPrice() == price) &&
                        (status == null || car.getStatus() == status))
                .collect(Collectors.toList());
    }

    public List<Order> searchOrders(Integer customerId, OrderStatus status, Integer carId) {
        return orderRepository.findAll().stream()
                .filter(order -> (customerId == null || order.getUser().getId() == customerId) &&
                        (status == null || order.getStatus() == status) &&
                        (carId == null || order.getCar().getId() == carId))
                .collect(Collectors.toList());
    }
    public List<User> filterUsersByRole(String role) {
        return userRepository.findAll().stream()
                .filter(user -> role == null || user.getRole().toString().equalsIgnoreCase(role))
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