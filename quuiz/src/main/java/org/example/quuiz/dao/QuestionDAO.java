package org.example.quuiz.dao;

import org.example.quuiz.entity.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private static final String URL = "jdbc:sqlite:quuuuiz.db";

    public List<Question> getQuestions() throws SQLException {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions";
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                questions.add(new Question(
                        resultSet.getInt("id"),
                        resultSet.getString("question_text"),
                        resultSet.getString("answer1"),
                        resultSet.getString("answer2"),
                        resultSet.getString("answer3"),
                        resultSet.getString("answer4"),
                        resultSet.getInt("correct_answer")
                ));
            }
        }
        return questions;
    }
}