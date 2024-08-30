package org.example.mapper;

import org.example.domain.dto.CarDTO;
import org.example.domain.model.Car;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Interface for mapping to DTO and back
 */
@Mapper(componentModel = "spring")
public interface CarMapper {
    CarDTO toDTO(Car car);
    Car toEntity(CarDTO carDTO);
    List<CarDTO> toDTOList(List<Car> cars);
}