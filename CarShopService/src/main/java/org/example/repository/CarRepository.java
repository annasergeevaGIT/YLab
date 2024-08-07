package org.example.repository;

import org.example.model.Car;
import java.util.ArrayList;
import java.util.List;
/**
 * Repository to store cars.
 */
public class CarRepository {
    private List<Car> cars = new ArrayList<>();
    private int nextId = 1;

    public List<Car> findAll() {
        return cars;
    }

    public Car findById(int id) {
        return cars.stream().filter(car -> car.getId() == id).findFirst().orElse(null);
    }

    public void save(Car car) {
        car.setId(nextId++);
        cars.add(car);
    }

    public void update(Car car) {
        Car existingCar = findById(car.getId());
        if (existingCar != null) {
            existingCar.setBrand(car.getBrand());
            existingCar.setModel(car.getModel());
            existingCar.setYear(car.getYear());
            existingCar.setPrice(car.getPrice());
            existingCar.setStatus(car.getStatus());
        }
    }

    public void deleteById(int id) {
        cars.removeIf(car -> car.getId() == id);
    }

    public int getNextId() {
        return nextId;
    }
}


