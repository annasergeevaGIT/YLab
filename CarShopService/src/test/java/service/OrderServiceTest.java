package service;

import out.Order;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import out.OrderRepository;
import out.OrderService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    public void placeOrder_success() {
        Order order = new Order("Order1", "Toyota Corolla", new User("john_doe", "password", Role.CUSTOMER));
        orderService.placeOrder(order);

        verify(orderRepository, times(1)).addOrder(order);
    }

    @Test
    public void listOrders_returnsOrders() {
        Order order1 = new Order("Order1", "Toyota Corolla", new User("john_doe", "password", Role.CUSTOMER));
        Order order2 = new Order("Order2", "Honda Civic", new User("jane_doe", "password", Role.CUSTOMER));
        when(orderRepository.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.listOrders();

        assertThat(orders).hasSize(2);
        assertThat(orders).contains(order1, order2);
    }
}
