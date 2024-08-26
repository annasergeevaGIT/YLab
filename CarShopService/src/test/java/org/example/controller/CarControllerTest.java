package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dto.CarDTO;
import org.example.domain.model.Car;
import org.example.domain.model.CarStatus;
import org.example.mapper.CarMapper;
import org.example.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    public void testListCars() throws Exception {
        Car car = new Car(1, "Moskvitch", "AZLK", 1968, 3000.00, CarStatus.AVAILABLE);
        CarDTO carDTO = new CarDTO(1, "Moskvitch", "AZLK", 1968, 3000.00, CarStatus.AVAILABLE);

        when(carService.getAllCars()).thenReturn(Collections.singletonList(car));
        when(carMapper.toDTO(car)).thenReturn(carDTO);

        mockMvc.perform(get("/api/cars")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].brand").value("Moskvitch"))
                .andExpect(jsonPath("$[0].model").value("AZLK"));
    }

    @Test
    public void testAddCar() throws Exception {
        CarDTO carDTO = new CarDTO(1, "Moskvitch", "AZLK", 1968, 3000.00, CarStatus.AVAILABLE);

        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(carDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Car added successfully."));
    }

    @Test
    public void testUpdateCar() throws Exception {
        CarDTO carDTO = new CarDTO(1, "Moskvitch", "AZLK", 1968, 3000.00, CarStatus.AVAILABLE);
        Car car = new Car(1, "Moskvitch", "AZLK", 1968, 3000.00, CarStatus.AVAILABLE);

        when(carService.getById(anyInt())).thenReturn(car);
        when(carMapper.toEntity(carDTO)).thenReturn(car);

        mockMvc.perform(put("/api/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(carDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Car info successfully updated."));
    }

    @Test
    public void testDeleteCar() throws Exception {
        Car car = new Car(1, "Moskvitch", "AZLK", 1968, 3000.00, CarStatus.AVAILABLE);

        when(carService.getById(anyInt())).thenReturn(car);

        mockMvc.perform(delete("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("The car is deleted successfully."));
    }

    // Additional tests for error cases (e.g., not found) can also be added
}
