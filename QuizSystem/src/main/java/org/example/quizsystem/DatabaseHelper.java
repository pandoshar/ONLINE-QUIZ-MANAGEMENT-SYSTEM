package org.example.quizsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

    // Параметры подключения к базе данных
    private static final String DB_URL = "jdbc:sqlite:quizsystem.db";  // Путь к базе данных
    private static final String DRIVER = "org.sqlite.JDBC";  // Драйвер для SQLite

    // Статический блок для загрузки драйвера
    static {
        try {
            // Загружаем драйвер JDBC
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading SQLite driver: " + e.getMessage());
        }
    }

    // Метод для получения соединения с базой данных
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Метод для создания таблиц в базе данных (если необходимо)
    public void createTables() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "userId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL" +
                ");";

        String createQuestionsTable = "CREATE TABLE IF NOT EXISTS questions (" +
                "questionId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "question TEXT NOT NULL, " +
                "answer1 TEXT NOT NULL, " +
                "answer2 TEXT NOT NULL, " +
                "answer3 TEXT NOT NULL, " +
                "answer4 TEXT NOT NULL, " +
                "correctAnswer INTEGER NOT NULL" +
                ");";

        String createResultsTable = "CREATE TABLE IF NOT EXISTS results (" +
                "resultId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER NOT NULL, " +
                "score INTEGER NOT NULL, " +
                "date TEXT NOT NULL, " +
                "FOREIGN KEY (userId) REFERENCES users(userId)" +
                ");";

        try (Connection connection = getConnection();
             var stmt = connection.createStatement()) {

            stmt.executeUpdate(createUsersTable);
            stmt.executeUpdate(createQuestionsTable);
            stmt.executeUpdate(createResultsTable);

        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
}
