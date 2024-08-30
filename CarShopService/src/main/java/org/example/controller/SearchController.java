package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.dto.AuditLogDTO;
import org.example.domain.dto.CarDTO;
import org.example.domain.dto.OrderDTO;
import org.example.domain.dto.UserDTO;
import org.example.domain.model.*;
import org.example.mapper.CarMapper;
import org.example.mapper.OrderMapper;
import org.example.mapper.UserMapper;
import org.example.service.AuditService;
import org.example.service.AuthServiceJdbc;
import org.example.service.SearchService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final AuditService auditService;
    private final AuthServiceJdbc authService;

    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @GetMapping("/cars")
    public ResponseEntity<List<CarDTO>> searchCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) CarStatus status) {

        List<Car> cars = searchService.searchCars(brand, model, year, price, status);
        List<CarDTO> carDTOs = carMapper.toDTOList(cars);

        User user = authService.getCurrentUser();
        auditService.logAction(user.getId(), "Searched cars with parameters: brand=" + brand + ", model=" + model
                + ", year=" + year + ", price=" + price + ", status=" + status);

        return new ResponseEntity<>(carDTOs, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> searchOrders(
            @RequestParam(required = false) Integer customerId,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) Integer carId) {

        List<Order> orders = searchService.searchOrders(customerId, status, carId);
        List<OrderDTO> orderDTOs = orderMapper.toDTOList(orders);

        User user = authService.getCurrentUser();
        auditService.logAction(user.getId(), "Searched orders with parameters: customerId=" + customerId + ", status="
                + status + ", carId=" + carId);

        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> searchUsers(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false) Integer orderCount) {

        List<User> users = searchService.searchUsers(userId, username, role, orderCount);
        List<UserDTO> userDTOs = userMapper.toDTOList(users);

        User user = authService.getCurrentUser();
        auditService.logAction(user.getId(), "Searched users with parameters: userId=" + userId + ", username=" + username
                + ", role=" + role + ", orderCount=" + orderCount);

        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @GetMapping("/audit-logs")
    public ResponseEntity<List<AuditLogDTO>> searchAuditLogs(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String action) {

        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : null;
        User user = username != null ? searchService.findByUsername(username) : null;

        List<AuditLog> logs = searchService.filterLogs(start, end, user.getId(), action);
        List<AuditLogDTO> logDTOs = logs.stream()
                .map(log -> new AuditLogDTO(log.getId(), log.getTimestamp(), log.getAction(), log.getUserId()))
                .collect(Collectors.toList());

        User currentUser = authService.getCurrentUser();
        auditService.logAction(currentUser.getId(), "Searched audit logs with parameters: startDate=" + startDate
                + ", endDate=" + endDate + ", username=" + username + ", action=" + action);

        return new ResponseEntity<>(logDTOs, HttpStatus.OK);
    }

    @PostMapping("/export-logs")
    public ResponseEntity<String> exportLogs() {
        try {
            auditService.exportLogs();
            User user = authService.getCurrentUser();
            auditService.logAction(user.getId(), "Exported audit logs to file audit_logs.txt");
            return new ResponseEntity<>("Log files successfully exported", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error while exporting log files: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
