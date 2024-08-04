package in;

import model.*;
import service.AuditService;
import service.SearchService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class SearchController {
    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

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
        System.out.print("Input car status (or leave empty): ");
        String status = scanner.nextLine();

        List<Car> cars = searchService.searchCars(brand.isEmpty() ? null : brand, model.isEmpty() ? null : model,
                year, price, status.isEmpty() ? null : CarStatus.valueOf(status));

        for (Car car : cars) {
            System.out.println("ID: " + car.getId() + ", Марка: " + car.getBrand() + ", Модель: " + car.getModel() +
                    ", Год: " + car.getYear() + ", Цена: " + car.getPrice() + ", Статус: " + car.getStatus());
        }
    }

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
            System.out.println(order.getId() + ", Car: " + order.getCar().getBrand() + " " + order.getCar().getModel() +
                    ", User: " + order.getUser().getUsername() + ", Status: " + order.getStatus());
        }
    }

    public void filterUsersByRole() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose user role (CUSTOMER, USER, ADMIN) or leave empty: ");
        String role = scanner.nextLine();

        List<User> users = searchService.filterUsersByRole(role.isEmpty() ? null : role);

        for (User user : users) {
            System.out.println(user);
        }
    }

    public int sortUsersByPurchases() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose car mark (or leave empty): ");
        int userId = scanner.nextInt();
        return searchService.getPurchaseCount(userId);
    }

    public void filterAuditLogs() {
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
}
