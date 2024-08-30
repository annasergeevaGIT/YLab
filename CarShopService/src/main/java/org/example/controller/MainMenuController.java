package org.example.controller;

import org.example.domain.dto.UserDTO;
import org.example.service.AuthServiceJdbc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/menu")
@Validated
public class MainMenuController {

    private final AuthServiceJdbc authService;

    public MainMenuController(AuthServiceJdbc authService) {
        this.authService = authService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startApp() {
        // Application start logic can be handled here or elsewhere
        return ResponseEntity.ok("Application started successfully.");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> mainMenu(
            @RequestParam("role") String role,
            @RequestParam("choice") int choice) {

        Map<String, Object> response = new HashMap<>();
        // Placeholder for command execution
        // Here you would execute your command based on the role and choice
        response.put("message", "Executed command for role: " + role + " with choice: " + choice);
        // Add more details to the response map as needed

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) {
        authService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestParam String username, @RequestParam String password) {
        UserDTO loggedInUser = authService.login(username, password);

        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exitApp() {
        // Application shutdown logic can be handled here or elsewhere
        return ResponseEntity.ok("Application closed.");
    }
}
