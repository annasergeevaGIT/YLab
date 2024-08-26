package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.domain.model.Car;
import org.example.domain.model.User;
import org.example.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for car management.
 */
@AllArgsConstructor
@Data
@Service
public class CarService {
    private CarRepository carRepository;
    private AuditService auditService;
    private AuthServiceJdbc authService;

    /**
     * Gets all cars.
     *
     * @return a list of all cars
     */
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Adds a new car
     * @param car
     */
    public void addCar(Car car) {
        User user = authService.getCurrentUser();
        carRepository.create(car);
        auditService.logAction(user.getId(), "Added car: " + car.getBrand() + " " + car.getModel()); // write log file
    }

    /**
     * Update existing car.
     * @param car
     */
    public void updateCar(Car car) {
        carRepository.update(car);
    }


    /**
     * Delete the car.
     *
     * @param carId
     */
    public void deleteCar(int carId) {
        User user = authService.getCurrentUser();
        Car car = carRepository.findById(carId);
        carRepository.delete(carId);
        auditService.logAction(user.getId(), "Removed car: " + car.getBrand() + " " + car.getModel()); // write log file
    }

    public Car getById(int carId) {
        return carRepository.findById(carId);
    }
}
