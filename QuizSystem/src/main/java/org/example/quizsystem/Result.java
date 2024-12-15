package org.example.quizsystem;

public class Result {
    private int resultId;
    private int userId;
    private int score;
    private String date;

    // Конструктор
    public Result(int resultId, int userId, int score, String date) {
        this.resultId = resultId;
        this.userId = userId;
        this.score = score;
        this.date = date;
    }

    // Геттеры и сеттеры
    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
