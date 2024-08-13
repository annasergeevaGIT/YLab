package org.example.repository;

import org.example.model.Car;
import org.example.model.CarStatus;
import org.example.repository.CarRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarRepositoryTest {
    private static PostgreSQLContainer<?> postgreSQLContainer;
    private CarRepository carRepository;

    @BeforeAll
    public static void setUp() {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("postgres")
                .withUsername("root")
                .withPassword("root");
        postgreSQLContainer.start();

        // Override the connection properties
        System.setProperty("database.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("database.username", postgreSQLContainer.getUsername());
        System.setProperty("database.password", postgreSQLContainer.getPassword());
    }

    @BeforeEach
    public void init() {
        carRepository = new CarRepository();
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    public void testCreateAndFindById() {
        Car car = new Car("Toyota", "Camry", 2020, 30000.00, CarStatus.AVAILABLE);
        carRepository.create(car);

        Car foundCar = carRepository.findById(car.getId());
        assertNotNull(foundCar);
        assertEquals("Toyota", foundCar.getBrand());
    }

    @Test
    public void testFindAll() {
        List<Car> cars = carRepository.findAll();
        assertNotNull(cars);
        assertTrue(cars.size() > 0);
    }
}
