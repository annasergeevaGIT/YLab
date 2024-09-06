package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dto.OrderDTO;
import org.example.domain.model.Order;
import org.example.domain.model.OrderStatus;
import org.example.mapper.OrderMapper;
import org.example.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("order controller test")
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    @Autowired
    private OrderController orderController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testGetAllOrders() throws Exception {
        when(orderService.getAll()).thenReturn(List.of()); // Adjust as necessary

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @DisplayName("test create new order")
    void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO(2, 1, OrderStatus.APPROVED, LocalDate.parse("2024-08-12"));
        String orderJson = objectMapper.writeValueAsString(orderDTO);
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("test update order status to 'CANCELLED'")
    void canceled() throws Exception {
        mockMvc.perform(put("/orders/canceled")
                        .param("id", "2"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCancelOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());
        verify(orderService, times(1)).canceled(anyInt());
    }
}
