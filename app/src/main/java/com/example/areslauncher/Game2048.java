package com.example.areslauncher;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Random;

public class Game2048 extends Activity {
    //VARIABLES
    private final ArrayList<ArrayList<Button>> buttons = new ArrayList<ArrayList<Button>>();
    private final ArrayList<ArrayList<String>>  oldButtonsText = new ArrayList<ArrayList<String>>();
    private GridLayout gridLayoutGame;
    private ImageView victorySplash, gameOverSplash;
    private ImageButton backButton, restartButton, undoButton, settingsButton;
    private Animation fadeIn;
    private final Random random = new Random();
    private ConstraintLayout constraintLayout;
    private boolean win = false;
    private boolean isOver = false;
    private TextView score;
    private String oldScore = "0";
    private boolean canUndo=false;
    private boolean activityPressed = false;
    private boolean ableAddNum = false;
    private Chronometer chronometer;


    //ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_2048);

        //Inicialitze variables
        gridLayoutGame = findViewById(R.id.gridLayout_game);
        constraintLayout = findViewById(R.id.constraintLayout);
        backButton = findViewById(R.id.back_button_2048);
        restartButton = findViewById(R.id.restart_button_2048);
        undoButton = findViewById(R.id.undo_button_2048);
        settingsButton = findViewById(R.id.settings_button_2048);
        victorySplash = findViewById(R.id.Victory2048);
        gameOverSplash = findViewById(R.id.GameOver2048);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        score = findViewById(R.id.score);
        chronometer = findViewById(R.id.chrono_2048);
        MenuActivity.effects.addEffect(R.raw.swipe2048_2);
        MenuActivity.effects.addEffect(R.raw.suma);

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        fadeIn.setDuration(2500);
        setListeners();

        //Set up game
        setUpNewButtons();
        setNewNumber();
        setNewNumber();

        setUpOldButtons();
        setColors();


    }

    @Override
    protected void onDestroy() {
        //MenuActivity.effects.removeEffect(R.raw.swipe2048_2);
        //MenuActivity.effects.removeEffect(R.raw.suma);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (!activityPressed){

            MenuActivity.music.pause();
        }
        activityPressed = false;

        super.onPause();
    }

    @Override
    protected void onResume() {
        MenuActivity.music.resume();

        super.onResume();
    }

    private void setListeners() {

        fadeIn.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                activityPressed = true;
                Game2048.this.finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        constraintLayout.setOnTouchListener(new SwipeListener(this){
            //Swipe top
            public void onSwipeTop() {

                updateOldButtons();
                swipeTopNumberSum();
                swipeTopArrangeNumbers();
                resumeGame();

            }

            //Swipe Right
            public void onSwipeRight() {
                updateOldButtons();
                swipeRightNumberSum();
                swipeRightArrangeNumbers();
                resumeGame();

            }

            //Swipe left
            public void onSwipeLeft() {
                updateOldButtons();
                swipeLeftNumberSum();
                swipeLeftArrangeNumbers();
                resumeGame();

            }

            //Swipe Bottom
            public void onSwipeBottom() {
                updateOldButtons();
                swipeBottomNumberSum();
                swipeBottomArrangeNumbers();
                resumeGame();

            }

        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MenuActivity.effects.playEffect(R.raw.menu_pick);
                activityPressed = true;
                Game2048.this.finish();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MenuActivity.effects.playEffect(R.raw.menu_pick);

                activityPressed = true;
                Game2048.this.finish();

                startActivity(new Intent(Game2048.this, Game2048.class));

            }
        });

        undoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MenuActivity.effects.playEffect(R.raw.menu_pick);

                if (canUndo) {
                    updateNewButtons();
                    setColors();
                }else{
                    Toast.makeText(Game2048.this, "Can't undo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityPressed = true;
                MenuActivity.effects.playEffect(R.raw.menu_pick);
                startActivity(new Intent(Game2048.this, MusicActivity.class));
            }
        });

    }



    //SWIPE TOP ARRANGE NUMBERS
    private void swipeTopArrangeNumbers() {
        //Loop all the buttons
        for (int i = 0; i < buttons.size(); i++) {
            for (int j = 0; j < buttons.get(i).size(); j++) {
                //When a button has no number
                if(buttons.get(i).get(j).getText().equals("")){
                    //Loop on the buttons after the found one
                    int k = i+1;
                    while(k < 4){
                        //When a button with number is found
                        if (!buttons.get(k).get(j).getText().equals("")) {
                            MenuActivity.effects.playEffect(R.raw.swipe2048_2);

                            //Switch values
                            buttons.get(i).get(j).setText(buttons.get(k).get(j).getText());
                            buttons.get(k).get(j).setText("");
                            ableAddNum = true;

                            break;
                        }
                        k++;

                    }
                }

            }
        }
    }

    //SWIPE TOP NUMBER SUM
    private void swipeTopNumberSum() {
        //Loop all the buttons
        for(int i = 0; i < buttons.size(); i++ ){
            for(int j = 0; j < buttons.get(i).size(); j++){
                //When a button with number is found
                if (!buttons.get(i).get(j).getText().equals("")) {
                    //Loop on the buttons after the found one
                    int k = i+1;
                    while(k < 4){
                        //When another button with number is found
                        if(!buttons.get(k).get(j).getText().equals("")){
                            //Look if both numbers are equal
                            if(buttons.get(i).get(j).getText().equals(buttons.get(k).get(j).getText())){
                                //Set the number multiplied by 2
                                int num = Integer.parseInt(buttons.get(i).get(j).getText().toString())*2;
                                //make the changes
                                MenuActivity.effects.playEffect(R.raw.suma);
                                buttons.get(i).get(j).setText("" + (num));
                                buttons.get(k).get(j).setText("");
                                //Change score
                                int score = Integer.parseInt(this.score.getText().toString());
                                score += num;
                                this.score.setText("" + score);
                                ableAddNum = true;
                            }
                            break;

                        }
                        k++;
                    }
                }
            }
        }
    }

    //SWIPE BOTTOM ARRANGE NUMBERS
    private void swipeBottomArrangeNumbers() {
        //Reverse loop all the buttons
        for(int i = buttons.size()-1; i >= 0; i-- ){
            for(int j = buttons.get(i).size()-1; j >= 0; j-- ){
                //When a button has no number
                if(buttons.get(i).get(j).getText().equals("")){
                    //Loop on the buttons before the found one
                    int k = i-1;
                    while(k >= 0){
                        //When a button with number is found
                        if (!buttons.get(k).get(j).getText().equals("")) {
                            //Switch values
                            MenuActivity.effects.playEffect(R.raw.swipe2048_2);
                            buttons.get(i).get(j).setText(buttons.get(k).get(j).getText());
                            buttons.get(k).get(j).setText("");
                            ableAddNum = true;
                            break;
                        }
                        k--;

                    }
                }

            }
        }
    }

    //SWIPE BOTTOM NUMBER SUM
    private void swipeBottomNumberSum() {
        //Reverse loop all the buttons
        for(int i = buttons.size()-1; i >= 0; i-- ){
            for(int j = buttons.get(i).size()-1; j >= 0; j-- ){
                //When a button with number is found
                if (!buttons.get(i).get(j).getText().equals("")) {
                    //Loop on the buttons before the found one
                    int k = i-1;
                    while(k >=0){
                        //When another button with number is found
                        if(!buttons.get(k).get(j).getText().equals("")){
                            //Look if both numbers are equal
                            if(buttons.get(i).get(j).getText().equals(buttons.get(k).get(j).getText())){
                                //Set the number multiplied by 2
                                int num = Integer.parseInt(buttons.get(i).get(j).getText().toString())*2;
                                //make the changes
                                MenuActivity.effects.playEffect(R.raw.suma);
                                buttons.get(i).get(j).setText("" + (num));
                                buttons.get(k).get(j).setText("");
                                //Change score
                                int score = Integer.parseInt(this.score.getText().toString());
                                score += num;
                                this.score.setText("" + score);
                                ableAddNum = true;

                            }
                            break;

                        }
                        k--;
                    }
                }
            }
        }
    }

    //SWIPE LEFT ARRANGE NUMBERS
    private void swipeLeftArrangeNumbers() {
        //Loop all the buttons
        for (int j = 0; j <  buttons.size(); j++)  {
            for (int i = 0; i < buttons.size(); i++){
                //When a button has no number
                if(buttons.get(i).get(j).getText().equals("")){
                    //Loop on the buttons after the found one
                    int k = j+1;
                    while(k < 4){
                        //When a button with number is found
                        if (!buttons.get(i).get(k).getText().equals("")) {
                            //Switch values
                            MenuActivity.effects.playEffect(R.raw.swipe2048_2);
                            buttons.get(i).get(j).setText(buttons.get(i).get(k).getText());
                            buttons.get(i).get(k).setText("");
                            ableAddNum = true;
                            break;
                        }
                        k++;

                    }
                }

            }
        }
    }

    //SWIPE LEFT NUMBER SUM
    private void swipeLeftNumberSum() {
        //Loop all the buttons
        for(int i = 0; i < buttons.size(); i++ ){
            for(int j = 0; j < buttons.get(i).size(); j++){
                //When a button with number is found
                if (!buttons.get(i).get(j).getText().equals("")) {
                    //Loop on the buttons after the found one
                    int k = j+1;
                    while(k < 4){
                        //When another button with number is found
                        if(!buttons.get(i).get(k).getText().equals("")){
                            //Look if both numbers are equal
                            if(buttons.get(i).get(j).getText().equals(buttons.get(i).get(k).getText())){
                                //Set the number multiplied by 2
                                int num = Integer.parseInt(buttons.get(i).get(j).getText().toString())*2;
                                //make the changes
                                MenuActivity.effects.playEffect(R.raw.suma);
                                buttons.get(i).get(j).setText("" + (num));
                                buttons.get(i).get(k).setText("");
                                //Change score
                                int score = Integer.parseInt(this.score.getText().toString());
                                score += num;
                                this.score.setText("" + score);
                                ableAddNum = true;

                            }
                            break;

                        }
                        k++;
                    }
                }
            }
        }
    }



    //SWIPE RIGHT ARRANGE NUMBERS
    private void swipeRightArrangeNumbers() {
        //Reverse loop all the buttons
        for(int i = buttons.size()-1; i >= 0; i-- ){
            for(int j = buttons.get(i).size()-1; j >= 0; j-- ){
                //When a button has no number
                if(buttons.get(i).get(j).getText().equals("")){
                    //Loop on the buttons before the found one
                    int k = j-1;
                    while(k >= 0){
                        //When a button with number is found
                        if (!buttons.get(i).get(k).getText().equals("")) {
                            //Switch values
                            MenuActivity.effects.playEffect(R.raw.swipe2048_2);
                            buttons.get(i).get(j).setText(buttons.get(i).get(k).getText());
                            buttons.get(i).get(k).setText("");
                            ableAddNum = true;
                            break;
                        }
                        k--;

                    }
                }

            }
        }
    }

    //SWIPE RIGHT NUMBER SUM
    private void swipeRightNumberSum() {
        //Reverse loop all the buttons
        for(int i = buttons.size()-1; i >= 0; i-- ){
            for(int j = buttons.get(i).size()-1; j >= 0; j-- ){
                //When a button with number is found
                if (!buttons.get(i).get(j).getText().equals("")) {
                    //Loop on the buttons before the found one
                    int k = j-1;
                    while(k >=0){
                        //When another button with number is found
                        if(!buttons.get(i).get(k).getText().equals("")){
                            //Look if both numbers are equal
                            if(buttons.get(i).get(j).getText().equals(buttons.get(i).get(k).getText())){
                                //Set the number multiplied by 2
                                int num = Integer.parseInt(buttons.get(i).get(j).getText().toString())*2;
                                //make the changes
                                MenuActivity.effects.playEffect(R.raw.suma);
                                buttons.get(i).get(j).setText("" + (num));
                                buttons.get(i).get(k).setText("");
                                //Change score
                                int score = Integer.parseInt(this.score.getText().toString());
                                score += num;
                                this.score.setText("" + score);
                                ableAddNum = true;

                            }
                            break;

                        }
                        k--;
                    }
                }
            }
        }
    }



    public void resumeGame() {
        if(ableAddNum) {
            setNewNumber();
        }else{
            Toast.makeText(getApplicationContext(), "Illegal move", Toast.LENGTH_SHORT).show();
        }
        ableAddNum = false;
        setColors();

        serch2024();
        if(win == true){
            chronometer.stop();
            Log.d("chrono", chronometer.getText().toString());
            victorySplash.startAnimation(fadeIn);
            victorySplash.setVisibility(View.VISIBLE);
        }
        checkGameOver();
        if(isOver == true){
            chronometer.stop();
            Log.d("chrono", chronometer.getText().toString());
            gameOverSplash.startAnimation(fadeIn);
            gameOverSplash.setVisibility(View.VISIBLE);
        }

    }

    private void setColors() {
        for(int i = 0; i < buttons.size(); i++) {
            for (int j = 0; j < buttons.size(); j++) {
                TextView button = buttons.get(i).get(j);
                switch (button.getText().toString()){
                    case "2":
                        button.setBackground(getDrawable(R.drawable.button2));
                        break;

                    case "4":
                        button.setBackground(getDrawable(R.drawable.button4));
                        break;

                    case "8":
                        button.setBackground(getDrawable(R.drawable.button8));
                        break;

                    case "16":
                        button.setBackground(getDrawable(R.drawable.button16));
                        break;

                    case "32":
                        button.setBackground(getDrawable(R.drawable.button32));
                        break;

                    case "64":
                        button.setBackground(getDrawable(R.drawable.button64));
                        break;

                    case "128":
                        button.setBackground(getDrawable(R.drawable.button128));
                        break;

                    case "256":
                        button.setBackground(getDrawable(R.drawable.button256));
                        break;

                    case "512":
                        button.setBackground(getDrawable(R.drawable.button512));
                        break;

                    case "1024":
                        button.setBackground(getDrawable(R.drawable.button1024));
                        break;

                    case "2048":
                        button.setBackground(getDrawable(R.drawable.button2048));
                        break;

                    default:
                        button.setBackground(getDrawable(R.drawable.button));
                        break;
                }
            }
        }

    }



    private void checkGameOver() {
        isOver = false;
        boolean canSum = false;

        for(int i = 0; i < buttons.size(); i++) {
            for (int j = 0; j < buttons.size(); j++) {
                String button = buttons.get(i).get(j).getText().toString();
                if (button.equals("")){
                    canSum = true;
                    break;
                }
                if (i == 0 || i == 3 || j == 0 || j == 3) {
                    if (i == 0 && j == 0){
                        if(button.equals(buttons.get(i+1).get(j).getText().toString())
                                || button.equals(buttons.get(i).get(j+1).getText().toString())){
                            canSum = true;
                            break;
                        }

                    }else if(i == 3 && j == 0){
                        if(button.equals(buttons.get(i-1).get(j).getText().toString())
                                || button.equals(buttons.get(i).get(j+1).getText().toString())){
                            canSum = true;
                            break;

                        }

                    }else if(i == 0 && j == 3){
                        if(button.equals(buttons.get(i+1).get(j).getText().toString())
                                || button.equals(buttons.get(i).get(j-1).getText().toString())){
                            canSum = true;
                            break;

                        }

                    }else if(i == 3 && j == 3){
                        if(button.equals(buttons.get(i-1).get(j).getText().toString())
                                || button.equals(buttons.get(i).get(j-1).getText().toString())){
                            canSum = true;
                            break;

                        }

                    }else if(i == 0){
                        if(button.equals(buttons.get(i+1).get(j).getText().toString())
                                || button.equals(buttons.get(i).get(j+1).getText().toString())
                                || button.equals(buttons.get(i).get(j-1).getText().toString())){
                            canSum = true;
                            break;

                        }

                    }else if(i == 3){
                        if(button.equals(buttons.get(i-1).get(j).getText().toString())
                                || button.equals(buttons.get(i).get(j+1).getText().toString())
                                || button.equals(buttons.get(i).get(j-1).getText().toString())){
                            canSum = true;
                            break;

                        }

                    }else if(j == 0){
                        if(button.equals(buttons.get(i+1).get(j).getText().toString())
                                || button.equals(buttons.get(i-1).get(j).getText().toString())
                                || button.equals(buttons.get(i).get(j+1).getText().toString())){
                            canSum = true;
                            break;

                        }

                    }else if(j == 3){
                        if(button.equals(buttons.get(i+1).get(j).getText().toString())
                                || button.equals(buttons.get(i-1).get(j).getText().toString())
                                || button.equals(buttons.get(i).get(j-1).getText().toString())){
                            canSum = true;
                            break;

                        }

                    }
                } else {
                    if(button.equals(buttons.get(i+1).get(j).getText().toString())
                            || button.equals(buttons.get(i-1).get(j).getText().toString())
                            || button.equals(buttons.get(i).get(j+1).getText().toString())
                            || button.equals(buttons.get(i).get(j-1).getText().toString())){
                        canSum = true;
                        break;

                    }

                }
            }
        }

        if(canSum == false){
            isOver = true;
        }

    }


    private void serch2024() {
        for(int i = 0; i < buttons.size(); i++){
            for(int j = 0; j < buttons.size(); j++) {
                win = buttons.get(i).get(j).getText().equals("2048");
            }
        }
    }

    public void setNewNumber() {
        boolean done = false;
        do{
            //make random numbers between the list
            int rand1 = random.nextInt(4);
            int rand2 = random.nextInt(4);
            //Generate de number
            if(buttons.get(rand1).get(rand2).getText().equals("")) {
                buttons.get(rand1).get(rand2).setText(generateNumber().toString());
                done = true;
            }

        }while (!done);

    }
    //Generate random number
    public Integer generateNumber(){
        //Probability of 10 per cent to be a 4
        int rand = new Random().nextInt(10);
        if(rand == 0){
            return 4;
        }else{
            return 2;
        }
    }

    public void setUpNewButtons() {
        //Fill list
        for (int i = 0; i < 4; i++){
            //Fill with arraylists
            buttons.add(new ArrayList<Button>());
            for(int j = 0; j < 4;j++) {
                //fill with numbers
                int num = j;
                switch (i) {
                    case 1:
                        num += 4;
                        break;
                    case 2:
                        num += 8;
                        break;
                    case 3:
                        num += 12;
                        break;

                }
                buttons.get(i).add((Button) gridLayoutGame.getChildAt(num));

            }

        }


    }

    public void setUpOldButtons() {
        canUndo = false;
        //Fill list
        for (int i = 0; i < 4; i++){
            //Fill with arraylists
            oldButtonsText.add(new ArrayList<String>());
            for(int j = 0; j < 4;j++) {
                //fill with numbers
                oldButtonsText.get(i).add(buttons.get(i).get(j).getText().toString());

            }

        }


    }




    private void updateOldButtons(){
        canUndo = true;
        oldScore = score.getText().toString();
        //Fill list
        for (int i = 0; i < 4; i++) {
            //Fill with arraylists
            for (int j = 0; j < 4; j++) {
                oldButtonsText.get(i).set(j, buttons.get(i).get(j).getText().toString());

            }
        }
    }
    private void updateNewButtons(){
        canUndo = false;
        score.setText(oldScore);

        //Fill list
        for (int i = 0; i < 4; i++) {
            //Fill with arraylists
            for (int j = 0; j < 4; j++) {
                buttons.get(i).get(j).setText(oldButtonsText.get(i).get(j));

            }
        }
    }



}