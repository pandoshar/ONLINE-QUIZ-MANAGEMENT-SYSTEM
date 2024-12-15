package org.example.quizsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class QuizController {

    @FXML
    private Text questionText;

    @FXML
    private RadioButton optionA, optionB, optionC, optionD;

    @FXML
    private Button submitButton;

    private QuestionService questionService;
    private User user;
    private int score;
    private List<Question> questions;
    private int currentQuestionIndex;

    public void initialize() {
        questionService = new QuestionService(new DatabaseHelper());
        questions = questionService.getAllQuestions();
        currentQuestionIndex = 0;
        score = 0;
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionText.setText(question.getQuestionText());
            optionA.setText(question.getOptionA());
            optionB.setText(question.getOptionB());
            optionC.setText(question.getOptionC());
            optionD.setText(question.getOptionD());
        } else {
            endQuiz();
        }
    }

    @FXML
    private void submitAnswer() {
        String selectedAnswer = null;
        if (optionA.isSelected()) selectedAnswer = optionA.getText();
        if (optionB.isSelected()) selectedAnswer = optionB.getText();
        if (optionC.isSelected()) selectedAnswer = optionC.getText();
        if (optionD.isSelected()) selectedAnswer = optionD.getText();

        if (selectedAnswer == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Answer Error");
            alert.setHeaderText("Please select an answer.");
            alert.showAndWait();
            return;
        }

        Question question = questions.get(currentQuestionIndex);
        if (selectedAnswer.equals(question.getCorrectAnswer())) {
            score++;
        }

        currentQuestionIndex++;
        loadNextQuestion();
    }

    private void endQuiz() {
        // Сохранение результата
        Result result = new Result(0, user.getUserId(), score, "2024-12-07");
        ResultService resultService = new ResultService(new DatabaseHelper());
        resultService.saveResult(result);

        // Показываем окно с результатом
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Quiz Completed");
        alert.setHeaderText("Your Score: " + score);
        alert.showAndWait();
    }
}
