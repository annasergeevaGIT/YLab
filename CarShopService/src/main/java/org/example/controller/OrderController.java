package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.dto.OrderDTO;
import org.example.mapper.OrderMapper;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final OrderMapper orderMapper;

    /**
     * Get all orders.
     *
     * @return List of OrderDTOs
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderMapper.toDTOList(orderService.getAllOrders());
        return ResponseEntity.ok(orders);
    }

    /**
     * Create a new order.
     *
     * @param orderDTO DTO containing the car ID and user ID.
     * @return Created OrderDTO
     */
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        OrderDTO createdOrder = orderMapper.toDTO(orderService.createOrder(orderDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * Update the status of an existing order.
     *
     * @param orderId  ID of the order to update.
     * @param orderDTO DTO containing the new status.
     * @return Updated OrderDTO
     */
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable int orderId,
            @RequestBody @Valid OrderDTO orderDTO) {
        OrderDTO updatedOrder = orderMapper.toDTO(orderService.updateOrderStatus(orderId, orderDTO.status()));
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Cancel an order.
     *
     * @param orderId ID of the order to cancel.
     * @return No content
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable int orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
