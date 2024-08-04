package service;

import model.Car;
import model.User;
import repository.CarRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CarService {
    private CarRepository carRepository;
    private AuditService auditService;

    public CarService(CarRepository carRepository, AuditService auditService) {
        this.carRepository = carRepository;
        this.auditService = auditService;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void addCar(Car car) {
        carRepository.save(car);
    }
    public void updateCar(Car car) {
        carRepository.update(car);
    }
    public void deleteCar(int carId) {
        carRepository.deleteById(carId);
    }

    public Car getById(int carId) {
        return carRepository.findById(carId);
    }

    public int getNextId() {
        return carRepository.getNextId();
    }
}
