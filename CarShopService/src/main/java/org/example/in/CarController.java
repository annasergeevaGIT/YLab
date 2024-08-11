package org.example.in;

import org.example.model.Car;
import org.example.model.CarStatus;
import org.example.service.CarService;

import java.util.List;
import java.util.Scanner;

/**
 * Handling user input for car manipulation.
 */
public class CarController {
    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }
    /**
     * Prints the list of all cars.
     */
    public void listCars() {
        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    /**
     * Managing user input to add a new car in the car repository.
     */
    public void addCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input car brand: ");
        String brand = scanner.nextLine();
        System.out.print("Input car model: ");
        String model = scanner.nextLine();
        System.out.print("Input car release year: ");
        int year = scanner.nextInt();
        System.out.print("Input price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        CarStatus status = CarStatus.AVAILABLE; // initial status is available

        carService.addCar(brand, model, year, price, status);
        System.out.println("Car is added successfully.");
    }

    /**
     * Managing user input to update the existing car data.
     */
    public void updateCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input car ID: ");
        int carId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        Car car = carService.getById(carId);
        if (car != null) {
            System.out.print("Input new car brand: ");
            String brand = scanner.nextLine();
            System.out.print("Input mew model: ");
            String model = scanner.nextLine();
            System.out.print("Input new car release year: ");
            int year = scanner.nextInt();
            System.out.print("Input new price: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            System.out.print("Input new status (AVAILABLE, RESERVED, SOLD): ");
            CarStatus status = CarStatus.valueOf(scanner.nextLine().toUpperCase());

            carService.updateCar(brand, model, year, price, status);
            System.out.println("Car info successfully updated.");
        } else {
            System.out.println("The car is not found.");
        }
    }

    /**
     * Managing user input to remove the car
     */
    public void deleteCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input car ID: ");
        int carId = scanner.nextInt();

        carService.deleteCar(carId);
        System.out.println("The car is deleted successfully.");
    }
}
