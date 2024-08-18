# RESTful Application README

## Running the Application
Deploy the application to a servlet container like Apache Tomcat or Jetty. Access the endpoints using an HTTP client or web browser to interact with the RESTful services.

## Overview
This application has been converted from a console-based interface to a RESTful service. All interactions with the system are now performed via HTTP requests, using JSON for data exchange. The application utilizes Java Servlets for handling HTTP requests, Jackson for JSON serialization/deserialization, and MapStruct for mapping between entities and DTOs. It also includes validation and logging features.

## Features
RESTful API: Communicate with the application through HTTP requests. Servlets handle the routing and processing of requests.
JSON Handling: All data is exchanged in JSON format. Jackson is used for serializing and deserializing JSON.
DTOs and Mapping: Data Transfer Objects (DTOs) are used for data exchange. MapStruct is used to map entities to DTOs and vice versa.
Validation: Incoming DTOs are validated to ensure they meet the required constraints.
Logging and Auditing: Actions are logged with execution time measurement. Aspect-Oriented Programming (AOP) is used for auditing and logging user actions.
Testing: Comprehensive tests are included to ensure the functionality and reliability of the servlets.
## Endpoints
#### User Endpoints
#### Register User

#### Endpoint: POST /users
Description: Registers a new user.
Request Body: UserDTO in JSON format.
Response: JSON response with a success message or error details.
Status Codes:
201 Created - User successfully registered.
400 Bad Request - Invalid user data.
409 Conflict - User already exists.
Login User

#### Endpoint: GET /users
Description: Logs in a user.
Query Parameters: username, password
Response: JSON response with user details or error message.
Status Codes:
200 OK - User successfully logged in.
401 Unauthorized - Invalid credentials.
400 Bad Request - Missing username or password.
Assign Role

#### Endpoint: PUT /users
Description: Assigns a role to a user (admin only).
Query Parameters: username, role
Response: JSON response with success or failure message.
Status Codes:
200 OK - Role assigned successfully.
400 Bad Request - Missing username or role, or invalid role.
403 Forbidden - User does not have permission.
404 Not Found - User not found.
## Order Endpoints
#### Create Order

#### Endpoint: POST /orders
Description: Creates a new order for a customer.
Request Body: OrderDTO in JSON format.
Response: JSON response with success message or error details.
Status Codes:
201 Created - Order created successfully.
400 Bad Request - Invalid order data.
Get Order

#### Endpoint: GET /orders
Description: Retrieves an order by ID.
Query Parameters: id
Response: JSON response with order details or error message.
Status Codes:
200 OK - Order found and returned.
404 Not Found - Order not found.
403 Forbidden - Access denied for customers.
Update Order Status

#### Endpoint: PUT /orders
Description: Updates the status of an existing order.
Request Body: OrderDTO in JSON format.
Response: JSON response with success or failure message.
Status Codes:
200 OK - Order status updated successfully.
400 Bad Request - Invalid order data.
403 Forbidden - Access denied for unauthorized users.
Cancel Order

#### Endpoint: DELETE /orders
Description: Cancels an order.
Query Parameters: id
Response: JSON response with success or failure message.
Status Codes:
200 OK - Order canceled successfully.
400 Bad Request - Missing order ID or invalid data.
403 Forbidden - Access denied for unauthorized users.

## Setup and Configuration
Dependencies: 
Ensure the following libraries are included in your build:

Jackson for JSON handling.
MapStruct for object mapping.
JUnit for testing.
Servlet Configuration: 
Configure your web.xml or use annotations to define servlet mappings and ensure they accept and return JSON.

DTO Annotations:
Add Jackson annotations to your DTOs if necessary for proper serialization/deserialization.

Validation: 
Implement validation logic in the DTO classes and configure the application to use these validations.

Logging and Auditing: 
Set up AOP for logging method execution and auditing user actions.

## Testing
Use JUnit 5 to run the provided tests for the servlets and DTO validation. Ensure all tests pass before deploying the application.

Unit Tests: Located in the src/test/java directory.
Integration Tests: Use tools like Chrome Extensions or Python scripts to send HTTP requests and validate responses.