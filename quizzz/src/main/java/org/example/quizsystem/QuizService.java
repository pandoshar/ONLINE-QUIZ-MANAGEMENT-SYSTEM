package org.example.quizsystem;

import java.util.List;

public class QuizService {

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private DatabaseHelper databaseHelper;

    public QuizService(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        this.questions = databaseHelper.getAllQuestions(); // Получаем все вопросы из базы данных
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size();
    }

    public Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    public boolean checkAnswer(int answerIndex) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        boolean isCorrect = currentQuestion.getCorrectAnswerIndex() == answerIndex;
        currentQuestion.setUserAnswer(answerIndex);
        currentQuestion.setCorrect(isCorrect);
        currentQuestionIndex++;
        return isCorrect;
    }

    public void finishQuiz(int userId) {
        // Сохранение результатов в базе данных
        for (Question question : questions) {
            databaseHelper.saveQuizResult(userId, question); // Сохраняем каждый вопрос и ответ пользователя
        }
    }
}
