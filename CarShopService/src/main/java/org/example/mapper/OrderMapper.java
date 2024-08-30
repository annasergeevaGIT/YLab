package org.example.mapper;

import org.example.domain.dto.OrderDTO;
import org.example.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Interface for mapping to DTO and back
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO orderDTO);
    List<OrderDTO> toDTOList(List<Order> orders);
}