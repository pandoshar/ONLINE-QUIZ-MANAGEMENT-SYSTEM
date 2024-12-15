package org.example.quuiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.quuiz.dao.UserDAO;
import org.example.quuiz.entity.User;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    @FXML
    private TextField nameField;

    @FXML
    private ImageView imageView;  // Добавляем ImageView, чтобы связать его с FXML

    private UserDAO userDAO = new UserDAO();

    // Метод для инициализации
    @FXML
    public void initialize() {
        // Загрузка изображения
        String imagePath = getClass().getResource("2.jpg").toExternalForm();
        Image image = new Image(imagePath);
        imageView.setImage(image);  // Устанавливаем изображение в ImageView
    }

    @FXML
    public void startQuiz(ActionEvent event) {
        String userName = nameField.getText();
        try {
            // Сохраняем имя пользователя в базе данных
            userDAO.saveUser(userName);

            // Получаем пользователя из базы данных
            User currentUser = userDAO.getUserByName(userName);

            if (currentUser != null) {
                // Переход на quiz.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("quiz.fxml"));
                Scene quizScene = new Scene(loader.load());
                QuizController quizController = loader.getController();

                // Передаем пользователя в QuizController
                quizController.setUser(currentUser);

                // Меняем сцену на quiz.fxml
                Stage stage = (Stage) nameField.getScene().getWindow();
                stage.setScene(quizScene);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
