package org.example.in;

import org.example.model.User;

import java.util.Scanner;

public class MainMenuController {
    /**
     * Shows an entry menu for all user roles.
     */
    public static void mainMenu() {
        System.out.println("1. Register, 2. Login, 3.Exit");
        System.out.print("Choose an option: ");
    }

    /**
     * Shows admin menu, let to perform actions based on the choice.
     *
     * @param scanner
     * @param userController
     * @param loggedInUser
     * @param carController
     * @param orderController
     * @param searchController
     * @param authController
     */
    public static void adminMenu(Scanner scanner, UserController userController, User loggedInUser, CarController carController, OrderController orderController, SearchController searchController, AuthController authController) {
        while (true) {
            System.out.println("1. List all users");
            System.out.println("2. Change user role");
            System.out.println("3. Add new user");
            System.out.println("4. Remove user");
            System.out.println("5. Add new car");
            System.out.println("6. List all cars");
            System.out.println("7. Update car information");
            System.out.println("8. Delete car");
            System.out.println("9. Place an order");
            System.out.println("10. List all orders");
            System.out.println("11. Update the order status");
            System.out.println("12. Cancel orders");
            System.out.println("13. Search cars");
            System.out.println("14. Search orders");
            System.out.println("15. Search users by role");
            System.out.println("16. Search users by order count"); // doesnt work
            System.out.println("17. Filter Audit Logs");
            System.out.println("18. Export Audit Logs");
            System.out.println("19. Exit");
            System.out.print("Choose and option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    userController.listUsers();
                    break;
                case 2:
                    userController.changeUserRole();
                    break;
                case 3:
                    userController.addUser();
                    break;
                case 4:
                    userController.removeUser();
                    break;
                case 5:
                    carController.addCar();
                    break;
                case 6:
                    carController.listCars();
                    break;
                case 7:
                    carController.updateCar();
                    break;
                case 8:
                    carController.deleteCar();
                    break;
                case 9:
                    orderController.createOrder(loggedInUser.getId());
                    break;
                case 10:
                    orderController.listOrders();
                    break;
                case 11:
                    orderController.updateOrderStatus();
                    break;
                case 12:
                    orderController.cancelOrder();
                    break;
                case 13:
                    searchController.searchCars();
                    break;
                case 14:
                    searchController.searchOrders();
                    break;
                case 15:
                    searchController.filterUsersByRole();
                    break;
                case 16:
                    searchController.sortUsersByPurchases();
                    break;
                case 17:
                    searchController.filterAuditLogs();
                    break;
                case 18:
                    searchController.exportLogs();
                    break;
                case 19:
                    System.out.println("Exit.");
                    return;
                default:
                    System.out.println("Wrong option. Try again.");
            }
        }
    }

    /**
     * Shows manager menu, let to perform actions based on the choice.
     *
     * @param scanner
     * @param carController
     * @param orderController
     * @param searchController
     */
    public static void managerMenu(Scanner scanner, CarController carController, OrderController orderController, SearchController searchController) {
        while (true) {
            System.out.println("1. Search cars");
            System.out.println("2. Search orders");
            System.out.println("3. Search users by role");
            System.out.println("4. Sort users by order count");
            System.out.println("5. Add new car");
            System.out.println("6. Update car information");
            System.out.println("7. Delete car");
            System.out.println("8. List orders");
            System.out.println("9. Change order status");
            System.out.println("10. Cancel order");
            System.out.println("11. Exit");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    carController.addCar();
                    break;
                case 2:
                    carController.updateCar();
                    break;
                case 3:
                    searchController.filterUsersByRole();
                    break;
                case 4:
                    searchController.sortUsersByPurchases();
                    break;
                case 5:
                    carController.addCar();
                    break;
                case 6:
                    carController.updateCar();
                    break;
                case 7:
                    carController.deleteCar();
                    break;
                case 8:
                    orderController.listOrders();
                    break;
                case 9:
                    orderController.updateOrderStatus();
                    break;
                case 10:
                    orderController.cancelOrder();
                    break;
                case 11:
                    System.out.println("Exit.");
                    return;
                default:
                    System.out.println("Wrong option. Try again.");
            }
        }
    }

    /**
     * Shows customer menu, let to perform actions based on the choice.
     *
     * @param scanner
     * @param carController
     * @param orderController
     * @param loggedInUser
     * @param searchController
     */
    public static void customerMenu(Scanner scanner, CarController carController, OrderController orderController, User loggedInUser, SearchController searchController) {
        while (true) {
            System.out.println("1. List cars");
            System.out.println("2. Place an order");
            System.out.println("3. Search cars");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    carController.listCars();
                    break;
                case 2:
                    orderController.createOrder(loggedInUser.getId());
                    break;
                case 3:
                    searchController.searchCars();
                    break;
                case 4:
                    System.out.println("Exit.");
                    return;
                default:
                    System.out.println("Wrong option. Try again.");
            }
        }
    }
}
