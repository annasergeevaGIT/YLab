package org.example.controller;

import lombok.Data;
import org.example.dao.CarRepository;
import org.example.domain.dto.CarDTO;
import org.example.domain.model.Car;
import org.example.mapper.CarMapper;
import org.example.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller for {@code Car} entity CRUD operations
 * database {@link CarRepository}
 * @RestController  handles HTTP-requests and return data in JSON format
 * @RequestMapping  annotation URL path and format for all methods
 */
@RestController
@RequestMapping(value = "/cars", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {
    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAll() {
        final List<Car> cars = service.getAll();
        final List<CarDTO> carsDTO = service.getAllDTO(cars);
        return ResponseEntity.ok(carsDTO);
    }

    @GetMapping("/filter/{name_filter}/{params}")
    public ResponseEntity<List<CarDTO>> getAllAfterFilter(@PathVariable String name_filter, @PathVariable String params) {
        List<Car> cars = service.getFilteredCars(name_filter, params);
        return ResponseEntity.ok(service.getAllDTO(cars));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getById(@PathVariable int id) {
        Car carById = service.getById(id);
        return ResponseEntity.ok(CarMapper.INSTANCE.toDTO(carById));
    }

    @PostMapping
    public ResponseEntity<Car> create(@RequestBody CarDTO carDTO) {
        Car car = CarMapper.INSTANCE.toEntity(carDTO);
        Car savedCar = service.saveOrUpdate(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Car> update(@RequestBody CarDTO carDTO, @PathVariable int id) {
        Car car = CarMapper.INSTANCE.toEntity(carDTO);
        car.setId(id);
        Car updatedCar = service.saveOrUpdate(car);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}