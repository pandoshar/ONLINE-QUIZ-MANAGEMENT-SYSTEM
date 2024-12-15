package org.example.quizsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import org.example.quizsystem.DatabaseHelper;

public class MainController {

    @FXML
    private TextField usernameField;

    private UserService userService;

    public void initialize() {
        userService = new UserService(new DatabaseHelper());
    }

    @FXML
    private void startQuiz() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Username is required");
            alert.showAndWait();
            return;
        }

        User user = userService.getUserByUsername(username);
        if (user == null) {
            user = new User(0, username);
            userService.saveUser(user);
        }

        // Переход к экрану теста
        // В этом месте создайте логику для перехода в следующий экран
    }
}
