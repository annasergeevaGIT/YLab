package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDTO;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.AuthService;
import org.example.service.UserService;
import org.example.util.ApplicationContext;
import org.example.util.DTOValidator;
import org.mapstruct.factory.Mappers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserServlet extends HttpServlet {

    private ObjectMapper objectMapper; // Jackson ObjectMapper
    private DTOValidator dtoValidator;
    private AuthService authService;
    private UserService userService;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class); // MapStruct UserMapper

    @Override
    public void init() throws ServletException {
        ApplicationContext context = ApplicationContext.getInstance();
        authService = context.getAuthService();
        objectMapper = context.getObjectMapper(); // Jackson ObjectMapper
        dtoValidator = context.getDtoValidator();
        userService = context.getUserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO userDTO = objectMapper.readValue(request.getInputStream(), UserDTO.class);

        // Validate the incoming DTO
        List<String> validationErrors = dtoValidator.validateUserDTO(userDTO);
        if (!validationErrors.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid user data");
            log.warn("User registration failed: Invalid user data");
            return;
        }

        try {
            User user = userMapper.toUser(userDTO); // Use UserMapper to convert UserDTO to User
            boolean success = authService.register(userDTO);
            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("User registered successfully");
                log.info("User registered successfully: {}", userDTO.getUsername());
            } else {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("User already exists");
                log.warn("User registration failed: User already exists for username: {}", userDTO.getUsername());
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error registering user: " + e.getMessage());
            log.error("Error registering user: {}", e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Username and password are required");
            log.warn("User login failed: Missing username or password");
            return;
        }

        try {
            User user = authService.login(username, password);
            if (user != null) {
                UserDTO userDTO = UserMapper.toUserDTO(user); // Use UserMapper to convert User to UserDTO
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(objectMapper.writeValueAsString(userDTO)); // Serialize UserDTO to JSON
                log.info("User logged in successfully: {}", username);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid credentials");
                log.warn("User login failed: Invalid credentials for username: {}", username);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error logging in user: " + e.getMessage());
            log.error("Error logging in user: {}", e.getMessage(), e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is an admin
        User loggedInUser = getLoggedInUser(request);
        if (loggedInUser == null || !loggedInUser.getRole().equals(UserRole.ADMIN)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("You do not have permission to assign roles.");
            return;
        }

        // Assign role to user
        String username = request.getParameter("username");
        String roleStr = request.getParameter("role");

        if (username == null || roleStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Username and role are required.");
            return;
        }

        UserRole role;
        try {
            role = UserRole.valueOf(roleStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid role.");
            return;
        }

        boolean success = userService.updateUserRoleByName(username, role);
        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Role assigned successfully.");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("User not found.");
        }
    }

    private User getLoggedInUser(HttpServletRequest request) {
        // Example: You should implement proper session management
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            return null;
        }
        return authService.login(username, (String) request.getSession().getAttribute("password"));
    }
}
