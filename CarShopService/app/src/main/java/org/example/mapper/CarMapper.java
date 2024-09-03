package org.example.mapper;

import org.example.domain.dto.CarDTO;
import org.example.domain.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Interface for mapping to DTO and back
 */
@Mapper(componentModel = "spring")
public interface CarMapper {
    CarDTO toDTO(Car car);
    Car toEntity(CarDTO carDTO);
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
    List<CarDTO> toDTOList(List<Car> cars);
}