package com.mobdeve.s14.espiritu.finez.perez.project.dao;

public class ScoreModel {
    private String username, password, dateScore;
    private int score;

    public ScoreModel() {

    }

    public ScoreModel(String username, String password, int score, String dateScore) {
        this.username = username;
        this.password = password;
        this.score = score;
        this.dateScore = dateScore;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public String getDateScore() {
        return dateScore;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDateScore(String dateScore) {
        this.dateScore = dateScore;
    }
}
