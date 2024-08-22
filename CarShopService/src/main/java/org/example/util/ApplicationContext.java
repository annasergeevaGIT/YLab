package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.repository.*;
import org.example.service.*;

/**
 * Singleton class that acts as a simple dependency injection container for the application.
 */
public class ApplicationContext {

    private static ApplicationContext instance;

    private final CarRepository carRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AuditRepository auditRepository;

    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final AuthService authService;
    private final AuditService auditService;
    private final SearchService searchService;
    private final ObjectMapper objectMapper;
    private final DTOValidator dtoValidator;




    private ApplicationContext() {
        // Initialize repositories
        this.carRepository = new CarRepository();
        this.orderRepository = new OrderRepository();
        this.userRepository = new UserRepository();
        this.auditRepository = new AuditRepository();

        // Initialize services
        this.auditService = new AuditService(auditRepository);
        this.userService = new UserService(userRepository);
        this.authService = new AuthService(userRepository, auditService);
        this.carService = new CarService(carRepository, auditService, authService);
        this.orderService = new OrderService(orderRepository, carRepository, userRepository, auditService);
        this.searchService = new SearchService(carRepository, orderRepository, userRepository, auditRepository);

        this.objectMapper = new ObjectMapper();  // Jackson ObjectMapper
        this.dtoValidator = new DTOValidator();  // Custom validator
    }

    // Initialize utilities

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public DTOValidator getDtoValidator() {
        return dtoValidator;
    }

    /**
     * Returns the singleton instance of ApplicationContext.
     *
     * @return the ApplicationContext instance
     */
    public static synchronized ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    // Getters for repositories
    public CarRepository getCarRepository() {
        return carRepository;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public AuditRepository getAuditRepository() {
        return auditRepository;
    }

    // Getters for services
    public CarService getCarService() {
        return carService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public AuditService getAuditService() {
        return auditService;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public UserService getUserService() {
        return userService;
    }
}
