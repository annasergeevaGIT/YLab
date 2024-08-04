package in;

import model.Car;
import model.CarStatus;
import service.CarService;

import java.util.List;
import java.util.Scanner;

public class CarController {
    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    public void listCars() {
        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            System.out.println(car);
        }
    }

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
        System.out.print("Input car status: AVAILABLE, RESERVED, SOLD");
        CarStatus status = CarStatus.AVAILABLE;

        Car newCar = new Car(carService.getNextId(), brand, model, year, price, status);
        carService.addCar(newCar);
        System.out.println("Car is added successfully.");
    }

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
            System.out.print("Input new status: ");
            CarStatus status = CarStatus.valueOf(scanner.nextLine().toUpperCase());

            car.setBrand(brand);
            car.setModel(model);
            car.setYear(year);
            car.setPrice(price);
            car.setStatus(status);

            carService.updateCar(car);
            System.out.println("Информация об автомобиле обновлена.");
        } else {
            System.out.println("Автомобиль не найден.");
        }
    }

    public void deleteCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID автомобиля: ");
        int carId = scanner.nextInt();

        carService.deleteCar(carId);
        System.out.println("Автомобиль удален.");
    }
}
