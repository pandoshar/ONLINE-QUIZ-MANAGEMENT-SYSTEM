package org.example.quizsystem;

import org.example.quizsystem.DatabaseHelper;

import java.sql.*;

public class UserService {

    private final DatabaseHelper dbHelper;

    public UserService(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Сохранение пользователя в базе данных
    public void saveUser(User user) {
        String query = "INSERT INTO users (username) VALUES (?)";
        try (Connection connection = dbHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    // Получение пользователя по имени
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = dbHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(resultSet.getInt("userId"), resultSet.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting user: " + e.getMessage());
        }
        return null;
    }
}
