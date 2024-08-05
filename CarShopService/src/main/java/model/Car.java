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
     * Constructs a new Car with the specified id, brand, model, year, price, and status.
     *
     * @param id     the car's ID
     * @param brand  the car's brand
     * @param model  the car's model
     * @param year   the car's manufacturing year
     * @param price  the car's price
     * @param status the car's status
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
