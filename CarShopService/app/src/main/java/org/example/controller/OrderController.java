package org.example.controller;

import org.example.domain.dto.OrderDTO;
import org.example.domain.model.Order;
import org.example.domain.model.OrderStatus;
import org.example.mapper.OrderMapper;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAll() {
        List<Order> list = service.getAll();
        return ResponseEntity.ok(service.getAllDTO(list));
    }

    @GetMapping("/filter/{name_filter}/{params}")
    public ResponseEntity<List<OrderDTO>> getAllAfterFilter(@PathVariable String name_filter, @PathVariable String params) {
        List<Order> orders = service.getFilteredOrder(name_filter, params);
        return ResponseEntity.ok(service.getAllDTO(orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable int id) {
        Order orderById = service.getById(id);
        return ResponseEntity.ok(OrderMapper.INSTANCE.toDTO(orderById));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
        Order savedOrder = service.saveOrUpdate(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.INSTANCE.toDTO(savedOrder));
    }

    @PutMapping("/canceled")
    public ResponseEntity<OrderDTO> canceled(@RequestParam(value = "id", required = false) int id) {
        final Order canceledOrder = service.canceled(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.INSTANCE.toDTO(canceledOrder));
    }

    @PutMapping("/change-status")
    public ResponseEntity<OrderDTO> changeStatus(@RequestParam(value = "id", required = false) int id,
                                                 @RequestParam(value = "status", required = false) OrderStatus status) {
        final Order changeStatusOrder = service.changeStatus(id, status);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.INSTANCE.toDTO(changeStatusOrder));
    }

}
