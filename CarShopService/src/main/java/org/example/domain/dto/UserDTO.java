package org.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.example.domain.model.Order;
import org.example.domain.model.UserRole;
import java.util.List;
/**
 * Data Transfer Object for User.
 */
public record UserDTO(
        @JsonProperty
        @NotEmpty(message = "User ID cannot be empty")
        int id,             // Unique identifier for the user
        @NotEmpty(message = "Username cannot be empty")
        String username,    // User's username
        @NotEmpty(message = "Password cannot be empty")
        String password,    // User's password
        @Email
        String email,       // User's Email
        @Min(value = 17, message = "must be more then 17 years old")
        int age,            // User's age, must be more than 18
        @NotEmpty(message = "Role cannot be empty")
        UserRole userRole,  // User's role in the system
        List<Order> orders  // List of orders associated with the user
){}
