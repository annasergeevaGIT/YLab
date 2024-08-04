package service;

import out.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import out.CarRepository;
import out.CarService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CarServiceTest {

    private CarService carService;
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        carRepository = mock(CarRepository.class);
        carService = new CarService(carRepository);
    }

    @Test
    public void addCar_success() {
        Car car = new Car("Toyota", "Corolla", 2020, 20000, "New");
        carService.addCar(car);

        verify(carRepository, times(1)).addCar(car);
    }

    @Test
    public void listCars_returnsCars() {
        Car car1 = new Car("Toyota", "Corolla", 2020, 20000, "New");
        Car car2 = new Car("Honda", "Civic", 2019, 18000, "Used");
        when(carRepository.getAllCars()).thenReturn(Arrays.asList(car1, car2));

        List<Car> cars = carService.listCars();

        assertThat(cars).hasSize(2);
        assertThat(cars).contains(car1, car2);
    }
}
