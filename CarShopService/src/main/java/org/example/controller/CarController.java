package org.example.controller;

import org.example.domain.dto.CarDTO;
import org.example.domain.model.Car;
import org.example.mapper.CarMapper;
import org.example.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
@Validated
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;

    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    /**
     * Get all cars.
     */
    @GetMapping
    public ResponseEntity<List<CarDTO>> listCars() {
        List<CarDTO> cars = carService.getAllCars().stream()
                .map(carMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cars);
    }

    /**
     * Add a new car.
     */
    @PostMapping
    public ResponseEntity<String> addCar(@Valid @RequestBody CarDTO carDTO) {
        Car car = carMapper.toEntity(carDTO);
        carService.addCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body("Car added successfully.");
    }

    /**
     * Update an existing car.
     */
    @PutMapping("/{carId}")
    public ResponseEntity<String> updateCar(
            @PathVariable int carId,
            @Valid @RequestBody CarDTO carDTO) {

        Car existingCar = carService.getById(carId);
        if (existingCar != null) {
            Car car = carMapper.toEntity(carDTO);
            car.setId(carId); // Ensure the ID is set correctly
            carService.updateCar(car);
            return ResponseEntity.ok("Car info successfully updated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The car is not found.");
        }
    }

    /**
     * Delete a car.
     */
    @DeleteMapping("/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable int carId) {
        Car existingCar = carService.getById(carId);
        if (existingCar != null) {
            carService.deleteCar(carId);
            return ResponseEntity.ok("The car is deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The car is not found.");
        }
    }
}
