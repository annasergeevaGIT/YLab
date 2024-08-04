package model;


import lombok.Data;

/**
 * Represents a car in the system.
 */

@Data
public class Car {
    private int id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private CarStatus status;

    /**
     *
     * @param id
     * @param brand
     * @param model
     * @param year
     * @param price
     * @param status  represents the status of the car (AVAILABLE, RESERVED, SOLD)
     */

    public Car(int id, String brand, String model, int year, double price, CarStatus status) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
    }
}
