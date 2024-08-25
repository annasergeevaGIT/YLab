package org.example.service;

import org.example.domain.model.Car;
import org.example.domain.model.CarStatus;
import org.example.domain.model.User;
import org.example.domain.model.UserRole;
import org.example.model.*;
import org.example.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private AuditService auditService;

    @Mock
    private AuthServiceJdbc authService;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getAllCars() - Should return a list of all cars")
    void testGetAllCars() {
        // Arrange
        Car car1 = new Car("Toyota", "Camry", 2020, 25000, CarStatus.AVAILABLE);
        Car car2 = new Car("Honda", "Accord", 2021, 27000, CarStatus.SOLD);
        List<Car> cars = Arrays.asList(car1, car2);

        when(carRepository.findAll()).thenReturn(cars);

        // Act
        List<Car> result = carService.getAllCars();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Toyota", result.get(0).getBrand());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test addCar() - Should add a car and log the action")
    void testAddCar() {
        // Arrange
        User user = new User(1, "admin", "password", UserRole.ADMIN, null);
        Car car = new Car("Toyota", "Camry", 2020, 25000, CarStatus.AVAILABLE);

        when(authService.getCurrentUser()).thenReturn(user);

        // Act
        carService.addCar("Toyota", "Camry", 2020, 25000, CarStatus.AVAILABLE);

        // Assert
        verify(carRepository, times(1)).create(any(Car.class));
        verify(auditService, times(1)).logAction(user, "Added car: Toyota Camry");
    }

    @Test
    @DisplayName("Test updateCar() - Should update an existing car")
    void testUpdateCar() {
        // Arrange
        Car car = new Car("Toyota", "Camry", 2020, 25000, CarStatus.AVAILABLE);

        // Act
        carService.updateCar(car);

        // Assert
        verify(carRepository, times(1)).update(car);
    }

    @Test
    @DisplayName("Test deleteCar() - Should delete a car and log the action")
    void testDeleteCar() {
        // Arrange
        User user = new User(1, "admin", "password", UserRole.ADMIN, null);
        Car car = new Car("Toyota", "Camry", 2020, 25000, CarStatus.AVAILABLE);

        when(authService.getCurrentUser()).thenReturn(user);
        when(carRepository.findById(1)).thenReturn(car);

        // Act
        carService.deleteCar(1);

        // Assert
        verify(carRepository, times(1)).delete(1);
        verify(auditService, times(1)).logAction(user, "Removed car: Toyota Camry");
    }

    @Test
    @DisplayName("Test getById() - Should return a car by its ID")
    void testGetById() {
        // Arrange
        Car car = new Car("Toyota", "Camry", 2020, 25000, CarStatus.AVAILABLE);
        when(carRepository.findById(1)).thenReturn(car);

        // Act
        Car result = carService.getById(1);

        // Assert
        assertEquals("Toyota", result.getBrand());
        verify(carRepository, times(1)).findById(1);
    }
}
