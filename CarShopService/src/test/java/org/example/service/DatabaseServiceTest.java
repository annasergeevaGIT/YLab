package org.example.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;

public class DatabaseServiceTest {
    private static PostgreSQLContainer<?> postgreSQLContainer;

    @BeforeAll
    public static void setUp() {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest").
                 withDatabaseName("postgres").
                 withUsername("root").
                 withPassword("root");
        postgreSQLContainer.start();

        // Override the connection properties
        System.setProperty("database.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("database.username", postgreSQLContainer.getUsername());
        System.setProperty("database.password", postgreSQLContainer.getPassword());
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    public void getConnection() throws IOException, SQLException {
        Connection expected = null;
        Connection actual = DatabaseService.getConnection();

        assertEquals(expected, actual);
    }
}
