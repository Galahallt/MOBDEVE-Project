package com.mobdeve.s14.espiritu.finez.perez.project.dao;

import java.util.ArrayList;

public interface PongDAO {
    // adds a profile
    boolean addProfile(ProfileModel profile);

    // checks if a user is existing
    ProfileModel checkExisting(String user);

    // adds user's score
    boolean addScore(ScoreModel score);

    // gets all user's scores
    ArrayList<ScoreModel> getAllUserScores(String user);

    // gets user's amount of games
    int getTotalGames(String user);

    // gets all users' highest scores
    ArrayList<ScoreModel> getAllUserHighestScore();
}
