package org.example.service.jpa;

import lombok.SneakyThrows;
import org.example.dao.CarRepository;
import org.example.domain.dto.CarDTO;
import org.example.domain.model.Car;
import org.example.mapper.CarMapper;
import org.example.service.CarService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Car Service implementation with JPA
 * link {@link CarRepository} database
 */
@Service
public class CarServiceJpa implements CarService {
    private final CarRepository repository;

    public CarServiceJpa(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Car> getAll() {
        return repository.findAll();
    }

    @Override
    public List<CarDTO> getAllDTO(List<Car> cars) {
        return cars.stream()
                .map(CarMapper.INSTANCE::toDTO)
                .toList();
    }

    @SneakyThrows
    @Override
    public Car getById(int id) {
        final Optional<Car> optionalCar = repository.findById(id);
        if (optionalCar.isPresent()) {
            return optionalCar.get();
        }
        throw new ChangeSetPersister.NotFoundException();
    }

    @Override
    public Car saveOrUpdate(Car car) {
        return repository.save(car);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
    @SneakyThrows
    @Override
    public List<Car> getFilteredCars(String nameFilter, String params) {
        return switch (nameFilter) {
            case "brand" -> repository.findByBrand(params);
            case "condition" -> repository.findByCondition(params);
            case "price" -> repository.findByPrice(Double.parseDouble(params));
            default -> throw new ChangeSetPersister.NotFoundException();
        };
    }
}
