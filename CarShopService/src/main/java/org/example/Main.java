package org.example;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
/**
 * The main class
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("org.example.config");
        context.refresh();

        System.out.println("Application started. Please use HTTP requests to interact with the application.");
    }
}

