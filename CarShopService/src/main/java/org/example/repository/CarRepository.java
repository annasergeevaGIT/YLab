package org.example.repository;

import org.example.model.Car;
import java.util.*;

/**
 * Repository to store cars.
 */

public class CarRepository {
    private Map<Integer, Car> cars = new HashMap<>();

    public void create(Car car) {
        cars.put(car.getId(), car);
    }

    public List<Car> findAll() {
        return new ArrayList<>(cars.values());
    }

    public Car findById(int id) {
        return cars.values().stream().filter(car -> car.getId() == id).findFirst().orElse(null);
    }

    public void update(Car car) {
        if (cars.containsKey(car.getId())) {
            cars.put(car.getId(), car);
        }
    }

    public void delete(int id) {
        cars.remove(id);
    }
}


