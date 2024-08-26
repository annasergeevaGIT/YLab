package org.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.domain.model.UserRole;

/**
 * Data Transfer Object for User.
 */
public record UserDTO(
        @JsonProperty("user_id")
        int id, // Unique identifier for the user

        @JsonProperty("user_name")
        @NotEmpty(message = "Username cannot be empty")
        String username, // User's username

        @JsonProperty("password")
        @NotEmpty(message = "Password cannot be empty")
        String password, // User's password

        @JsonProperty("email_address")
        @Email(message = "Invalid email format")
        String email, // User's Email

        @JsonProperty("user_age")
        @Min(value = 18, message = "Age must be 18 years or older")
        int age, // User's age

        @JsonProperty("role")
        @NotNull(message = "Role cannot be null")
        UserRole userRole // User's role in the system
) {}
