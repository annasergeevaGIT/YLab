package org.example.controller;

import org.example.domain.dto.OrderDTO;
import org.example.domain.model.Order;
import org.example.domain.model.OrderStatus;
import org.example.mapper.OrderMapper;
import org.example.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testGetAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of()); // Adjust as necessary

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void testCreateOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO(1,101,202, OrderStatus.PENDING, LocalDateTime.now()); // Populate as necessary

        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(new Order()); // Adjust as necessary
        when(orderMapper.toDTO(any(Order.class))).thenReturn(orderDTO);

        mockMvc.perform(post("/api/orders")
                        .contentType("application/json")
                        .content("{ \"carId\": 1, \"userId\": 1 }")) // Adjust as necessary
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void testUpdateOrderStatus() throws Exception {
        OrderDTO orderDTO = new OrderDTO(1,2,3,OrderStatus.PENDING,LocalDateTime.now());
        when(orderService.updateOrderStatus(anyInt(), any(OrderStatus.class))).thenReturn(new Order()); // Adjust as necessary
        when(orderMapper.toDTO(any(Order.class))).thenReturn(orderDTO);

        mockMvc.perform(patch("/api/orders/1/status")
                        .contentType("application/json")
                        .content("{ \"status\": \"SHIPPED\" }")) // Adjust as necessary
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void testCancelOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());

        verify(orderService, times(1)).cancelOrder(anyInt());
    }
}
