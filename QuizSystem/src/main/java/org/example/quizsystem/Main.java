package org.example.quizsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/Main.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quiz System");
        primaryStage.show();
    }

    public static void showQuizScreen(Stage stage, int userId) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("resources/Quiz.fxml"));
        Scene scene = new Scene(loader.load());

        QuizController controller = loader.getController();
        List<Question> questions = DatabaseHelper.getAllQuestions();
        controller.initialize(questions, userId);

        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
