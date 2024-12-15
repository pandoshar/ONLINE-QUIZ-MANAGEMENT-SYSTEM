package org.example.quuiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import org.example.quuiz.dao.QuestionDAO;
import org.example.quuiz.dao.ResultDAO;
import org.example.quuiz.dao.UserDAO;
import org.example.quuiz.entity.Question;
import org.example.quuiz.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class QuizController {
    @FXML
    private ImageView imageView2;
    @FXML
    private Text questionText;
    @FXML
    private RadioButton answer1, answer2, answer3, answer4;
    @FXML
    private Button nextButton, submitButton, checkButton;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private UserDAO userDAO = new UserDAO();
    private QuestionDAO questionDAO = new QuestionDAO();
    private ResultDAO resultDAO = new ResultDAO();

    private User currentUser; // Для хранения текущего пользователя
    private Map<Integer, Integer> answers = new HashMap<>(); // Мапа для хранения ответов пользователя

    public void initialize() {
        try {
            questions = questionDAO.getQuestions();
            loadNextQuestion();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Проверка пути к изображению
        String imagePath = getClass().getResource("5.png").toExternalForm();
        if (imagePath == null) {
            System.err.println("Image not found");
        } else {
            Image image = new Image(imagePath);
            imageView2.setImage(image);
        }
    }


    // Этот метод вызывается из MainController для установки пользователя
    public void setUser(User user) {
        this.currentUser = user;
    }
    @FXML
    public void loadNextQuestion() {
        // Сбрасываем выбранные радиокнопки
        answer1.setSelected(false);
        answer2.setSelected(false);
        answer3.setSelected(false);
        answer4.setSelected(false);

        // Делаем кнопку "Next" доступной только после выбора ответа
        nextButton.setDisable(true);
        checkButton.setDisable(false);

        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionText.setText(question.getQuestionText());
            answer1.setText(question.getAnswer1());
            answer2.setText(question.getAnswer2());
            answer3.setText(question.getAnswer3());
            answer4.setText(question.getAnswer4());
            currentQuestionIndex++;
        } else {
            submitButton.setVisible(true); // Показываем кнопку "Submit" в конце
            nextButton.setVisible(false);
            checkButton.setVisible(false);
        }
    }

    // Метод для сохранения ответа на текущий вопрос
    private void saveAnswerForCurrentQuestion() {
        int selectedAnswer = -1;

        if (answer1.isSelected()) selectedAnswer = 1;
        else if (answer2.isSelected()) selectedAnswer = 2;
        else if (answer3.isSelected()) selectedAnswer = 3;
        else if (answer4.isSelected()) selectedAnswer = 4;

        if (selectedAnswer != -1) {
            answers.put(currentQuestionIndex - 1, selectedAnswer); // Сохраняем ответ для текущего вопроса
            System.out.println("Question " + currentQuestionIndex + " answer: " + selectedAnswer); // Отладка
            nextButton.setDisable(false); // Разрешаем переход к следующему вопросу
            checkButton.setDisable(true); // Отключаем кнопку "Check" после ответа
        } else {
            System.out.println("No answer selected for question " + currentQuestionIndex); // Отладка
        }
    }

    // Метод для проверки всех ответов и подсчета результата
    private void saveResults() {
        score = 0; // Сбрасываем счётчик перед подсчётом

        // Перебираем все вопросы, чтобы подсчитать результат
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            int userAnswer = answers.getOrDefault(i, -1); // Получаем ответ пользователя, если есть

            if (userAnswer != -1 && userAnswer == question.getCorrectAnswer()) {
                score++; // Если ответ правильный, увеличиваем счёт
            }
            // Для отладки
            System.out.println("Checking Question " + (i + 1) + ": User Answer = " + userAnswer + ", Correct Answer = " + question.getCorrectAnswer());
        }

        // Сохраняем результат в базе данных
        try {
            if (currentUser != null) {
                resultDAO.saveResult(currentUser.getId(), score); // Сохраняем финальный результат
                System.out.println("Final score saved: " + score); // Отладка
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Нажатие на кнопку "Следующий" (переводит на следующий вопрос)
    @FXML
    public void handleNextQuestion(ActionEvent event) {
        saveAnswerForCurrentQuestion(); // Сохраняем ответ перед переходом к следующему вопросу
        loadNextQuestion();
    }

    // Нажатие на кнопку "Check" (сохраняет ответ)
    // Метод для обработки завершения викторины
    @FXML
    public void submitQuiz(ActionEvent event) {
        saveAnswerForCurrentQuestion(); // Сохраняем последний ответ
        saveResults(); // Подсчитываем и сохраняем результат

        // Закрываем приложение после завершения
        System.exit(0);
    }

    public void checkAnswer(ActionEvent actionEvent) {
        saveAnswerForCurrentQuestion();
    }
}
