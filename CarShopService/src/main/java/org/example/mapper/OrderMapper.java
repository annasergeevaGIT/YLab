package org.example.mapper;

import org.example.domain.dto.OrderDTO;
import org.example.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Interface for mapping to DTO and back
 */
@Mapper
public interface OrderMapper {
    OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO orderDTO);
    List<OrderDTO> toDTOList(List<Order> orders);
}