# CarShop-Service

# Description
A simple console application for managing a car dealership. Supports user registration and authorization with role management, viewing the list of registered clients and employees, and adding and editing employee information.

# Project Structure
- **model**: Contains classes for data representation.
- **service**: Contains the application logic.
- **in**: Controllers for handling input.
- **out**: Repositories for data storage. Currently empty
- **main**: The entry point of the application.

## Build and Run
1. Clone the repository.
2. Navigate to the project directory.
3. Compile the project: `mvn compile`
4. Run the project: `java -cp .\target\classes org.example.Main`

## Example Usage:

User Registration and Authorization:

A user with the ADMIN role, login "root," and password "root" is automatically created when the application starts.

User Authorization:
A user can log in using a username and password. The CUSTOMER role is automatically assigned, but it can be changed by the ADMIN.

Car and Order Management:
ADMIN and MANAGER can view, add, edit, and delete cars.
ADMIN and MANAGER can manage orders.
CUSTOMER can view available cars and create orders.

Exporting Audit Logs:
ADMIN can export audit logs to a file.

# Features
User registration and authentication (admin, manager, customer).
Car management (add, edit, delete, view list).
Processing car purchase orders.
View and manage orders (search, change status, cancel).
User action logging.

# Requirements
Java 17,
Maven (for dependency management and project build),
JUnit5 (for testing)