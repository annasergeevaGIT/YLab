package org.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.model.UserRole;

/**
 * Data Transfer Object for User.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
        @JsonProperty("user_name")
        @NotEmpty(message = "Username cannot be empty")
        private String username; // User's username

        @JsonProperty("user_age")
        @Min(value = 18, message = "Age must be 18 years or older")
        private int age; // User's age

        @JsonProperty("email_address")
        @Email(message = "Invalid email format")
        private String email; // User's Email

        @JsonProperty("role")
        @NotNull(message = "Role cannot be null")
        private UserRole userRole; // User's role in the system
}
