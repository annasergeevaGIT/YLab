package main;

import in.*;
import model.User;
import model.UserRole;
import repository.*;
import service.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        CarRepository carRepository = new CarRepository();
        OrderRepository orderRepository = new OrderRepository();
        AuditRepository auditRepository = new AuditRepository();

        AuthService authService = new AuthService(userRepository);
        UserService userService = new UserService(userRepository);
        CarService carService = new CarService(carRepository);
        SearchService searchService = new SearchService(carRepository,orderRepository,userRepository, auditRepository);
        OrderService orderService = new OrderService(orderRepository, carRepository, userRepository, auditRepository);

        AuthController authController = new AuthController(authService);
        UserController userController = new UserController(userService);
        CarController carController = new CarController(carService);
        SearchController searchController = new SearchController(searchService);
        OrderController orderController = new OrderController(orderService);

        Scanner scanner = new Scanner(System.in);
        /**
         * Create a root admin user, set login and password for root Admin
         */
        authService.registerAdmin("root", "root");
        User loggedInUser = null;

        while (true) {
            mainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                authController.register();
            } else if (choice == 2) {
                loggedInUser = authController.login();
                    if (loggedInUser != null) {
                        if (loggedInUser.getRole() == UserRole.ADMIN) {
                            adminMenu(scanner, userController, carController, orderController, searchController, authController);
                        } else if (loggedInUser.getRole() == UserRole.MANAGER) {
                            managerMenu(scanner, carController, orderController, searchController);
                        } else if (loggedInUser.getRole() == UserRole.CUSTOMER) {
                            customerMenu(scanner, carController, orderController, loggedInUser, searchController);
                        }
                    }
                }
             else if(choice == 3) {
                System.out.print("Bye!");
                break;
            }
        }
    }


    private static void mainMenu() {
        System.out.println("1. Регистрация");
        System.out.println("2. Вход");
        System.out.println("3. Выход");
        System.out.print("Выберите опцию: ");
    }

    private static void adminMenu(Scanner scanner, UserController userController, CarController carController, OrderController orderController, SearchController searchController, AuthController authController) {
        while (true) {
            System.out.println("1. List users");
            System.out.println("2. Change user role");
            System.out.println("3. Add new user");
            System.out.println("4. Add new car");
            System.out.println("5. Update car information");
            System.out.println("6. Delete car");
            System.out.println("7. List orders");
            System.out.println("8. Update the order status");
            System.out.println("9. Cancel orders");
            System.out.println("10. Search cars");
            System.out.println("11. Search orders");
            System.out.println("12. Search users by role");
            System.out.println("13. Search users by order count"); // doesnt work
            System.out.println("14. Filter Audit Logs");
            System.out.println("15. Export Audit Logs");
            System.out.println("16. Exit");
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
                    carController.addCar();
                    break;
                case 5:
                    carController.updateCar();
                    break;
                case 6:
                    carController.deleteCar();
                    break;
                case 7:
                    orderController.listOrders();
                    break;
                case 8:
                    orderController.updateOrderStatus();
                    break;
                case 9:
                    orderController.cancelOrder();
                    break;
                case 10:
                    carController.addCar();
                    break;
                case 11:
                    carController.updateCar();
                    break;
                case 12:
                    searchController.filterUsersByRole();
                    break;
                case 13:
                    searchController.sortUsersByPurchases();
                    break;
                case 14:
                    searchController.filterAuditLogs();
                    break;
                case 15:
                    authController.exportLogs();
                    break;
                case 16:
                    System.out.println("Exit.");
                    return;
                default:
                    System.out.println("Wrong option. Try again.");
            }
        }
    }

    private static void managerMenu(Scanner scanner, CarController carController, OrderController orderController, SearchController searchController) {
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

    private static void customerMenu(Scanner scanner, CarController carController, OrderController orderController, User loggedInUser, SearchController searchController) {
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
