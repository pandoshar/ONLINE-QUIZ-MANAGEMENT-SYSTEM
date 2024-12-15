package org.example.quizsystem;

import java.sql.*;

public class ResultService {

    private final DatabaseHelper dbHelper;

    // Конструктор
    public ResultService(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Сохранение результата в базе данных
    public void saveResult(Result result) {
        String query = "INSERT INTO results (userId, score, date) VALUES (?, ?, ?)";
        try (Connection connection = dbHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Установка параметров запроса
            statement.setInt(1, result.getUserId());
            statement.setInt(2, result.getScore());
            statement.setString(3, result.getDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving result: " + e.getMessage());
        }
    }

    // Получение последнего результата по userId
    public Result getResultByUserId(int userId) {
        String query = "SELECT * FROM results WHERE userId = ? ORDER BY date DESC LIMIT 1";
        try (Connection connection = dbHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Установка параметра для запроса
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            // Если результат найден, возвращаем объект Result
            if (resultSet.next()) {
                return new Result(
                        resultSet.getInt("resultId"),
                        resultSet.getInt("userId"),
                        resultSet.getInt("score"),
                        resultSet.getString("date")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error getting result: " + e.getMessage());
        }
        return null; // Если результата нет
    }
}
