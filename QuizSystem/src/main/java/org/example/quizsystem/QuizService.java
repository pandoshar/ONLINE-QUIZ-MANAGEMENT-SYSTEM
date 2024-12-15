package org.example.quizsystem;

import org.example.quizsystem.Question;
import org.example.quizsystem.QuestionService;

import java.util.List;

public class QuizService {
    private final List<Question> questions;
    private int currentQuestionIndex = 0;

    public QuizService(QuestionService questionService) {
        this.questions = questionService.getAllQuestions();
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size();
    }

    public Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    public boolean checkAnswer(int userAnswer) {
        Question question = questions.get(currentQuestionIndex);
        boolean isCorrect = question.getCorrectAnswerIndex() == userAnswer;
        currentQuestionIndex++;
        return isCorrect;
    }
}
