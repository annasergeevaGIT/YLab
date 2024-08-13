package org.example.util;

import org.example.repository.DatabaseConnection;

import java.io.IOException;
import java.sql.*;

public class IDGenerator {

    /**
     * Retrieves the next value from the specified sequence.
     *
     * @param sequenceName the name of the sequence
     * @return the next ID value from the sequence
     */
    public static int getNextId(String sequenceName) {
        int nextId = -1;

        String sql = "SELECT nextval(?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, sequenceName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nextId = rs.getInt(1);
                } else {
                    throw new RuntimeException("Failed to retrieve next ID from sequence: No result returned");
                }
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException("Failed to get next ID from sequence: " + e.getMessage(), e);
        }

        return nextId;
    }
}
