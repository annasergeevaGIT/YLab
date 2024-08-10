package org.example.in;

import lombok.AllArgsConstructor;
import org.example.model.UserRole;
import org.example.model.User;
import org.example.service.UserService;
import java.util.List;
import java.util.Scanner;
/**
 * Handling user (admin) input to manipulate with user.
 */
@AllArgsConstructor
public class UserController {
    private UserService userService;

    /**
     *  Pring all users registered in the system with all roles.
     */
    public void listUsers() {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }
    /**
     * Reading the input to change user role.
     */
    public void changeUserRole() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input user ID: ");
        int userId = scanner.nextInt();
        System.out.print("Choose user role (CUSTOMER, MANAGER, ADMIN): ");
        String roleStr = scanner.next();
        UserRole newRole = UserRole.valueOf(roleStr.toUpperCase());

        userService.updateUserRole(userId, newRole);
        System.out.println("User role is updated.");
    }
    /**
     * Reading the input to add new user.
     */
    public void addUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Login: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Choose user role: (CUSTOMER, MANAGER, ADMIN): ");
        String roleStr = scanner.nextLine();
        UserRole role = UserRole.valueOf(roleStr.toUpperCase());

        userService.addUser(username, password, role);
        System.out.println("User is added.");
    }

    /**
     * Reading the input to remove user.
     */
    public void removeUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose user ID: ");
        int userId = scanner.nextInt();
        userService.deleteUser(userId);
    }
}
