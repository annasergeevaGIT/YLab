package org.example.in;

import org.example.command.*;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.repository.*;
import org.example.service.*;
import java.util.*;

public class MainMenuController {
    private static final Map<Integer, Command> adminCommands = new HashMap<>();
    private static final Map<Integer, Command> managerCommands = new HashMap<>();
    private static final Map<Integer, Command> customerCommands = new HashMap<>();

    /**
     * Start the application.
     */
    public static void startApp() {
        // Apply database migrations using Liquibase
        LiquibaseUpdater.getInstance();

        UserRepository userRepository = new UserRepository();
        CarRepository carRepository = new CarRepository();
        OrderRepository orderRepository = new OrderRepository();
        AuditRepository auditRepository = new AuditRepository();

        AuditService auditService = new AuditService(auditRepository);
        AuthService authService = new AuthService(userRepository, auditService);
        UserService userService = new UserService(userRepository);
        CarService carService = new CarService(carRepository, auditService, authService);
        SearchService searchService = new SearchService(carRepository, orderRepository, userRepository, auditRepository);
        OrderService orderService = new OrderService(orderRepository, carRepository, userRepository, auditService);

        AuthController authController = new AuthController(authService);
        UserController userController = new UserController(userService);
        CarController carController = new CarController(carService);
        SearchController searchController = new SearchController(searchService, auditService,authService);
        OrderController orderController = new OrderController(orderService, authService);

        initializeCommands(userController, carController, orderController, searchController);

        Scanner scanner = new Scanner(System.in);

        // Create a root admin user, set login and password for root Admin
        authService.registerAdmin("root", "root");
        User loggedInUser = null;

        // Print the main menu and redirect based on user role.
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
                        adminMenu(scanner);
                    } else if (loggedInUser.getRole() == UserRole.MANAGER) {
                        managerMenu(scanner);
                    } else if (loggedInUser.getRole() == UserRole.CUSTOMER) {
                        customerMenu(scanner);
                    }
                }
            } else if (choice == 3) {
                System.out.print("Bye!");
                break;
            }
        }
    }

    /**
     * Shows the main menu for all user roles.
     */
    public static void mainMenu() {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    /**
     * Initializes command mappings.
     */
    public static void initializeCommands(UserController userController, CarController carController, OrderController orderController, SearchController searchController) {
        adminCommands.put(1, new ListUsersCommand(userController));
        adminCommands.put(2, new ChangeUserRoleCommand(userController));
        adminCommands.put(3, new AddUserCommand(userController));
        adminCommands.put(4, new RemoveUserCommand(userController));
        adminCommands.put(5, new AddCarCommand(carController));
        adminCommands.put(6, new ListCarsCommand(carController));
        adminCommands.put(7, new UpdateCarCommand(carController));
        adminCommands.put(8, new DeleteCarCommand(carController));
        adminCommands.put(9, new CreateOrderCommand(orderController));
        adminCommands.put(10, new ListOrderCommand(orderController));
        adminCommands.put(11, new UpdateOrderStatusCommand(orderController));
        adminCommands.put(12, new CancelOrderCommand(orderController));
        adminCommands.put(13, new SearchCarsCommand(searchController));
        adminCommands.put(14, new SearchOrdersCommand(searchController));
        adminCommands.put(15, new SearchUsersCommand(searchController));;
        adminCommands.put(16, new SearchAuditLogsCommand(searchController));
        adminCommands.put(17, new ExportLogsCommand(searchController));

        managerCommands.put(1, new SearchCarsCommand(searchController));
        managerCommands.put(2, new SearchOrdersCommand(searchController));
        managerCommands.put(3, new SearchUsersCommand(searchController));
        managerCommands.put(4, new ListCarsCommand(carController));
        managerCommands.put(5, new AddCarCommand(carController));
        managerCommands.put(6, new UpdateCarCommand(carController));
        managerCommands.put(7, new DeleteCarCommand(carController));
        managerCommands.put(8, new ListOrderCommand(orderController));
        managerCommands.put(9, new UpdateOrderStatusCommand(orderController));
        managerCommands.put(10, new CancelOrderCommand(orderController));

        customerCommands.put(1, new ListCarsCommand(carController));
        customerCommands.put(2, new CreateOrderCommand(orderController));
        customerCommands.put(3, new SearchCarsCommand(searchController));
    }

    /**
     * Admin menu actions.
     *
     * @param scanner
     */
    public static void adminMenu(Scanner scanner) {
        while (true) {
            adminInputOptions();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            Command command = adminCommands.get(choice);

            if (command != null) {
                command.execute();
            } else if (choice == 19) {
                System.out.println("Exit.");
                break;
            } else {
                System.out.println("Unknown option. Try again.");
            }
        }
    }

    private static void adminInputOptions() {
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
        System.out.println("15. Search users");
        System.out.println("16. Filter Audit Logs");
        System.out.println("17. Export Audit Logs");
        System.out.println("18. Exit");
        System.out.print("Choose an option: ");
    }

    /**
     * Manager menu actions.
     *
     * @param scanner
     */
    public static void managerMenu(Scanner scanner) {
        while (true) {
            System.out.println("1. Search cars");
            System.out.println("2. Search orders");
            System.out.println("3. Search users by role");
            System.out.println("4. List all cars");
            System.out.println("5. Add new car");
            System.out.println("6. Update car information");
            System.out.println("7. Delete car");
            System.out.println("8. List orders");
            System.out.println("9. Change order status");
            System.out.println("10. Cancel order");
            System.out.println("11. Exit");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();

            Command command = managerCommands.get(choice);

            if (command != null) {
                command.execute();
            } else if (choice == 11) {
                System.out.println("Exit.");
                break;
            } else {
                System.out.println("Unknown option. Try again.");
            }
        }
    }

    /**
     * Customer menu actions.
     *
     * @param scanner
     */
    public static void customerMenu(Scanner scanner) {
        while (true) {
            System.out.println("1. List cars");
            System.out.println("2. Place an order");
            System.out.println("3. Search cars");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            Command command = customerCommands.get(choice);

            if (command != null) {
                command.execute();
            } else if (choice == 4) {
                System.out.println("Exit.");
                break;
            } else {
                System.out.println("Unknown option. Try again.");
            }
        }
    }
}
