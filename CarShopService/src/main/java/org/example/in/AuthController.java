package org.example.in;

import org.example.dto.UserDTO;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.AuthService;
import java.util.Scanner;
/**
 * Handling user input for logging and authorisation actions.
 */
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * User input to register a new user.
     */
    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Login: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = new User(username, password,UserRole.CUSTOMER,null);
        UserDTO userDTO = UserMapper.toUserDTO(user);

        if (authService.register(userDTO)) {
            System.out.println("Register successful.");
        } else {
            System.out.println("Username already in use.");
        }
    }

    /**
     * User input to log in.
     */
    public User login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Login: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = authService.login(username, password);
        if (user != null) {
            System.out.println("Login successful.");
            return user;
        } else {
            System.out.println("Wrong login or password.");
            return null;
        }
    }
}
