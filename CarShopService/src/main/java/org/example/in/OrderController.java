package org.example.in;

import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.service.OrderService;

import java.util.List;
import java.util.Scanner;
/**
 * Controller for managing user input to perform order manipulation.
 */
public class OrderController {
    private OrderService orderService;
    /**
     * Constructs an OrderController with the specified OrderService and AuthService.
     *
     * @param orderService the service for managing orders
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void listOrders() {
        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            System.out.println("ID: " + order.getId() + ", Car: " + order.getCar().getBrand() + " " + order.getCar().getModel() + ", Customer: " + order.getUser().getUsername() + ", Статус: " + order.getStatus());
        }
    }

    /**
     * Managing user input to create a new order.
     *
     * @param customerId
     */
    public void createOrder(int customerId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input car ID: ");
        int carId = scanner.nextInt();
        orderService.createOrder(carId, customerId);
        System.out.println("Order created!.");
    }

    /**
     * Managing user input to update the status of an order.
     */
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

    /**
     * Managing user input to cancel an order.
     */
    public void cancelOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input order ID: ");
        int orderId = scanner.nextInt();

        orderService.cancelOrder(orderId);
        System.out.println("Order is cancelled.");
    }
}

