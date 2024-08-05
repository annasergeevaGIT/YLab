package in;

import model.*;
import service.OrderService;

import java.util.List;
import java.util.Scanner;

public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void listOrders() {
        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            System.out.println("ID: " + order.getId() + ", Car: " + order.getCar().getBrand() + " " + order.getCar().getModel() + ", Customer: " + order.getUser().getUsername() + ", Статус: " + order.getStatus());
        }
    }

    public void createOrder(int customerId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID автомобиля: ");
        int carId = scanner.nextInt();
        orderService.createOrder(carId, customerId);
        System.out.println("Заказ создан.");
    }

    public void updateOrderStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input order ID: ");
        int orderId = scanner.nextInt();
        System.out.print("Input new order status (PENDING, APPROVED, CANCELLED, COMPLETED): ");
        String statusStr = scanner.next();
        OrderStatus status = OrderStatus.valueOf(statusStr.toUpperCase());

        orderService.updateOrderStatus(orderId, status);
        System.out.println("Order is updated.");
    }

    public void cancelOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input order ID: ");
        int orderId = scanner.nextInt();

        orderService.cancelOrder(orderId);
        System.out.println("Order is cancelled.");
    }
}

