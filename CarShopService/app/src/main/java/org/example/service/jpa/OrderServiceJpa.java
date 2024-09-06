package org.example.service.jpa;

import lombok.SneakyThrows;
import org.example.dao.OrderRepository;
import org.example.domain.dto.CarDTO;
import org.example.domain.dto.OrderDTO;
import org.example.domain.model.Car;
import org.example.domain.model.Order;
import org.example.domain.model.OrderStatus;
import org.example.mapper.CarMapper;
import org.example.mapper.OrderMapper;
import org.example.service.CarService;
import org.example.service.OrderService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Order Service implementation with JPA
 * link {@link OrderRepository} database
 */
@Service
public class OrderServiceJpa implements OrderService {
    private final OrderRepository repository;

    public OrderServiceJpa(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order saveOrUpdate(Order order) {
        return repository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }

    @Override
    public List<OrderDTO> getAllDTO(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper.INSTANCE::toDTO)
                .toList();
    }
    @SneakyThrows
    @Override
    public Order getById(int id) {
        final Optional<Order> optionalOrder = repository.findById(id);
        if(optionalOrder.isPresent()){
            return optionalOrder.get();
        }
        throw new ChangeSetPersister.NotFoundException();
    }

    @Override
    public Order changeStatus(int id, OrderStatus status) {
        return changeOrderStatus(id, status);
    }

    @Override
    public Order canceled(int id) {
        return changeOrderStatus(id, OrderStatus.CANCELLED);
    }
    @SneakyThrows
    @Override
    public List<Order> getFilteredOrder(String nameFilter, String params) {
        return switch (nameFilter) {
            case "date" -> repository.findByDate(LocalDate.parse(params));
            case "status" -> repository.findByStatus(params);
            default -> throw new ChangeSetPersister.NotFoundException();
        };
    }

    @SneakyThrows
    private Order changeOrderStatus(int id, OrderStatus status) {
        final Optional<Order> optionalOrder = repository.findById(id);
        if(optionalOrder.isPresent()){
            final Order order = optionalOrder.get();
            order.setStatus(status);
            return saveOrUpdate(order);
        }
        throw new ChangeSetPersister.NotFoundException();
    }
}
