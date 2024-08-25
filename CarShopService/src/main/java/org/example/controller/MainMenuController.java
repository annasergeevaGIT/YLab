package org.example.controller;

import org.example.domain.dto.*;
import org.example.mapper.UserMapper;
import org.example.service.*;
import org.example.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/menu")
@Validated
public class MainMenuController {

    private final AuthServiceJdbc authService;
    private final UserMapper userMapper;
    private final Map<Integer, Command> adminCommands;
    private final Map<Integer, Command> managerCommands;
    private final Map<Integer, Command> customerCommands;

    public MainMenuController(
            AuthServiceJdbc authService,
            UserMapper userMapper,
            Map<Integer, Command> adminCommands,
            Map<Integer, Command> managerCommands,
            Map<Integer, Command> customerCommands) {
        this.authService = authService;
        this.userMapper = userMapper;
        this.adminCommands = adminCommands;
        this.managerCommands = managerCommands;
        this.customerCommands = customerCommands;
    }

    /**
     * Endpoint to start the application.
     */
    @PostMapping("/start")
    public ResponseEntity<String> startApp() {
        // Apply database migrations using Liquibase
        DatabaseService databaseService = new DatabaseService();
        databaseService.applyMigrations();

        // Create a root admin user
        UserDTO adminUserDTO = new UserDTO("root", "root", "ADMIN", null);
        authService.registerAdmin(adminUserDTO);

        return ResponseEntity.ok("Application started and root admin created.");
    }

    /**
     * Endpoint for the main menu.
     */
    @GetMapping
    public ResponseEntity<CommandResponseDTO> mainMenu(
            @RequestParam("role") String role,
            @RequestParam("choice") int choice) {
        CommandResponseDTO response = new CommandResponseDTO();
        Command command = getCommandForRole(role, choice);

        if (command != null) {
            response = command.execute();
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommandResponseDTO("Invalid choice or role."));
        }
    }

    private Command getCommandForRole(String role, int choice) {
        switch (role.toUpperCase()) {
            case "ADMIN":
                return adminCommands.get(choice);
            case "MANAGER":
                return managerCommands.get(choice);
            case "CUSTOMER":
                return customerCommands.get(choice);
            default:
                return null;
        }
    }

    /**
     * Register a new user.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) {
        authService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    /**
     * Log in a user.
     */
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestParam String username, @RequestParam String password) {
        UserDTO loggedInUser = authService.login(username, password);

        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    /**
     * Exit the application.
     */
    @PostMapping("/exit")
    public ResponseEntity<String> exitApp() {
        DatabaseService databaseService = new DatabaseService();
        databaseService.closeConnection();
        return ResponseEntity.ok("Application closed.");
    }
}
