package org.example.service;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.*;
import org.example.repository.CarRepository;
import java.util.List;

/**
 * Service for car management.
 */
@AllArgsConstructor
@Data
public class CarService {
    private CarRepository carRepository;
    private AuditService auditService;
    private AuthService authService;

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
     * @param brand
     * @param model
     * @param year
     * @param price
     * @param status
     */
    public void addCar(String brand, String model, int year, double price, CarStatus status) {
        User user = authService.getCurrentUser();
        Car car = new Car(brand, model, year, price, status);
        carRepository.create(car);
        auditService.logAction(user, "Added car: " + car.getBrand() + " " + car.getModel()); // write log file
    }

    /**
     * Update existing car.
     *
     * @param car the car to update
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
        auditService.logAction(user, "Removed car: " + car.getBrand() + " " + car.getModel()); // write log file
    }

    public Car getById(int carId) {
        return carRepository.findById(carId);
    }
}
