package org.example;

import org.example.loggingstarter.EnableLogging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class to start the Spring Boot application.
 */
@SpringBootApplication
@EnableLogging // This enables the logging aspect
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Application started. Please use HTTP requests to interact with the application.");
    }
}

