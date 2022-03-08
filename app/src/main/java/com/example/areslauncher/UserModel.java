package com.example.areslauncher;

/**
 * Class that represents each user
 */
public class UserModel {
    //VARIABLES
    private int ID;
    private String name;
    private String password;

    //GETTERS AND SETTERS
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
