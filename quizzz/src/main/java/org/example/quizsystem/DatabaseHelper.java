package org.example.quizsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:quiz.db"; // Путь к базе данных SQLite

    // Метод для получения соединения с базой данных
    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            throw e;
        }
    }

    // Метод для создания таблиц (если они еще не созданы)
    public void createTables() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "userId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL)";

        String createQuestionsTable = "CREATE TABLE IF NOT EXISTS questions (" +
                "questionId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "questionText TEXT, " +
                "optionA TEXT, " +
                "optionB TEXT, " +
                "optionC TEXT, " +
                "optionD TEXT, " +
                "correctAnswer TEXT)";

        String createResultsTable = "CREATE TABLE IF NOT EXISTS results (" +
                "resultId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "score INTEGER, " +
                "date TEXT, " +
                "FOREIGN KEY(userId) REFERENCES users(userId))";

        // Использование try-with-resources для правильного закрытия ресурсов
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {

            // Создание всех таблиц
            stmt.execute(createUsersTable);
            stmt.execute(createQuestionsTable);
            stmt.execute(createResultsTable);

            System.out.println("Tables created successfully (if not already present).");

        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
}
