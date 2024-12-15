package org.example.quizsystem;

public class Result {
    private int resultId;
    private int userId;
    private int score;
    private String date;

    public Result(int resultId, int userId, int score, String date) {
        this.resultId = resultId;
        this.userId = userId;
        this.score = score;
        this.date = date;
    }

    public int getResultId() {
        return resultId;
    }

    public int getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }
}
