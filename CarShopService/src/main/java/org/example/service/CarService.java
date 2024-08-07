package org.example.service;
import org.example.model.Car;
import org.example.model.User;
import org.example.repository.CarRepository;
import java.util.List;

/**
 * Service for car management.
 */
public class CarService {
    private CarRepository carRepository;
    private AuditService auditService;
    private AuthService authService;

    /**
     * Constructs a new CarService.
     *
     * @param carRepository the car repository
     * @param auditService  the audit service
     */
    public CarService(CarRepository carRepository, AuditService auditService, AuthService authService) {
        this.carRepository = carRepository;
        this.auditService = auditService;
        this.authService = authService;
    }

    /**
     * Gets all cars.
     *
     * @return a list of all cars
     */
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Adds a new car.
     *
     * @param car the car to add
     */
    public void addCar(Car car) {
        User user = authService.getCurrentUser();
        carRepository.save(car);
        auditService.logAction(user, "Added car: " + car.getBrand() + " " + car.getModel());
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
        carRepository.deleteById(carId);
    }

    /**
     * Get the car by ID.
     *
     * @param carId the car ID
     */
    public Car getById(int carId) {
        return carRepository.findById(carId);
    }

    /**
     * Generate next ID.
     */
    public int getNextId() {
        return carRepository.getNextId();
    }
}
