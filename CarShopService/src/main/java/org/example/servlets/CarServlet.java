package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.CarDTO;
import org.example.model.Car;
import org.example.model.CarStatus;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.CarService;
import org.example.service.AuthService;
import org.example.util.DTOValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@WebServlet("/cars")
public class CarServlet extends HttpServlet {

    private CarService carService;
    private ObjectMapper objectMapper;
    private DTOValidator dtoValidator;
    private AuthService authService;

    private User getCurrentUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = getCurrentUser(req);

        if (currentUser == null || !UserRole.CUSTOMER.equals(currentUser.getRole()) && !UserRole.MANAGER.equals(currentUser.getRole()) && !UserRole.ADMIN.equals(currentUser.getRole())) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Access denied");
            return;
        }

        String idParam = req.getParameter("id");

        if (idParam != null) {
            int carId = Integer.parseInt(idParam);
            Car car = carService.getById(carId);
            if (car != null) {
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(car));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Car not found");
            }
        } else {
            List<Car> cars = carService.getAllCars();
            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(cars));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = getCurrentUser(req);

        if (currentUser == null || !UserRole.ADMIN.equals(currentUser.getRole()) && !UserRole.MANAGER.equals(currentUser.getRole())) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Access denied");
            return;
        }

        CarDTO carDTO = objectMapper.readValue(req.getReader(), CarDTO.class);

        List<String> validationErrors = dtoValidator.validateCarDTO(carDTO);
        if (!validationErrors.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(String.join(", ", validationErrors));
            return;
        }

        CarStatus status = CarStatus.valueOf(carDTO.getStatus().toUpperCase());
        carService.addCar(carDTO.getBrand(), carDTO.getModel(), carDTO.getYear(), carDTO.getPrice(), status);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("Car added successfully");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = getCurrentUser(req);

        if (currentUser == null || !UserRole.ADMIN.equals(currentUser.getRole()) && !UserRole.MANAGER.equals(currentUser.getRole())) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Access denied");
            return;
        }

        CarDTO carDTO = objectMapper.readValue(req.getReader(), CarDTO.class);

        List<String> validationErrors = dtoValidator.validateCarDTO(carDTO);
        if (!validationErrors.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(String.join(", ", validationErrors));
            return;
        }

        CarStatus status = CarStatus.valueOf(carDTO.getStatus().toUpperCase());
        carService.updateCar( carDTO.getBrand(), carDTO.getModel(), carDTO.getYear(), carDTO.getPrice(), status);
        resp.getWriter().write("Car updated successfully");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = getCurrentUser(req);

        if (currentUser == null || !UserRole.ADMIN.equals(currentUser.getRole())) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Access denied");
            return;
        }

        String idParam = req.getParameter("id");
        if (idParam != null) {
            int carId = Integer.parseInt(idParam);
            carService.deleteCar(carId);
            resp.getWriter().write("Car deleted successfully");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Car ID is required");
        }
    }
}
