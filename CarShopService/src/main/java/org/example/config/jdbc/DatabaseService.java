package org.example.config.jdbc;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@Service
public class DatabaseService {

    private Connection connection;
    private final Properties properties = new Properties();

    @Value("classpath:config/config.properties")
    private Resource configFile;

    @PostConstruct
    private void loadConfigProperties() throws IOException {
        try (InputStream is = configFile.getInputStream()) {
            properties.load(is);
            log.info("Config file loaded: url={}, user={}", url(), user());
        } catch (IOException e) {
            log.error("Error loading config file: {}", configFile.getFilename());
            throw e;
        }
    }

    public Connection initConnection() throws SQLException, IOException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(url(), user(), password());
                connection.createStatement().execute("SET search_path TO " + schema());
                log.info("Connection to the database established");
            } catch (SQLException e) {
                log.error("Error connecting to the database: {}", e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    public Connection getConnection() throws SQLException, IOException {
        if (connection == null) connection = initConnection();
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                log.info("Connection to the database closed");
            } catch (SQLException e) {
                log.error("Error closing the database connection: {}", e.getMessage());
            }
        }
    }

    private String url() {
        return properties.getProperty("database.url");
    }

    private String user() {
        return properties.getProperty("database.username");
    }

    private String password() {
        return properties.getProperty("database.password");
    }

    private String schema() {
        return properties.getProperty("database.schema");
    }
}
