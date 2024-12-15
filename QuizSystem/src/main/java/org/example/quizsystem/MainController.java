package org.example.quizsystem;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private TextField nameField;

    @FXML
    private void startQuiz() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            showAlert("Please enter your name.");
        } else {
            DatabaseHelper.saveUser(name);
            // Get the userId of the newly created user
            int userId = DatabaseHelper.getAllUsers().stream()
                    .filter(user -> user.getName().equals(name))
                    .map(User::getId)
                    .findFirst()
                    .orElseThrow();

            try {
                Main.showQuizScreen((Stage) nameField.getScene().getWindow(), userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.show();
    }
}
