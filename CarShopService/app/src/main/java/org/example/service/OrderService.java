package org.example.service;

import org.example.domain.dto.OrderDTO;
import org.example.domain.model.Order;
import org.example.domain.model.OrderStatus;

import java.util.List;
/**
 * Service for managing orders.
 */
public interface OrderService {
    public Order saveOrUpdate(Order order);

    public List<Order> getAll();

    public List<OrderDTO> getAllDTO(List<Order> orders);

    public Order getById(int id);

    public Order changeStatus(int id, OrderStatus status);

    public Order canceled(int id);

    public List<Order> getFilteredOrder(String nameFilter, String params);
}
