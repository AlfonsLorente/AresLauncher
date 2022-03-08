package com.example.areslauncher;

/**
 * Class that represents each score
 */
public class ScoreModel {
    //VARIABLES
    private int ID;
    private String user;
    private int highScore;
    private String time;

    //GETTERS AND SETTERS
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
