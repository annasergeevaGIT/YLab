package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dto.OrderDTO;
import org.example.domain.model.*;
import org.example.model.*;
import org.example.service.OrderService;
import org.example.util.DTOValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

/**
 * Tests for the `OrderServlet` class.
 */
public class OrderServletTest {

    @InjectMocks
    private OrderServlet orderServlet;

    @Mock
    private OrderService orderService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private DTOValidator dtoValidator;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter writer;
    /**
     * Sets up the test environment by initializing mocks and configuring common behavior.
     */
    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this); // Initialize mocks and configure the common behavior for all tests.
        when(response.getWriter()).thenReturn(writer);
    }
    /**
     * Tests that a successful response is returned when a valid order ID is provided
     * and the user is authorized to view the order.
     */
    @Test
    void testDoGet_OrderById_Success() throws Exception {
        User user = new User(1, "root", "root", UserRole.ADMIN, null);
        Order order = new Order(new Car(1, "GAZ-13", "Chaika", 2023, 30000, CarStatus.AVAILABLE), user, OrderStatus.PENDING);

        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn("1");
        when(orderService.getOrderById(1)).thenReturn(order);
        when(objectMapper.writeValueAsString(order)).thenReturn("{\"id\":1}");
        // Verify that the servlet returns the correct order details when valid input is provided.
        orderServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(writer).write("{\"id\":1}");
    }
    /**
     * Tests that a NOT_FOUND response is returned when the order with the provided ID does not exist.
     */
    @Test
    void testDoGet_OrderById_NotFound() throws Exception {
        User user = new User(1, "root", "root", UserRole.ADMIN, null);

        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn("1");
        when(orderService.getOrderById(1)).thenReturn(null);

        orderServlet.doGet(request, response);
        // Verify that the servlet responds with NOT_FOUND when the order is not found.
        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(writer).write("Order not found");
    }

    @Test
    void testDoGet_UnauthorizedUser() throws Exception {
        when(request.getSession().getAttribute("user")).thenReturn(null);

        orderServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(writer).write("Access denied");
    }

    @Test
    void testDoGet_AllOrders_Success() throws Exception {
        User user = new User(1, "root", "root",  UserRole.ADMIN, null);
        List<Order> orders = List.of(new Order(new Car(1, "GAZ-13", "Chaika", 2023, 30000, CarStatus.AVAILABLE), user, OrderStatus.PENDING));

        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(orderService.getAllOrders()).thenReturn(orders);
        when(objectMapper.writeValueAsString(orders)).thenReturn("[{\"id\":1}]");

        orderServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(writer).write("[{\"id\":1}]");
    }

    @Test
    void testDoGet_UnauthorizedCustomer() throws Exception {
        User user = new User(2, "anna", "anna",  UserRole.CUSTOMER, null);

        when(request.getSession().getAttribute("user")).thenReturn(user);

        orderServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(writer).write("Access denied");
    }

    @Test
    void testDoPost_Success() throws Exception {
        User user = new User(2, "anna", "anna", UserRole.CUSTOMER, null);
        OrderDTO orderDTO = new OrderDTO(1, OrderStatus.PENDING);
        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(objectMapper.readValue(request.getReader(), OrderDTO.class)).thenReturn(orderDTO);
        when(dtoValidator.validateOrderDTO(orderDTO)).thenReturn(new ArrayList<>());

        doNothing().when(orderService).createOrder(orderDTO.getId(), user.getId());

        orderServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(writer).write("Order created successfully");
    }

    @Test
    void testDoPost_ValidationErrors() throws Exception {
        User user = new User(2, "anna", "anna", UserRole.CUSTOMER, null);
        OrderDTO orderDTO = new OrderDTO(1, OrderStatus.PENDING);
        List<String> errors = List.of("Invalid data");
        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(objectMapper.readValue(request.getReader(), OrderDTO.class)).thenReturn(orderDTO);
        when(dtoValidator.validateOrderDTO(orderDTO)).thenReturn(errors);

        orderServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(writer).write("Invalid data");
    }

    @Test
    void testDoPost_UnauthorizedUser() throws Exception {
        when(request.getSession().getAttribute("user")).thenReturn(new User(1, "root", "root", UserRole.ADMIN, null));

        orderServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(writer).write("Access denied");
    }

    @Test
    void testDoPut_Success() throws Exception {
        User user = new User(1, "root", "root", UserRole.ADMIN, null);
        OrderDTO orderDTO = new OrderDTO(1, OrderStatus.COMPLETED);
        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(objectMapper.readValue(request.getReader(), OrderDTO.class)).thenReturn(orderDTO);
        when(dtoValidator.validateOrderDTO(orderDTO)).thenReturn(new ArrayList<>());

        doNothing().when(orderService).updateOrderStatus(orderDTO.getId(), orderDTO.getStatus());

        orderServlet.doPut(request, response);

        verify(response).getWriter().write("Order status updated successfully");
    }


    @Test
    void testDoPut_UnauthorizedUser() throws Exception {
        when(request.getSession().getAttribute("user")).thenReturn(new User(2, "anna", "anna",UserRole.CUSTOMER, null));

        orderServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(writer).write("Access denied");
    }

    @Test
    void testDoDelete_Success() throws Exception {
        User user = new User(1, "root", "root",UserRole.ADMIN, null);
        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn("1");
        doNothing().when(orderService).cancelOrder(1);

        orderServlet.doDelete(request, response);

        verify(response).getWriter().write("Order cancelled successfully");
    }

    @Test
    void testDoDelete_MissingOrderId() throws Exception {
        User user = new User(1, "root", "root",UserRole.ADMIN, null);
        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn(null);

        orderServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(writer).write("Order ID is required");
    }

    @Test
    void testDoDelete_InvalidOrderId() throws Exception {
        User user = new User(1, "root", "root",UserRole.ADMIN, null);
        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn("invalid");

        orderServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(writer).write("Order ID is invalid");
    }

    @Test
    void testDoDelete_UnauthorizedUser() throws Exception {
        when(request.getSession().getAttribute("user")).thenReturn(new User(2, "anna", "anna", UserRole.CUSTOMER, null));

        orderServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(writer).write("Access denied");
    }
}
