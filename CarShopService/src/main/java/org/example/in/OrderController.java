package org.example.in;

import lombok.AllArgsConstructor;
import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.model.User;
import org.example.service.AuthService;
import org.example.service.OrderService;

import java.util.List;
import java.util.Scanner;
/**
 * Controller for managing user input to perform order manipulation.
 */
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;
    private AuthService authService;

    /**
     * Print an ArrayList with all orders.
     */
    public void listOrders() {
        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            System.out.printf("ID: %d, Car: %s %s, User: %s, Status: %s%n",
                    order.getId(),
                    order.getCar().getBrand(),
                    order.getCar().getModel(),
                    order.getUser().getUsername(),
                    order.getStatus());
        }
    }

    /**
     * Managing user input to create a new order.
     */
    public void createOrder() {
        User loggedInUser = authService.getCurrentUser();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input car ID: ");
        int carId = scanner.nextInt();
        orderService.createOrder(carId, loggedInUser.getId());
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

