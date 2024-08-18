package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.UserDTO;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.AuthService;
import org.example.service.UserService;
import org.example.util.DTOValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Unit test class for the UserServlet.
 * Contains test cases for POST, GET, and PUT HTTP methods.
 */
public class UserServletTest {

    @Mock
    private ObjectMapper objectMapper; // Mocked Jackson ObjectMapper

    @Mock
    private DTOValidator dtoValidator; // Mocked DTO Validator

    @Mock
    private AuthService authService; // Mocked AuthService

    @Mock
    private UserService userService; // Mocked UserService

    @InjectMocks
    private UserServlet userServlet; // Servlet under test

    private HttpServletRequest request; // Mocked HttpServletRequest
    private HttpServletResponse response; // Mocked HttpServletResponse
    private ByteArrayOutputStream responseOutputStream; // Output stream for capturing response

    /**
     * Setup method to initialize mocks and setup the servlet for testing.
     * This method is run before each test case.
     */
    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responseOutputStream = new ByteArrayOutputStream();
        when(response.getWriter()).thenReturn(new PrintWriter(responseOutputStream));
    }

    /**
     * Test for successful user registration via POST request.
     * Validates proper handling of user creation and response status.
     */
    @Test
    void testDoPost_Success() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO(1,"user1", "password", UserRole.CUSTOMER, null);
        when(objectMapper.readValue(ArgumentMatchers.any(InputStream.class), eq(UserDTO.class)))
                .thenReturn(userDTO);
        when(dtoValidator.validateUserDTO(userDTO)).thenReturn(Collections.emptyList());
        when(authService.register(userDTO)).thenReturn(true);

        // Act
        userServlet.doPost(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(response.getWriter()).write("User registered successfully");
    }

    /**
     * Test for user registration failure due to validation errors.
     * Ensures proper handling of invalid user data.
     */
    @Test
    void testDoPost_ValidationErrors() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO(1,"user1", "password", UserRole.CUSTOMER, null);
        when(objectMapper.readValue(ArgumentMatchers.any(InputStream.class), eq(UserDTO.class)))
                .thenReturn(userDTO);
        when(dtoValidator.validateUserDTO(userDTO)).thenReturn(Collections.singletonList("Validation error"));

        // Act
        userServlet.doPost(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response.getWriter()).write("Invalid user data");
    }

    /**
     * Test for user registration failure due to existing user.
     * Validates proper conflict response when user already exists.
     */
    @Test
    void testDoPost_UserAlreadyExists() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO(1,"user1", "password", UserRole.CUSTOMER,null);
        when(objectMapper.readValue(ArgumentMatchers.any(InputStream.class), eq(UserDTO.class)))
                .thenReturn(userDTO);
        when(dtoValidator.validateUserDTO(userDTO)).thenReturn(Collections.emptyList());
        when(authService.register(userDTO)).thenReturn(false);

        // Act
        userServlet.doPost(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_CONFLICT);
        verify(response.getWriter()).write("User already exists");
    }

    /**
     * Test for user registration failure due to internal server error.
     * Ensures proper error response handling for unexpected exceptions.
     */
    @Test
    void testDoPost_InternalServerError() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO(1, "user1", "password", UserRole.CUSTOMER, null);
        when(objectMapper.readValue(ArgumentMatchers.any(InputStream.class), eq(UserDTO.class)))
                .thenReturn(userDTO);
        when(dtoValidator.validateUserDTO(userDTO)).thenReturn(Collections.emptyList());
        when(authService.register(userDTO)).thenThrow(new RuntimeException("Database error"));

        // Act
        userServlet.doPost(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(response.getWriter()).write("Error registering user: Database error");
    }

    /**
     * Test for successful user login via GET request.
     * Checks correct handling of login and response generation.
     */
    @Test
    void testDoGet_LoginSuccess() throws Exception {
        // Arrange
        User user = new User(1,"user1", "password", UserRole.CUSTOMER, null);
        UserDTO userDTO = new UserDTO(1,"user1", "password", UserRole.CUSTOMER, null);
        when(request.getParameter("username")).thenReturn("user1");
        when(request.getParameter("password")).thenReturn("password");
        when(authService.login("user1", "password")).thenReturn(user);
        when(objectMapper.writeValueAsString(userDTO)).thenReturn("{}");
        when(UserMapper.toUserDTO(user)).thenReturn(userDTO);

        // Act
        userServlet.doGet(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response.getWriter()).write("{}");
    }

    /**
     * Test for user login failure due to invalid credentials.
     * Ensures correct response when credentials are incorrect.
     */
    @Test
    void testDoGet_LoginFailure() throws Exception {
        // Arrange
        when(request.getParameter("username")).thenReturn("user1");
        when(request.getParameter("password")).thenReturn("wrongpassword");
        when(authService.login("user1", "wrongpassword")).thenReturn(null);

        // Act
        userServlet.doGet(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response.getWriter()).write("Invalid credentials");
    }

    /**
     * Test for user login failure due to missing parameters.
     * Validates the handling of missing username or password.
     */
    @Test
    void testDoGet_MissingParameters() throws Exception {
        // Arrange
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("password")).thenReturn(null);

        // Act
        userServlet.doGet(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response.getWriter()).write("Username and password are required");
    }

    /**
     * Test for successful user role assignment via PUT request.
     * Validates proper role assignment and response generation.
     */
    @Test
    void testDoPut_AssignRoleSuccess() throws Exception {
        // Arrange
        User adminUser = new User(1,"root", "root", UserRole.ADMIN,null);
        when(request.getSession().getAttribute("username")).thenReturn("admin");
        when(request.getSession().getAttribute("password")).thenReturn("adminpassword");
        when(authService.login("admin", "adminpassword")).thenReturn(adminUser);
        when(request.getParameter("username")).thenReturn("user1");
        when(request.getParameter("role")).thenReturn("MANAGER");
        when(userService.updateUserRoleByName("user1", UserRole.MANAGER)).thenReturn(true);

        // Act
        userServlet.doPut(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response.getWriter()).write("Role assigned successfully.");
    }

    /**
     * Test for role assignment failure due to invalid role.
     * Ensures correct response for unrecognized roles.
     */
    @Test
    void testDoPut_InvalidRole() throws Exception {
        // Arrange
        User adminUser = new User(1,"root", "root", UserRole.ADMIN,null);
        when(request.getSession().getAttribute("username")).thenReturn("admin");
        when(request.getSession().getAttribute("password")).thenReturn("adminpassword");
        when(authService.login("admin", "adminpassword")).thenReturn(adminUser);
        when(request.getParameter("username")).thenReturn("user1");
        when(request.getParameter("role")).thenReturn("INVALID_ROLE");

        // Act
        userServlet.doPut(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response.getWriter()).write("Invalid role.");
    }

    /**
     * Test for role assignment failure due to missing parameters.
     * Ensures proper handling when username or role is not provided.
     */
    @Test
    void testDoPut_MissingParameters() throws Exception {
        // Arrange
        User adminUser = new User(1,"root", "root", UserRole.ADMIN, null);
        when(request.getSession().getAttribute("username")).thenReturn("admin");
        when(request.getSession().getAttribute("password")).thenReturn("adminpassword");
        when(authService.login("admin", "adminpassword")).thenReturn(adminUser);
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("role")).thenReturn(null);

        // Act
        userServlet.doPut(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response.getWriter()).write("Username and role are required.");
    }

    /**
     * Test for role assignment failure due to unauthorized user.
     * Ensures the servlet correctly denies role assignment if the logged-in user is not an admin.
     */
    @Test
    void testDoPut_UnauthorizedUser() throws Exception {
        // Arrange
        User nonAdminUser = new User(2,"user", "userpassword", UserRole.CUSTOMER,null);
        when(request.getSession().getAttribute("username")).thenReturn("user");
        when(request.getSession().getAttribute("password")).thenReturn("userpassword");
        when(authService.login("user", "userpassword")).thenReturn(nonAdminUser);

        // Act
        userServlet.doPut(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(response.getWriter()).write("You do not have permission to assign roles.");
    }

    /**
     * Helper method to simulate user login and session management.
     * This is used to retrieve the logged-in user for role assignments.
     */
    private User getLoggedInUser(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        String password = (String) request.getSession().getAttribute("password");
        if (username == null || password == null) {
            return null;
        }
        return authService.login(username, password);
    }
}
