package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dto.CarDTO;
import org.example.domain.model.Car;
import org.example.domain.model.CarStatus;
import org.example.domain.model.User;
import org.example.domain.model.UserRole;
import org.example.service.CarService;
import org.example.util.DTOValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
/**
 * Tests for the `CarServlet` class.
 */
public class CarServletTest {

    @InjectMocks
    private CarServlet carServlet;

    @Mock
    private CarService carService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private DTOValidator dtoValidator;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter writer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        try {
            when(response.getWriter()).thenReturn(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDoGet_WithCarId() throws Exception {
        Car car = new Car();
        car.setId(1);
        car.setBrand("GAZ-13");
        car.setModel("Chaika");
        car.setYear(2023);
        car.setPrice(30000);
        car.setStatus(CarStatus.AVAILABLE);

        when(request.getParameter("id")).thenReturn("1");
        when(carService.getById(1)).thenReturn(car);
        when(objectMapper.writeValueAsString(car)).thenReturn("{\"id\":1,\"brand\":\"GAZ-13\",\"model\":\"Chaika\",\"year\":2023,\"price\":30000,\"status\":\"AVAILABLE\"}");

        carServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(writer).write("{\"id\":1,\"brand\":\"GAZ-13\",\"model\":\"Chaika\",\"year\":2023,\"price\":30000,\"status\":\"AVAILABLE\"}");
    }

    @Test
    void testDoGet_WithoutCarId() throws Exception {
        List<Car> cars = Arrays.asList(
                new Car(1, "GAZ-13", "Chaika", 2023, 30000, CarStatus.AVAILABLE),
                new Car(2, "GAZ-21", "Volga", 2022, 25000, CarStatus.AVAILABLE)
        );

        when(request.getParameter("id")).thenReturn(null);
        when(carService.getAllCars()).thenReturn(cars);
        when(objectMapper.writeValueAsString(cars)).thenReturn("[{\"id\":1,\"brand\":\"GAZ-13\",\"model\":\"Chaika\",\"year\":2023,\"price\":30000,\"status\":\"AVAILABLE\"},{\"id\":2,\"brand\":\"GAZ-21\",\"model\":\"Volga\",\"year\":2022,\"price\":25000,\"status\":\"AVAILABLE\"}]");

        carServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(writer).write("[{\"id\":1,\"brand\":\"GAZ-13\",\"model\":\"Chaika\",\"year\":2023,\"price\":30000,\"status\":\"AVAILABLE\"},{\"id\":2,\"brand\":\"GAZ-21\",\"model\":\"Volga\",\"year\":2022,\"price\":25000,\"status\":\"AVAILABLE\"}]");
    }

    @Test
    void testDoPost_Success() throws Exception {
        User user = new User();
        user.setRole(UserRole.ADMIN);

        CarDTO carDTO = new CarDTO();
        carDTO.setBrand("GAZ-13");
        carDTO.setModel("Chaika");
        carDTO.setYear(2023);
        carDTO.setPrice(30000);
        carDTO.setStatus("AVAILABLE");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"brand\":\"GAZ-13\",\"model\":\"Chaika\",\"year\":2023,\"price\":30000,\"status\":\"AVAILABLE\"}")));
        when(dtoValidator.validateCarDTO(carDTO)).thenReturn(Arrays.asList());
        doNothing().when(carService).addCar("GAZ-13", "Chaika", 2023, 30000, CarStatus.AVAILABLE);

        carServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(writer).write("Car added successfully");
    }

    @Test
    void testDoPut_Success() throws Exception {
        User user = new User();
        user.setRole(UserRole.ADMIN);

        CarDTO carDTO = new CarDTO();
        carDTO.setBrand("GAZ-13");
        carDTO.setModel("Chaika");
        carDTO.setYear(2023);
        carDTO.setPrice(30000);
        carDTO.setStatus("AVAILABLE");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"brand\":\"GAZ-13\",\"model\":\"Chaika\",\"year\":2023,\"price\":30000,\"status\":\"AVAILABLE\"}")));
        when(dtoValidator.validateCarDTO(carDTO)).thenReturn(Arrays.asList());
        Car car = new Car("GAZ-13", "Chaika", 2023, 30000, CarStatus.AVAILABLE);
        doNothing().when(carService).updateCar(car);

        carServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(writer).write("Car updated successfully");
    }

    @Test
    void testDoDelete_Success() throws Exception {
        User user = new User();
        user.setRole(UserRole.ADMIN);

        when(request.getParameter("id")).thenReturn("1");
        doNothing().when(carService).deleteCar(1);

        carServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(writer).write("Car deleted successfully");
    }

    // Add more tests to cover other scenarios and edge cases.
}
