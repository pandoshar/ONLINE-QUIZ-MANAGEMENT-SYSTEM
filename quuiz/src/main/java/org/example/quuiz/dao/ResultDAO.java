package org.example.quuiz.dao;

import java.sql.*;

public class ResultDAO {
    private static final String URL = "jdbc:sqlite:quuuuiz.db";

    // Метод для сохранения количества правильных ответов
    public void saveResult(int userId, int correctAnswers) throws SQLException {
        String query = "INSERT INTO results (user_id, correct_answers) VALUES (?, ?)";

        // Используем try-with-resources для автоматического закрытия соединений и ресурсов
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Подставляем параметры запроса
            statement.setInt(1, userId);
            statement.setInt(2, correctAnswers);

            // Выполняем запрос
            statement.executeUpdate();
        } catch (SQLException e) {
            // Логируем ошибку для отладки
            System.err.println("Error while saving result: " + e.getMessage());
            throw e;  // Перебрасываем исключение, если необходимо обработать его в другом месте
        }
    }
}