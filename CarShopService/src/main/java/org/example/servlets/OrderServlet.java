package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.OrderDTO;
import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.OrderService;
import org.example.util.DTOValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private OrderService orderService;
    private ObjectMapper objectMapper;
    private DTOValidator dtoValidator;

    private User getCurrentUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = getCurrentUser(req);

        if (currentUser == null) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Access denied");
            return;
        }

        String idParam = req.getParameter("id");

        if (idParam != null) {
            int orderId = Integer.parseInt(idParam);
            Order order = orderService.getOrderById(orderId);
            if (order != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(order));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Order not found");
            }
        } else {
            if (UserRole.CUSTOMER.equals(currentUser.getRole())) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().write("Access denied");
                return;
            }
            List<Order> orders = orderService.getAllOrders();
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(orders));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = getCurrentUser(req);

        if (currentUser == null || !UserRole.CUSTOMER.equals(currentUser.getRole())) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Access denied");
            return;
        }

        OrderDTO orderDTO = objectMapper.readValue(req.getReader(), OrderDTO.class);

        List<String> validationErrors = dtoValidator.validateOrderDTO(orderDTO);
        if (!validationErrors.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(String.join(", ", validationErrors));
            return;
        }

        try {
            orderService.createOrder(orderDTO.getId(), currentUser.getId());
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Order created successfully");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = getCurrentUser(req);

        if (currentUser == null || (!UserRole.ADMIN.equals(currentUser.getRole()) && !UserRole.MANAGER.equals(currentUser.getRole()))) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Access denied");
            return;
        }

        OrderDTO orderDTO = objectMapper.readValue(req.getReader(), OrderDTO.class);

        List<String> validationErrors = dtoValidator.validateOrderDTO(orderDTO);
        if (!validationErrors.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(String.join(", ", validationErrors));
            return;
        }

        try {
            orderService.updateOrderStatus(orderDTO.getId(), orderDTO.getStatus());
            resp.getWriter().write("Order status updated successfully");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = getCurrentUser(req);

        if (currentUser == null || !UserRole.ADMIN.equals(currentUser.getRole())) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Access denied");
            return;
        }

        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Order ID is required");
            return;
        }

        int orderId = Integer.parseInt(idParam);

        try {
            orderService.cancelOrder(orderId);
            resp.getWriter().write("Order cancelled successfully");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }
}
