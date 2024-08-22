package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.CarStatus;
import org.example.model.Order;
import org.example.model.UserRole;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarDTO {
    private Integer id; // For update operations
    private String brand;
    private String model;
    private int year;
    private double price;
    private String status; // This should match CarStatus enum values
}
