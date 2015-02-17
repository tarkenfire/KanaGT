package com.hinodesoftworks.kanagt.util;

public class QuizResult {
    public String type = "";
    public int score = 0;
    public long date = 0;
    public long timeTake = 0;

    public QuizResult(String type, int score, long date, long timeTake) {
        this.type = type;
        this.score = score;
        this.date = date;
        this.timeTake = timeTake;
    }
}
