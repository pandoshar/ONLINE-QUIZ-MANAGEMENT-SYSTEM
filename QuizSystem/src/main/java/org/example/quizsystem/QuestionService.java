package org.example.quizsystem;

import org.example.quizsystem.models.Question;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionService {
    private final DatabaseHelper dbHelper;

    public QuestionService(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions";

        try (Connection connection = dbHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                questions.add(new Question(
                        resultSet.getInt("questionId"),
                        resultSet.getString("questionText"),
                        resultSet.getString("optionA"),
                        resultSet.getString("optionB"),
                        resultSet.getString("optionC"),
                        resultSet.getString("optionD"),
                        resultSet.getInt("correctAnswerIndex")
                ));
            }
        } catch (Exception e) {
            System.err.println("Error fetching questions: " + e.getMessage());
        }

        return questions;
    }
}
