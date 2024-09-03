# CarShopService
Spring Boot Application managing Car Sop Service. Consist of 3 modules: 
1. aspect-logging - to manage logging and audit
2. spring-swagger - to manage swagger
3. app - main module managing business processes and controllers

## Build and Run
1. Clone the repository.
2. Navigate to the project directory.
3. Compile the project: `mvn compile`
4. run database with: docker-compose up --build
5. Run the project: `java -cp .\target\classes org.example.Main`
6. Swagger: http://localhost:8080/swagger-ui/index.html