package com.example.areslauncher;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

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


}
