package org.example.repository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.model.Car;
import org.example.config.jdbc.DatabaseService;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@Repository
public class CarRepository implements Crud<Car>{
    private static final DatabaseService databaseService = new DatabaseService();
    private Connection connection;

    public CarRepository() {
        try {
            this.connection = databaseService.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Connection error: " + e.getMessage(), e);
        }
    }

    /**
     * Saves a car to the PostgreSQL database.
     *
     * @param car the car to save
     */
    @Override
    public void create(Car car) {
        String sql = "INSERT INTO entity_schema.cars (model, brand, year, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, car.getModel());
            stmt.setString(2, car.getBrand());
            stmt.setInt(3, car.getYear());
            stmt.setDouble(4, car.getPrice());
            stmt.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating car failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving car to database: " + e.getMessage(), e);
        }
    }

    /**
     * Returns a list of all cars from the PostgreSQL database.
     *
     * @return the list of cars
     */
    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM entity_schema.cars";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt("id"));
                car.setModel(rs.getString("model"));
                car.setBrand(rs.getString("brand"));
                car.setYear(rs.getInt("year"));
                car.setPrice(rs.getDouble("price"));
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving cars from database: " + e.getMessage(), e);
        }
        return cars;
    }

    /**
     * Find a car by its ID in the PostgreSQL database.
     *
     * @param id the ID of the car to find
     * @return the car if found, otherwise null
     */
    @Override
    public Car findById(int id) {
        Car car = null;
        String sql = "SELECT * FROM entity_schema.cars WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    car = new Car();
                    car.setId(rs.getInt("id"));
                    car.setModel(rs.getString("model"));
                    car.setBrand(rs.getString("brand"));
                    car.setYear(rs.getInt("year"));
                    car.setPrice(rs.getDouble("price"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving car from database: " + e.getMessage(), e);
        }
        return car;
    }

    /**
     * Update the existing car in the PostgreSQL database.
     *
     * @param car the car to update
     */
    @Override
    public void update(Car car) {
        String sql = "UPDATE entity_schema.cars SET model = ?, brand = ?, year = ?, price = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, car.getModel());
            stmt.setString(2, car.getBrand());
            stmt.setInt(3, car.getYear());
            stmt.setDouble(4, car.getPrice());
            stmt.setInt(5, car.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating car in database: " + e.getMessage(), e);
        }
    }

    /**
     * Delete a car by its ID from the PostgreSQL database.
     *
     * @param id the ID of the car to delete
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM entity_schema.cars WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No car found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting car from database: " + e.getMessage(), e);
        }
    }
}
