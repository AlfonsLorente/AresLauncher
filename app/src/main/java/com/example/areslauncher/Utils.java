package com.example.areslauncher;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    String[] banedChars = new String[] {".", ",", "\"",";" , "'", "-", "@", "#", "~", "€", "¬", "/", "(", ")", "$", "·", "!", "º", "ª", "=", "+", "\t", "{", "}", "\\", " "};


    public void insertResultsPeg(Context context, ScoreModel actualScore){

        DBHelper dbHelper = new DBHelper(context);
        ScoreModel oldScore = dbHelper.selectUserPeg(actualScore.getUser());
        //If there is no old score

        if(oldScore != null){
            //if the actual score is higher
            if (actualScore.getHighScore() < oldScore.getHighScore()){

                dbHelper.deleteOldScorePeg(oldScore);
                dbHelper.insertScorePeg(actualScore);
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

                    dbHelper.deleteOldScorePeg(oldScore);
                    dbHelper.insertScorePeg(actualScore);
                }
            }
        }else{
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

                dbHelper.deleteOldScore2048(oldScore);
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
                    dbHelper.deleteOldScore2048(oldScore);
                    dbHelper.insertScore2048(actualScore);
                }
            }
        }else{
            dbHelper.insertScore2048(actualScore);
        }
        dbHelper.close();

    }

    public void hideSystemUI(View decorView) {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
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



    public boolean usernameTest(Context context, String username){
        boolean isValid = true;
        if (username == null){
            isValid = false;
            Toast.makeText(context, "Username is null", Toast.LENGTH_SHORT).show();
        }
        else if(username.equals("")){
            isValid = false;
            Toast.makeText(context, "Username is required", Toast.LENGTH_SHORT).show();

        }
        else if(username.length() > 14){
            isValid = false;
            Toast.makeText(context, "Username is too long", Toast.LENGTH_SHORT).show();

        } else{
            isValid = banedCharsCompare(context, username);
        }

        return isValid;
    }

    public boolean passwordTest(Context context, String password){
        boolean isValid = true;
        if(password == null){
            isValid = false;
            Toast.makeText(context, "Password is null", Toast.LENGTH_SHORT).show();
        }
        else if(password.equals("")){
            isValid = false;
            Toast.makeText(context, "Password is required", Toast.LENGTH_SHORT).show();

        }else if(password.length() < 8){
            isValid = false;
            Toast.makeText(context, "Password is too short", Toast.LENGTH_SHORT).show();

        }else {
            isValid = banedCharsCompare(context, password);
        }

        return isValid;
    }

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
