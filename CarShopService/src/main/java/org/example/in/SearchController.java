package org.example.in;

import org.example.model.*;
import org.example.service.AuditService;
import org.example.service.SearchService;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
/**
 * Controller for searching and exporting logs.
 */
public class SearchController {
    private SearchService searchService;
    private AuditService auditService;

    /**
     * Constructs a SearchController with the specified AuditService.
     *
     * @param auditService the service for managing audit logs
     */
    public SearchController(SearchService searchService, AuditService auditService) {
        this.searchService = searchService;
        this.auditService = auditService;
    }

    /**
     * Managing user input to search cars by different parameters.
     */
    public void searchCars() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input car mark (or leave empty): ");
        String brand = scanner.nextLine();
        System.out.print("Input car model (or leave empty): ");
        String model = scanner.nextLine();
        System.out.print("Input car year (or leave empty): ");
        Integer year = null;
        String yearStr = scanner.nextLine();
        if (!yearStr.isEmpty()) {
            year = Integer.parseInt(yearStr);
        }
        System.out.print("Input car price (or leave empty): ");
        Double price = null;
        String priceStr = scanner.nextLine();
        if (!priceStr.isEmpty()) {
            price = Double.parseDouble(priceStr);
        }
        System.out.print("Input car status (AVAILABLE, RESERVED, SOLD or leave empty): ");
        String status = scanner.nextLine();

        List<Car> cars = searchService.searchCars(brand.isEmpty() ? null : brand, model.isEmpty() ? null : model,
                year, price, status.isEmpty() ? null : CarStatus.valueOf(status));

        for (Car car : cars) {
            System.out.println(car);
        }
    }

    /**
     * Managing user input to search orders by different parameters.
     */
    public void searchOrders() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input User ID (or leave empty): ");
        Integer customerId = null;
        String customerIdStr = scanner.nextLine();
        if (!customerIdStr.isEmpty()) {
            customerId = Integer.parseInt(customerIdStr);
        }
        System.out.print("Input order status (or leave empty): ");
        OrderStatus status = null;
        String statusStr = scanner.nextLine();
        if (!statusStr.isEmpty()) {
            status = OrderStatus.valueOf(statusStr.toUpperCase());
        }
        System.out.print("Input car ID (or leave empty): ");
        Integer carId = null;
        String carIdStr = scanner.nextLine();
        if (!carIdStr.isEmpty()) {
            carId = Integer.parseInt(carIdStr);
        }

        List<Order> orders = searchService.searchOrders(customerId, status, carId);

        for (Order order : orders) {
            System.out.printf("Order ID: %d, Car: %s %s, User: %s, Status: %s\n",
                    order.getId(),
                    order.getCar().getBrand(),
                    order.getCar().getModel(),
                    order.getUser().getUsername(),
                    order.getStatus());
        }
    }

    /**
     * Managing user input to search cars by different parameters.
     */
    public void searchUsers() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input user Id (or leave empty): ");
        Integer userId = null;
        String userIdStr = scanner.nextLine();
        if (!userIdStr.isEmpty()) {
            userId = Integer.parseInt(userIdStr);
        }

        System.out.print("Input user name (or leave empty): ");
        String username = scanner.nextLine();

        System.out.print("Input user role (ADMIN, MANAGER, CUSTOMER or leave empty): ");
        String role = scanner.nextLine();

        System.out.print("Input amount of completed orders (or leave empty): ");
        Integer orderCount = null;
        String orderStr = scanner.nextLine();
        if (!orderStr.isEmpty()) {
            orderCount = Integer.parseInt(orderStr);
        }

        List<User> users = searchService.searchUsers(userId, username.isEmpty() ? null : username,
                role.isEmpty() ? null : UserRole.valueOf(role), orderCount);

        for (User user : users) {
            System.out.printf("User ID: %d, Username: %s, Status %s\n",
                    user.getId(),
                    user.getUsername(),
                    user.getRole());

            List<Order> orders = user.getOrders();
            if (orders == null || orders.isEmpty()) {
                System.out.println("  No orders found.");
            } else {
                System.out.println("  Orders:");
                for (Order order : orders) {
                    System.out.printf("    Order ID: %d, Status: %s, Car: %s\n",
                            order.getId(),
                            order.getStatus(),
                            order.getCar() != null ? order.getCar().toString() : "No car details");
                }
            }
        }
    }

    /**
     * Managing user input to filter audit logs.
     */
    public void searchAuditLogs() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter start date (yyyy-MM-dd HH:mm:ss) or press Enter to skip:");
        String startDateInput = scanner.nextLine();
        LocalDateTime startDate = startDateInput.isEmpty() ? null : LocalDateTime.parse(startDateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("Enter end date (yyyy-MM-dd HH:mm:ss) or press Enter to skip:");
        String endDateInput = scanner.nextLine();
        LocalDateTime endDate = endDateInput.isEmpty() ? null : LocalDateTime.parse(endDateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("Enter username or press Enter to skip:");
        String username = scanner.nextLine();
        User user = username.isEmpty() ? null : searchService.findByUsername(username);

        System.out.println("Enter action type or press Enter to skip:");
        String action = scanner.nextLine();
        action = action.isEmpty() ? null : action;

        List<AuditLog> filteredLogs = searchService.filterLogs(startDate, endDate, user, action);
            for (AuditLog log : filteredLogs) {
            System.out.println(log);
        }
    }

    /**
     * Exports audit logs to a file.
     */
    public void exportLogs() {
        List<AuditLog> logs = auditService.getAllLogs();

        try (FileWriter writer = new FileWriter("audit_logs.txt")) {
            for (AuditLog log : logs) {
                writer.write(log.toString() + "\n");
            }
            System.out.println("Log files successfully exported to audit_logs.txt");
        } catch (IOException e) {
            System.out.println("Error while exporting log files: " + e.getMessage());
        }
    }
}
