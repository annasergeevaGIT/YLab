# CarShop-Service

# Description
A simple console application for managing a car dealership. Supports user registration and authorization with role management, viewing the list of registered clients and employees, and adding and editing employee information.

# Project Structure
- **model**: Contains classes for data representation.
- **service**: Contains the application logic.
- **in**: Controllers for handling input.
- **out**: Repositories for data storage.
- **main**: The entry point of the application.

## Build and Run
1. Clone the repository.
2. Navigate to the project directory.
3. Compile the project: `javac main/Main.java`
4. Run the project: `java main.Main`

## Usage
1. When the application starts, a root Admin will be created with the login `root` and password `root`.
2. Register a new user or log in with an existing login.
3. Login as root Admin to manage user roles and add new users.
4. Users with the ADMIN and MANAGER roles can manage vehicles and orders.
5. Users with the CUSTOMER role can view available vehicles and create orders.

# Features
User registration and authentication (admin, manager, customer).
Car management (add, edit, delete, view list).
Processing car purchase orders.
View and manage orders (search, change status, cancel).
User action logging.

# Requirements
Java 17
Maven (for dependency management and project build)
JUnit5 (for testing)

# Project Structure
CarShopService/
│	└──	src/
│		├──	main/
│		│	└──java/
│		│		├── in/
│		│		│   ├── AuthController.java
│		│		│   ├── CarController.java
│		│		│   ├── OrderController.java
│		│		│   ├── SearchController.java
│		│		│   └── UserController.java
│		│		├── model/
│		│		│	├── AuditLog.java
│		│		│   ├── Car.java
│		│		│	├── CarStatus.java
│		│		│   ├── Order.java
│		│		│	├── OrderStatus.java
│		│		│   ├── User.java
│		│		│   └── UserRole.java
│		│		├── repository/
│		│		│   ├── AuditRepository.java
│		│		│   ├── CarRepository.java
│		│		│   ├── OrderRepository.java
│		│		│   └── UserRepository.java
│		│		├── service/
│		│		│   ├── AuditService.java
│		│		│   ├── AuthService.java
│		│		│   ├── CarService.java
│		│	    │	├── OrderService.java
│		│		│   ├── SearchService.java
│		│		│   └── UserService.java
│		│		└── main/			
│		│			└── Main.java
│		└──	test/
│			└──java/
│				└── service/
│					├── AuditServiceTest.java
│					├── AuthServiceTest.java
│					├── CarServiceTest.java
│					├── OrderServiceTest.java
│					├── SearchServiceTest.java
│					└── UserServiceTest.java
├── pom.xml
└── README.md

# Installation
Ensure you have Java 17 and Maven installed.
Clone the repository:
git clone https://github.com/annasergeevaGIT/YLab
Navigate to the project directory:
cd CarShopService
Build the project using Maven:
mvn clean install

# Running the Application
After a successful build, run the following command to start the application:
mvn exec:java -Dexec.mainClass="Main"
Follow the console instructions to interact with the application.

# Testing
To run tests, execute the following command:
mvn test

# Usage Examples
1. Register a New User
To register a new user, run the application and select the registration option. You will be prompted to enter the username, password, and role.
Enter command: register
Enter username: john_doe
Enter password: password123
Enter role (ADMIN, MANAGER, CUSTOMER): CUSTOMER
User registered successfully.

2. Login
To login, use the following command and provide your username and password.
Enter command: login
Enter username: john_doe
Enter password: password123
Login successful.

3. Add a New Car
After logging in as an administrator or manager, you can add a new car to the inventory.
Enter command: add_car
Enter car make: Toyota
Enter car model: Corolla
Enter car year: 2020
Enter car price: 20000
Enter car condition (New, Used): New
Car added successfully.

4. List All Cars
To view all cars in the inventory, use the following command.
Enter command: list_cars
Toyota Corolla (2020) - $20000 - New
Honda Civic (2019) - $18000 - Used

5. Place an Order
To place an order, specify the car model and customer information.
Enter command: place_order
Enter car model: Toyota Corolla
Enter customer username: john_doe
Order placed successfully.

6. Update an Order Status
Update the status of an existing order.
Enter command: update_order_status
Enter order ID: Order1
Enter new status (PENDING, COMPLETED, CANCELED): COMPLETED
Order status updated successfully.

7. View Order History
To view the history of orders, use the following command.
Enter command: view_orders
Order ID: Order1 - Toyota Corolla - john_doe - COMPLETED
Order ID: Order2 - Honda Civic - jane_doe - PENDING

8. Add a Customer
Add a new customer to the system.
Enter command: add_customer
Enter username: jane_doe
Enter full name: Jane Doe
Enter contact information: 987-654-3210
Customer added successfully.

9. List All Customers
List all customers in the system.
Enter command: list_customers
Username: john_doe - John Doe - 123-456-7890
Username: jane_doe - Jane Doe - 987-654-3210

10. Add an Employee
Add a new employee to the system.
Enter command: add_employee
Enter username: admin
Enter full name: Admin User
Enter role: Manager
Employee added successfully.

11. Export Audit Log
Export the audit log to a text file for analysis.
Enter command: export_audit_log
Enter filename: audit_logs.txt
Audit log exported successfully.

12. View Audit Log
View the audit log to see recent actions.
Enter command: view_audit_log
Action: Login - User: john_doe - Date: 2024-08-03T10:15:00
Action: Add Car - User: admin - Date: 2024-08-03T10:20:00
