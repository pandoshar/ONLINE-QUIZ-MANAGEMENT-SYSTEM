package org.example.quizsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionService {

    private final DatabaseHelper dbHelper;

    public QuestionService(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Получение всех вопросов
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions";

        // Используем try-with-resources для автоматического закрытия всех ресурсов
        try (Connection connection = dbHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Перебор всех строк в ResultSet
            while (resultSet.next()) {
                Question question = new Question(
                        resultSet.getInt("questionId"),
                        resultSet.getString("questionText"),
                        resultSet.getString("optionA"),
                        resultSet.getString("optionB"),
                        resultSet.getString("optionC"),
                        resultSet.getString("optionD"),
                        resultSet.getString("correctAnswer")
                );
                questions.add(question);
            }

        } catch (SQLException e) {
            System.out.println("Error getting questions: " + e.getMessage());
        }

        return questions;
    }
}
