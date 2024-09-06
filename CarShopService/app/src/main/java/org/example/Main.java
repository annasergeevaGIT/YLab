package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * The main class to start the Spring Boot application.
 * enable autoconfig, aspects, API SpringDoc
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableSwaggerSpringDoc
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Application started. Please use HTTP requests to interact with the application.");
    }
}

