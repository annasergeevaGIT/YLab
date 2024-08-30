package org.example.repository;

import org.example.domain.model.Car;
import org.example.domain.model.CarStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarRepositoryTest extends BaseRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    public void init() {
        carRepository = new CarRepository();
    }

    @Test
    @DisplayName("Should create and find a car by ID")
    public void testCreateAndFindById() {
        Car car = new Car("Toyota", "Camry", 2020, 30000.00, CarStatus.AVAILABLE);
        carRepository.create(car);

        Car foundCar = carRepository.findById(car.getId());
        assertNotNull(foundCar);
        assertEquals("Toyota", foundCar.getBrand());
    }

    @Test
    @DisplayName("Should find all cars in the repository")
    public void testFindAll() {
        List<Car> cars = carRepository.findAll();
        assertNotNull(cars);
        assertTrue(cars.size() > 0);
    }
}
