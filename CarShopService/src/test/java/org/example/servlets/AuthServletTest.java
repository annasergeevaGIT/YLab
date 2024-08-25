package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dto.UserDTO;
import org.example.domain.model.User;
import org.example.domain.model.UserRole;
import org.example.service.AuthServiceJdbc;
import org.example.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
/**
 * Tests for the `AuthServlet` class.
 */
class AuthServletTest {

    private AuthServlet authServlet;
    private AuthServiceJdbc authService;
    private AuditService auditService;
    private ObjectMapper objectMapper;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PrintWriter responseWriter;

    @BeforeEach
    void setUp() throws IOException {
        authService = mock(AuthServiceJdbc.class);
        auditService = mock(AuditService.class);
        objectMapper = new ObjectMapper();

        authServlet = new AuthServlet(authService, auditService, objectMapper);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responseWriter = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(responseWriter);
    }

    @Test
    void testRegisterAdmin() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("admin");
        userDTO.setPassword("adminpassword");

        when(authService.getCurrentUser()).thenReturn(null); // No admin exists

        authServlet.doPost(request, response);

        verify(authService).registerAdmin(any(UserDTO.class));
        verify(responseWriter).write("Admin registered");
    }

    @Test
    void testRegisterUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        userDTO.setPassword("userpassword");

        User adminUser = new User("admin", "adminpassword", UserRole.ADMIN, null);
        when(authService.getCurrentUser()).thenReturn(adminUser); // Admin exists
        when(authService.register(any(UserDTO.class))).thenReturn(true);

        authServlet.doPost(request, response);

        verify(authService).register(any(UserDTO.class));
        verify(responseWriter).write("User registered");
    }

    @Test
    void testRegisterUser_Failure() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        userDTO.setPassword("userpassword");

        User adminUser = new User("admin", "adminpassword", UserRole.ADMIN, null);
        when(authService.getCurrentUser()).thenReturn(adminUser); // Admin exists
        when(authService.register(any(UserDTO.class))).thenReturn(false);

        authServlet.doPost(request, response);

        verify(authService).register(any(UserDTO.class));
        verify(response).setStatus(HttpServletResponse.SC_CONFLICT);
        verify(responseWriter).write("User already exists");
    }

    @Test
    void testLoginSuccess() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        userDTO.setPassword("userpassword");

        User user = new User("user", "userpassword", UserRole.CUSTOMER, null);
        when(request.getParameter("username")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("userpassword");
        when(authService.login(anyString(), anyString())).thenReturn(user);

        authServlet.doPost(request, response);

        verify(authService).login("user", "userpassword");
        verify(responseWriter).write("Login successful for user: user");
        verify(auditService).logAction(user, "Logged in");
    }

    @Test
    void testLoginFailure() throws Exception {
        when(request.getParameter("username")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("wrongpassword");
        when(authService.login(anyString(), anyString())).thenReturn(null);

        authServlet.doPost(request, response);

        verify(authService).login("user", "wrongpassword");
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(responseWriter).write("Invalid credentials");
    }
}
