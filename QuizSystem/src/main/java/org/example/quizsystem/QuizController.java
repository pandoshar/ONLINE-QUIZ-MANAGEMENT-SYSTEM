package org.example.quizsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import org.example.quizsystem.Question;

import java.util.List;

public class QuizController {

    @FXML
    private AnchorPane quizPane;

    @FXML
    private RadioButton option1, option2, option3, option4;

    @FXML
    private Button submitButton, finishButton;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int userId;
    private int score = 0;

    public void initialize(List<Question> questions, int userId) {
        this.questions = questions;
        this.userId = userId;
        showNextQuestion();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            option1.setText(question.getOption1());
            option2.setText(question.getOption2());
            option3.setText(question.getOption3());
            option4.setText(question.getOption4());
        }
    }

    @FXML
    private void submitAnswer() {
        Question question = questions.get(currentQuestionIndex);
        RadioButton selectedOption = (RadioButton) new ToggleGroup().getSelectedToggle();
        if (selectedOption != null && selectedOption.getText().equals(question.getCorrectOption())) {
            score++;
        }
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            showNextQuestion();
        } else {
            finishButton.setVisible(true);
            submitButton.setVisible(false);
        }
    }

    @FXML
    private void finishQuiz() {
        String result = "Your score: " + score + "/" + questions.size();
        DatabaseHelper.saveResult(userId, result);
        // Close the application or show result screen
    }
}
