package org.example.util;

import org.example.domain.dto.UserDTO;
import org.example.domain.dto.CarDTO;
import org.example.domain.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;

public class DTOValidator {

    public List<String> validateUserDTO(UserDTO userDTO) {
        List<String> errors = new ArrayList<>();
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            errors.add("Username is required");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            errors.add("Password is required");
        }
        return errors;
    }

    public List<String> validateCarDTO(CarDTO carDTO) {
        List<String> errors = new ArrayList<>();
        if (carDTO.getBrand() == null || carDTO.getBrand().isEmpty()) {
            errors.add("Brand is required");
        }
        if (carDTO.getModel() == null || carDTO.getModel().isEmpty()) {
            errors.add("Model is required");
        }
        if (carDTO.getYear() <= 0) {
            errors.add("Year must be a positive integer");
        }
        if (carDTO.getPrice() <= 0) {
            errors.add("Price must be a positive value");
        }
        if (carDTO.getStatus() == null) {
            errors.add("Status is required");
        }
        return errors;
    }

    public List<String> validateOrderDTO(OrderDTO orderDTO) {
        List<String> errors = new ArrayList<>();
        if (orderDTO.getId() <= 0) {
            errors.add("Car ID must be a positive integer");
        }
        if (orderDTO.getId() <= 0) {
            errors.add("User ID must be a positive integer");
        }
        if (orderDTO.getStatus() == null) {
            errors.add("Order status is required");
        }
        return errors;
    }
}
