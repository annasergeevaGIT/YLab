package org.example.util;

import org.example.dto.UserDTO;
import org.example.dto.CarDTO;
import org.example.dto.OrderDTO;
import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DTOValidatorTest {

    private final DTOValidator dtoValidator = new DTOValidator(); // Instantiate the class under test

    /**
     * Test validation of UserDTO with valid data.
     * Ensures no validation errors are returned for valid input.
     */
    @Test
    void testValidateUserDTO_Valid() {
        // Arrange
        UserDTO userDTO = new UserDTO(1,"root", "root", UserRole.ADMIN, null);

        // Act
        List<String> errors = dtoValidator.validateUserDTO(userDTO);

        // Assert
        assertEquals(0, errors.size(), "There should be no validation errors for valid UserDTO.");
    }

    /**
     * Test validation of UserDTO with missing username.
     * Ensures an appropriate error message is returned.
     */
    @Test
    void testValidateUserDTO_MissingUsername() {
        // Arrange
        UserDTO userDTO = new UserDTO(1,"root", "root", UserRole.ADMIN, null);

        // Act
        List<String> errors = dtoValidator.validateUserDTO(userDTO);

        // Assert
        assertEquals(1, errors.size(), "There should be one validation error for missing username.");
        assertEquals("Username is required", errors.get(0), "Error message for missing username is incorrect.");
    }

    /**
     * Test validation of UserDTO with missing password.
     * Ensures an appropriate error message is returned.
     */
    @Test
    void testValidateUserDTO_MissingPassword() {
        // Arrange
        UserDTO userDTO = new UserDTO(1,"root", "root", UserRole.ADMIN, null);

        // Act
        List<String> errors = dtoValidator.validateUserDTO(userDTO);

        // Assert
        assertEquals(1, errors.size(), "There should be one validation error for missing password.");
        assertEquals("Password is required", errors.get(0), "Error message for missing password is incorrect.");
    }

    /**
     * Test validation of CarDTO with valid data.
     * Ensures no validation errors are returned for valid input.
     */
    @Test
    void testValidateCarDTO_Valid() {
        // Arrange
        CarDTO carDTO = new CarDTO(1,"GAZ-13", "Chaika", 2022, 20000.00, CarStatus.AVAILABLE.toString());

        // Act
        List<String> errors = dtoValidator.validateCarDTO(carDTO);

        // Assert
        assertEquals(0, errors.size(), "There should be no validation errors for valid CarDTO.");
    }

    /**
     * Test validation of CarDTO with missing brand.
     * Ensures an appropriate error message is returned.
     */
    @Test
    void testValidateCarDTO_MissingBrand() {
        // Arrange
        CarDTO carDTO = new CarDTO(1,"GAZ-13", "Chaika", 2022, 20000.00, CarStatus.AVAILABLE.toString());

        // Act
        List<String> errors = dtoValidator.validateCarDTO(carDTO);

        // Assert
        assertEquals(1, errors.size(), "There should be one validation error for missing brand.");
        assertEquals("Brand is required", errors.get(0), "Error message for missing brand is incorrect.");
    }

    /**
     * Test validation of CarDTO with missing model.
     * Ensures an appropriate error message is returned.
     */
    @Test
    void testValidateCarDTO_MissingModel() {
        // Arrange
        CarDTO carDTO = new CarDTO(1,"GAZ-13", "Chaika", 2022, 20000.00, CarStatus.AVAILABLE.toString());

        // Act
        List<String> errors = dtoValidator.validateCarDTO(carDTO);

        // Assert
        assertEquals(1, errors.size(), "There should be one validation error for missing model.");
        assertEquals("Model is required", errors.get(0), "Error message for missing model is incorrect.");
    }

    /**
     * Test validation of CarDTO with invalid year and price.
     * Ensures appropriate error messages are returned.
     */
    @Test
    void testValidateCarDTO_InvalidYearAndPrice() {
        // Arrange
        CarDTO carDTO = new CarDTO(1,"GAZ-13", "Chaika", 2022, 20000.00, CarStatus.AVAILABLE.toString());

        // Act
        List<String> errors = dtoValidator.validateCarDTO(carDTO);

        // Assert
        assertEquals(4, errors.size(), "There should be four validation errors.");
        assertEquals("Year must be a positive integer", errors.get(0), "Error message for invalid year is incorrect.");
        assertEquals("Price must be a positive value", errors.get(1), "Error message for invalid price is incorrect.");
        assertEquals("Status is required", errors.get(2), "Error message for missing status is incorrect.");
    }

    /**
     * Test validation of OrderDTO with valid data.
     * Ensures no validation errors are returned for valid input.
     */
    @Test
    void testValidateOrderDTO_Valid() {
        // Arrange
        Car car = new Car(1, "GAZ-13", "Chaika", 2023, 30000, CarStatus.AVAILABLE);
        User user = new User(1, "root", "root", UserRole.ADMIN, null);
        OrderDTO orderDTO = new OrderDTO(1, car, user, OrderStatus.PENDING, LocalDateTime.now());

        // Act
        List<String> errors = dtoValidator.validateOrderDTO(orderDTO);

        // Assert
        assertEquals(0, errors.size(), "There should be no validation errors for valid OrderDTO.");
    }

    /**
     * Test validation of OrderDTO with invalid car ID and user ID.
     * Ensures appropriate error messages are returned.
     */
    @Test
    void testValidateOrderDTO_InvalidIds() {
        // Arrange
        Car car = new Car(1, "GAZ-13", "Chaika", 2023, 30000, CarStatus.AVAILABLE);
        User user = new User(1, "root", "root", UserRole.ADMIN, null);
        OrderDTO orderDTO = new OrderDTO(1, car, user, OrderStatus.PENDING, LocalDateTime.now());

        // Act
        List<String> errors = dtoValidator.validateOrderDTO(orderDTO);

        // Assert
        assertEquals(2, errors.size(), "There should be two validation errors.");
        assertEquals("Car ID must be a positive integer", errors.get(0), "Error message for invalid car ID is incorrect.");
        assertEquals("User ID must be a positive integer", errors.get(1), "Error message for invalid user ID is incorrect.");
        assertEquals("Order status is required", errors.get(2), "Error message for missing order status is incorrect.");
    }
}
