package org.example.config.jdbc;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@Service
public class DatabaseService {

    private static Connection connection;

    private static Properties properties;
    private static String URL, USER, PASS, SCHEMA;
    @Getter
    private static final String CONFIG_FILE = "src/main/resources/config/config.properties";

    public static Connection initConnection() throws SQLException, IOException {
        if (connection == null || connection.isClosed()) {
            try {
                loadConfigProperties();
                connection = DriverManager.getConnection(url(), user(), password());
                connection.createStatement().execute("SET search_path TO " + schema());
                log.info("Connection to the database established");
            } catch (SQLException | IOException e) {
                log.error("Error connecting to the database: {}", e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    private static void loadConfigProperties() throws IOException {
        if (properties == null) {
            properties = new Properties();
            try (InputStream is = new FileInputStream(CONFIG_FILE)) {
                properties.load(is);
                log.info("Config file loaded: url={}, user={}", url(), user());
            } catch (IOException e) {
                log.error("Error loading config file: {}", CONFIG_FILE);
                throw e;
            }
        }
    }

    private static String url() {
        return URL != null ? URL : properties.getProperty("database.url");
    }

    private static String user() {
        return USER != null ? USER : properties.getProperty("database.username");
    }

    private static String password() {
        return PASS != null ? PASS : properties.getProperty("database.password");
    }

    private static String schema() {
        return SCHEMA != null ? SCHEMA : properties.getProperty("database.schema");
    }

    public Connection getConnection() throws SQLException, IOException {
        if (connection == null) connection = initConnection();
        return connection;
    }

    public static void setConnection(String url, String user, String password) {
        DatabaseService.URL = url;
        DatabaseService.USER = user;
        DatabaseService.PASS = password;
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
}
