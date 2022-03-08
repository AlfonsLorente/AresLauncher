package com.example.areslauncher;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class that helps with some tasks
 */
public class Utils {
    //VARIABLES
    private String[] banedChars = new String[] {".", ",", "\"",";" , "'", "-", "@", "#", "~", "€", "¬", "/", "(", ")", "$", "·", "!", "º", "ª", "=", "+", "\t", "{", "}", "\\", " "};


    //PUBLIC METHODS

    /**
     * Inserts results to the peg table
     * @param context Context
     * @param actualScore ScoreModel
     */
    public void insertResultsPeg(Context context, ScoreModel actualScore){
        //db instantiate
        DBHelper dbHelper = new DBHelper(context);
        ScoreModel oldScore = dbHelper.selectUserPeg(actualScore.getUser());//old score of the user

        //If there is old score
        if(oldScore != null){
            //if the actual score is higher
            if (actualScore.getHighScore() < oldScore.getHighScore()){
                //delete old score and add the new one
                dbHelper.deleteScorePeg(oldScore);
                dbHelper.insertScorePeg(actualScore);
            }//If the actual score is equals to the old one
            else if(actualScore.getHighScore() == oldScore.getHighScore()){
                //Compare the scores times
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                Date newDate = null;
                Date oldDate = null;
                try {
                    newDate = dateFormat.parse(actualScore.getTime());
                    oldDate = dateFormat.parse(oldScore.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //if the new time is lower than the old one
                if (oldDate.compareTo(newDate) > 0){
                    //delete old score and add the new one
                    dbHelper.deleteScorePeg(oldScore);
                    dbHelper.insertScorePeg(actualScore);
                }
            }
        }else{
            //add new score
            dbHelper.insertScorePeg(actualScore);
        }
        dbHelper.close();

    }

    public void insertResults2048(Context context, ScoreModel actualScore){

        DBHelper dbHelper = new DBHelper(context);
        ScoreModel oldScore = dbHelper.selectUser2048(actualScore.getUser());
        //If there is no old score

        if(oldScore != null){

            //if the actual score is higher

            if (actualScore.getHighScore() > oldScore.getHighScore()){

                dbHelper.deleteScore2048(oldScore);
                dbHelper.insertScore2048(actualScore);
            }//If the actual score is equals to the old one
            else if(actualScore.getHighScore() == oldScore.getHighScore()){

                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                Date newDate = null;
                Date oldDate = null;
                try {
                    newDate = dateFormat.parse(actualScore.getTime());
                    oldDate = dateFormat.parse(oldScore.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //if the new time is lower than the old one
                if (oldDate.compareTo(newDate) > 0){
                    dbHelper.deleteScore2048(oldScore);
                    dbHelper.insertScore2048(actualScore);
                }
            }
        }else{
            dbHelper.insertScore2048(actualScore);
        }
        dbHelper.close();

    }

    /**
     * Sets the system UI to immersive and full screen.
     * @param decorView View
     */
    public void hideSystemUI(View decorView) {
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


    /**
     * Tests if the username is well formatted
     * @param context Context
     * @param username String
     * @return boolean
     */
    public boolean usernameTest(Context context, String username){
        boolean isValid = true;
        //cant be null
        if (username == null){
            isValid = false;
            Toast.makeText(context, "Username is null", Toast.LENGTH_SHORT).show();
        }
        //cant be empty
        else if(username.equals("")){
            isValid = false;
            Toast.makeText(context, "Username is required", Toast.LENGTH_SHORT).show();

        }
        //cant be longer than 14 char
        else if(username.length() > 14){
            isValid = false;
            Toast.makeText(context, "Username is too long", Toast.LENGTH_SHORT).show();

        } else{
            //cant contain baned chars
            isValid = banedCharsCompare(context, username);
        }

        return isValid;
    }

    /**
     * Tests if the username is well formatted
     * @param context Context
     * @param password String
     * @return boolean
     */
    public boolean passwordTest(Context context, String password){
        boolean isValid = true;
        //cant be null
        if(password == null){
            isValid = false;
            Toast.makeText(context, "Password is null", Toast.LENGTH_SHORT).show();
        }
        //cant be empty
        else if(password.equals("")){
            isValid = false;
            Toast.makeText(context, "Password is required", Toast.LENGTH_SHORT).show();

        }
        //cant be shorter than 8 char
        else if(password.length() < 8){
            isValid = false;
            Toast.makeText(context, "Password is too short", Toast.LENGTH_SHORT).show();

        }
        //cant contain banned char
        else {
            isValid = banedCharsCompare(context, password);
        }

        return isValid;
    }

    //PRIVATE METHODS

    /**
     * Loops all the banned chars and looks if the text contains any of them.
     * @param context Context
     * @param txt String
     * @return boolean
     */
    private boolean banedCharsCompare(Context context, String txt) {
        for (int i = 0; i < banedChars.length; i++) {
            if (txt.contains(banedChars[i])) {
                Toast.makeText(context, "Forbidden characters found", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


}
