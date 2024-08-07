package org.example.service;

import org.example.model.Car;
import org.example.model.CarStatus;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.AuditService;
import org.example.service.AuthService;
import org.example.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.repository.CarRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CarServiceTest {

    private CarService carService;
    private CarRepository carRepository;
    private AuditService auditService;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        carRepository = mock(CarRepository.class);
        auditService = mock(AuditService.class);
        carService = new CarService(carRepository, auditService, authService);
    }

    @Test
    void testGetAllCars() {
        Car car1 = new Car(1, "Toyota", "Camry", 2020, 25000, CarStatus.AVAILABLE);
        Car car2 = new Car(2, "Honda", "Accord", 2019, 22000, CarStatus.AVAILABLE);
        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        List<Car> cars = carService.getAllCars();

        assertThat(cars).hasSize(2);
        assertThat(cars).contains(car1, car2);
    }

    @Test
    void testAddCar() {
        Car car = new Car(0, "Toyota", "Camry", 2020, 25000, CarStatus.AVAILABLE);
        User user = new User(1, "admin", "password", UserRole.ADMIN);
        carService.addCar(car);

        verify(carRepository, times(1)).save(car);
        verify(auditService, times(1)).logAction(user, "Added car: Toyota Camry");
    }
}
