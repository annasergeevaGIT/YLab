package org.example.util;

import org.example.domain.dto.CarDTO;
import org.example.domain.dto.OrderDTO;
import org.example.domain.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTOValidator {

    public List<String> validateUserDTO(UserDTO userDTO) {
        List<String> errors = new ArrayList<>();
        if (userDTO.username() == null || userDTO.username().isEmpty()) {
            errors.add("Username is required");
        }
        if (userDTO.password() == null || userDTO.password().isEmpty()) {
            errors.add("Password is required");
        }
        return errors;
    }

    public List<String> validateCarDTO(CarDTO carDTO) {
        List<String> errors = new ArrayList<>();
        if (carDTO.brand() == null || carDTO.brand().isEmpty()) {
            errors.add("Brand is required");
        }
        if (carDTO.model() == null || carDTO.model().isEmpty()) {
            errors.add("Model is required");
        }
        if (carDTO.year() <= 0) {
            errors.add("Year must be a positive integer");
        }
        if (carDTO.price() <= 0) {
            errors.add("Price must be a positive value");
        }
        if (carDTO.status() == null) {
            errors.add("Status is required");
        }
        return errors;
    }

    public List<String> validateOrderDTO(OrderDTO orderDTO) {
        List<String> errors = new ArrayList<>();
        if (orderDTO.carId() <= 0) {
            errors.add("Car ID must be a positive integer");
        }
        if (orderDTO.userId() <= 0) {
            errors.add("User ID must be a positive integer");
        }
        if (orderDTO.status() == null) {
            errors.add("Order status is required");
        }
        return errors;
    }
}
