package org.example.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a car in the system.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "cars", schema = "entity_schema")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "car_seq")
    @SequenceGenerator(schema = "entity_schema",
            name = "car_seq", // referencing GeneratedValue annotation
            sequenceName = "entity_schema.car_id_seq",  // name and schema
            allocationSize = 1)
    private int id;

    @Column(name = "brand")
    @NotEmpty(message = "Brand cannot be empty")
    private String brand;

    @Column(name = "model")
    @NotEmpty(message = "Model cannot be empty")
    private String model;

    @Column(name = "year")
    @PastOrPresent(message = "Release year cannot be in the future")
    private int year;

    @Column(name = "price")
    @Positive(message = "Price must be positive")
    private double price;

    @Column(name = "status")
    @NotNull(message = "Status cannot be null")
    private CarStatus status;

    /**
     * Constructs a new Car with the specified brand, model, year, price, and status.
     *
     * @param brand  the car's brand
     * @param model  the car's model
     * @param year   the car's manufacturing year
     * @param price  the car's price
     * @param status the car's status
     */
    public Car(String brand, String model, int year, double price, CarStatus status) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
    }
}
