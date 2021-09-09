package com.mobdeve.s14.espiritu.finez.perez.project.dao;

public class ScoreModel {
    private String username, dateScore, time;
    private int score;

    public ScoreModel() {
    }

    public ScoreModel(String username, int score, String dateScore) {
        this.username = username;
        this.score = score;
        this.dateScore = dateScore;
    }

    public ScoreModel(String username, int score, String dateScore, String time) {
        this.username = username;
        this.score = score;
        this.dateScore = dateScore;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String getDateScore() {
        return dateScore;
    }

    public String getTime() {
        return time;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDateScore(String dateScore) {
        this.dateScore = dateScore;
    }
}
