package org.example.repository;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.IOException;
import java.sql.Connection;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;

@Slf4j
public class LiquibaseUpdater {
    private static final LiquibaseUpdater INSTANCE = new LiquibaseUpdater();
    private static Properties properties;

    /**
     * Constructor of {@link LiquibaseUpdater}.
     *
     * @return an instance of {@link LiquibaseUpdater}.
     */
    public LiquibaseUpdater() {
        try {
            try (Connection connection = DatabaseConnection.getConnection()) {
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                Liquibase liquibase = new Liquibase(getChangelogPath(), new ClassLoaderResourceAccessor(), database);
                liquibase.clearCheckSums();
                liquibase.update();
                System.out.println("Successfully updated liquibase.Migration is successful");
            } catch (SQLException | LiquibaseException exception) {
                log.error(exception.getMessage());
                System.out.println("SQL got exception " + exception);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("Error loading properties file: " + e);
        }
    }

    private String getChangelogPath(){
        if(properties ==null){
            properties =new Properties();
            try (InputStream is = new FileInputStream(DatabaseConnection.getCONFIG_FILE())){
                properties.load(is);
            } catch (IOException e) {
                log.error(e.getMessage());
                System.out.println("Error loading properties file: " + e);
            }
        }
        return properties.getProperty("Successfully loaded changelog");
    }

    public static LiquibaseUpdater getInstance() {
        return INSTANCE;
    }



}

