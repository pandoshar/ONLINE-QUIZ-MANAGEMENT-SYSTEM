package org.example.quuiz.dao;

import org.example.quuiz.entity.User;

import java.sql.*;

public class UserDAO {
    private static final String URL = "jdbc:sqlite:quuuuiz.db";

    public void saveUser(String name) throws SQLException {
        String query = "INSERT INTO users (name) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        }
    }

    public User getUserByName(String name) throws SQLException {
        String query = "SELECT * FROM users WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getInt("id"), resultSet.getString("name"));
            }
        }
        return null;
    }
}