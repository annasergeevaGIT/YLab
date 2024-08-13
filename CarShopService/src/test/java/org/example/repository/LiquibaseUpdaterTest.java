package org.example.repository;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.var;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class LiquibaseUpdaterTest {

    private static PostgreSQLContainer<?> postgresContainer;
    private static Connection connection;

    @BeforeAll
    public static void setUp() throws SQLException {
        // Start PostgreSQL container
        postgresContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("postgres")
                .withUsername("root")
                .withPassword("root");
        postgresContainer.start();

        // Set up the database connection
        connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );
        DatabaseConnection.setConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );

        // Create necessary database schema if needed
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE SCHEMA IF NOT EXISTS entity_schema");
        }
    }

    @Test
    public void testLiquibaseUpdate() {
        // Run Liquibase migration
        try {
            LiquibaseUpdater updater = LiquibaseUpdater.getInstance();

            // Verify the schema was updated correctly
            // Example: Check if a specific table exists
            try (Statement stmt = connection.createStatement()) {
                var resultSet = stmt.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'entity_schema' AND table_name = 'your_table')");
                if (resultSet.next()) {
                    boolean tableExists = resultSet.getBoolean(1);
                    assertTrue(tableExists, "Table should exist after Liquibase update");
                } else {
                    fail("Failed to check table existence");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Liquibase update failed: " + e.getMessage());
        }
    }

    @AfterAll
    public static void tearDown() {
        if (postgresContainer != null) {
            postgresContainer.stop();
        }
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}