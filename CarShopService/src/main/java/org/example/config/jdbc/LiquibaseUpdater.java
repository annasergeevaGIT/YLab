package org.example.config.jdbc;

import jakarta.annotation.PostConstruct;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@Component
public class LiquibaseUpdater {

    private final DatabaseService databaseService;

    @Value("${liquibase.changelog.path}")
    private String changelogPath;

    @Value("${liquibase.schemaName}")
    private String schemaName;

    public LiquibaseUpdater(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @PostConstruct
    public void updateDatabase() {
        try (Connection connection = databaseService.getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            // Set the schema for Liquibase
            if (schemaName != null) {
                database.setDefaultSchemaName(schemaName);
            }

            Liquibase liquibase = new Liquibase(changelogPath, new ClassLoaderResourceAccessor(), database);
            liquibase.clearCheckSums();
            liquibase.update();
            log.info("Successfully updated liquibase. Migration is successful");
        } catch (SQLException | LiquibaseException | IOException e) {
            log.error("Error during Liquibase update: {}", e.getMessage());
        }
    }
}
