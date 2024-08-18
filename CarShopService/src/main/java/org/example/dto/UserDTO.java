package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Order;
import org.example.model.UserRole;

import java.util.List;

/**
 * Data Transfer Object for User.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @JsonProperty
    private int id;          // Unique identifier for the user
    private String username; // User's username
    private String password; // User's password
    private UserRole userRole;
    private List<Order> orders;


}
