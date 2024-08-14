package org.example.service;

import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.example.repository.CarRepository;
import org.example.repository.OrderRepository;
import org.example.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("OrderService Tests")
public class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private CarRepository carRepository;
    private UserRepository userRepository;
    private AuditService auditService;

    @BeforeEach
    @DisplayName("Set up OrderService and repository mocks")
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        carRepository = mock(CarRepository.class);
        userRepository = mock(UserRepository.class);
        auditService = mock(AuditService.class);
        orderService = new OrderService(orderRepository, carRepository, userRepository, auditService);
    }

    @Test
    @DisplayName("Test getAllOrders() - Should return all orders")
    void testGetAllOrders() {
        Car car = new Car(1, "Toyota", "Camry", 2020, 25000, CarStatus.AVAILABLE);
        User user = new User(1, "customer", "password", UserRole.CUSTOMER,null);
        Order order1 = new Order(1, car, user, OrderStatus.PENDING, LocalDateTime.now());
        Order order2 = new Order(2, car, user, OrderStatus.APPROVED, LocalDateTime.now());
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getAllOrders();

        assertThat(orders).hasSize(2);
        assertThat(orders).contains(order1, order2);
    }

    @Test
    @DisplayName("Test createOrder() - Should create a new order")
    void testCreateOrder() {
        Car car = new Car(1, "Toyota", "Camry", 2020, 25000, CarStatus.AVAILABLE);
        User customer = new User(1, "customer", "password", UserRole.CUSTOMER,null);
        when(carRepository.findById(1)).thenReturn(car);
        when(userRepository.findById(1)).thenReturn(customer);

        orderService.createOrder(1, 1);

        verify(orderRepository, times(1)).create(any(Order.class));
        verify(auditService, times(1)).logAction(customer, "Created order for car: Toyota Camry");
    }
}
