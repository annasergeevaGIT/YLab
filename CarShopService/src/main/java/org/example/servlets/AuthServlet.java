package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDTO;
import org.example.model.User;
import org.example.service.AuthService;
import org.example.service.AuditService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private AuthService authService;
    private AuditService auditService;
    private ObjectMapper objectMapper; // Added ObjectMapper for JSON handling

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("register".equals(action)) {
            // Register a new admin or user based on the role parameter
            UserDTO userDTO = objectMapper.readValue(req.getReader(), UserDTO.class);
            if (userDTO != null && userDTO.getUsername() != null && userDTO.getPassword() != null) {
                if (authService.getCurrentUser() == null) {
                    // If no admin exists, register the first user as admin
                    authService.registerAdmin(userDTO);
                    resp.getWriter().write("Admin registered");
                } else {
                    // Otherwise, register as a regular user with the CUSTOMER role
                    boolean success = authService.register(userDTO);
                    if (success) {
                        resp.getWriter().write("User registered");
                    } else {
                        resp.setStatus(HttpServletResponse.SC_CONFLICT);
                        resp.getWriter().write("User already exists");
                    }
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid input");
            }
        } else if ("login".equals(action)) {
            // Handle user login
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (username != null && password != null) {
                User user = authService.login(username, password);
                if (user != null) {
                    resp.getWriter().write("Login successful for user: " + user.getUsername());
                    auditService.logAction(user, "Logged in");
                } else {
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    resp.getWriter().write("Invalid credentials");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Username and password are required");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid action");
        }
    }
}
