package org.example.service;

import org.example.domain.dto.CarDTO;
import org.example.domain.model.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for car management, conveting to objects {@link CarDTO}, CRUD operations
 */

public interface CarService {
    public List<Car> getAll();

    public List<CarDTO> getAllDTO(List<Car> cars);

    public Car getById(int id);

    public Car saveOrUpdate(Car car);

    public void delete(int id);

    public List<Car> getFilteredCars(String nameFilter, String params);
}
