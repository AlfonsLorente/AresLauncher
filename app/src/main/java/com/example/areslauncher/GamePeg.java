package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GamePeg extends AppCompatActivity {
    //Declare variables
    private final ArrayList<ArrayList<ImageButton>> pegs = new ArrayList<ArrayList<ImageButton>>();
    private final ArrayList<ArrayList<Drawable.ConstantState>> oldPegs = new ArrayList<ArrayList<Drawable.ConstantState>>();

    private GridLayout gridLayout;
    private TextView pegsAmount, possibleMoves;
    private ImageView victorySplash, gameOverSplash;
    private ImageButton backButton, restartButton, undoButton;
    private boolean canUndo = false;
    private Chronometer chronometer;

    private int oldi, oldj;
    private Animation fadeIn;
    private boolean activityPressed = false;
    private ImageButton settingsButton;

    //ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_peg);

        //initialize variables
        gridLayout = findViewById(R.id.grid_peg);
        pegsAmount = findViewById(R.id.pegs_amount);
        backButton = findViewById(R.id.back_button_peg);
        restartButton = findViewById(R.id.restart_button_peg);
        undoButton = findViewById(R.id.undo_button_peg);
        settingsButton = findViewById(R.id.settings_button_peg);

        chronometer = findViewById(R.id.chrono_peg);
        possibleMoves = findViewById(R.id.possibleMoves);
        victorySplash = findViewById(R.id.pegVictory);
        gameOverSplash = findViewById(R.id.pegGameOver);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeIn.setDuration(2500);

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        MenuActivity.effects.addEffect(R.raw.peg_pick);
        MenuActivity.effects.addEffect(R.raw.peg_drop);


        setListeners();
        //Start the game
        setUpNewPegs();
        setUpOldPegs();

        resumeGame();


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (!activityPressed) {

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
        fadeIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                activityPressed = true;
                GamePeg.this.finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPressed = true;
                MenuActivity.effects.playEffect(R.raw.menu_pick);
                GamePeg.this.finish();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insertResults();
                activityPressed = true;
                MenuActivity.effects.playEffect(R.raw.menu_pick);
                GamePeg.this.finish();

                startActivity(new Intent(GamePeg.this, GamePeg.class));
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuActivity.effects.playEffect(R.raw.menu_pick);

                if (canUndo) {
                    updateNewPegs();
                } else {
                    Toast.makeText(GamePeg.this, "Can't undo", Toast.LENGTH_SHORT).show();
                }
            }
        });


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityPressed = true;
                MenuActivity.effects.playEffect(R.raw.menu_pick);
                startActivity(new Intent(GamePeg.this, MusicActivity.class));
            }
        });

    }



    private void resumeGame() {
        updatePuntuation();
        if (pegsAmount.getText().toString().equals("1") ){
            insertResults();
            victorySplash.startAnimation(fadeIn);
            victorySplash.setVisibility(View.VISIBLE);
        } else {
            scanGame();
        }


    }

    private void scanGame() {
        int moves = 0;
        for (int i = 0; i < pegs.size(); i++) {
            for (int j = 0; j < pegs.get(i).size(); j++) {

                if (!pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.peginvisible).getConstantState())) {

                    if ((pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState()) ||
                            pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState())) &&
                            (i < pegs.size() - 2)) {

                        if ((pegs.get(i + 1).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) ||
                                (pegs.get(i + 1).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState()))) {
                            if (pegs.get(i + 2).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegempty).getConstantState())) {
                                moves++;

                            }

                        }

                    }

                    if ((pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState()) ||
                            pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState())) &&
                            (i > 1)) {
                        if ((pegs.get(i - 1).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) ||
                                (pegs.get(i - 1).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState()))) {
                            if (pegs.get(i - 2).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegempty).getConstantState())) {
                                moves++;

                            }

                        }

                    }

                    if ((pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState()) ||
                            pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState())) &&
                            (j < pegs.get(i).size() - 2)) {
                        if ((pegs.get(i).get(j + 1).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) ||
                                (pegs.get(i).get(j + 1).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState()))) {
                            if (pegs.get(i).get(j + 2).getBackground().getConstantState().equals(getDrawable(R.drawable.pegempty).getConstantState())) {
                                moves++;
                            }
                        }

                    }

                    if ((pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState()) ||
                            pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState())) &&
                            (j > 1)) {
                        if ((pegs.get(i).get(j - 1).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) ||
                                (pegs.get(i).get(j - 1).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState()))) {
                            if (pegs.get(i).get(j - 2).getBackground().getConstantState().equals(getDrawable(R.drawable.pegempty).getConstantState())) {
                                moves++;
                            }

                        }


                    }
                }
            }
        }
        possibleMoves.setText("" + moves);
        if (moves == 0) {

            insertResults();
            gameOverSplash.startAnimation(fadeIn);
            gameOverSplash.setVisibility(View.VISIBLE);
        }
    }

    //UPDATE PUNTUATION
    private void updatePuntuation() {
        //Loop all the pegs
        int amount = 0;
        for (int i = 0; i < pegs.size(); i++) {
            for (int j = 0; j < pegs.get(i).size(); j++) {
                //Count the total pegs in the game
                if ((pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) ||
                        (pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState()))) {
                    amount++;

                }
            }
        }
        //set the text view with the peg amount
        pegsAmount.setText("" + amount);
    }

    //BUTTON CLICKED
    public void buttonClicked(View view) {
        //Ignore if its an invisible peg
        if (!view.getBackground().getConstantState().equals(getDrawable(R.drawable.peginvisible).getConstantState())) {
            //Search if there is already a clicked button
            boolean isClicked = searchClickedButton();
            //If it is try to move it, if not set it pressed
            if (isClicked) {
                calculateMovement(view);
            } else {
                if (!view.getBackground().getConstantState().equals(getDrawable(R.drawable.pegempty).getConstantState())) {
                    MenuActivity.effects.playEffect(R.raw.peg_pick);

                    view.setBackground(getDrawable(R.drawable.pegpressed));
                }
            }
        }
        resumeGame();

    }


    //CALCULATE MOVEMENT
    private void calculateMovement(View view) {
        updateOldPegs();
        //loop all the pegs
        for (int i = 0; i < pegs.size(); i++) {
            for (int j = 0; j < pegs.get(i).size(); j++) {
                //If found (should always be found)
                if ((pegs.get(i).get(j) == view)) {
                    //check if the clicked peg is empty, if not then just set it pressed and unpress the old one
                    if (view.getBackground().getConstantState().equals(getDrawable(R.drawable.pegempty).getConstantState())) {
                        /*
                            Make the calculation in all possible directions where the pegs, to move, must be 2 squares away
                            and there should be the middle one with 'pegunpressed'
                         */
                        if (oldi - 2 == i && oldj == j) {
                            if (pegs.get(i + 1).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) {
                                //move the pegs
                                MenuActivity.effects.playEffect(R.raw.peg_drop);

                                pegs.get(i).get(j).setBackground(getDrawable(R.drawable.pegunpressed));
                                pegs.get(i + 1).get(j).setBackground(getDrawable(R.drawable.pegempty));
                                pegs.get(i + 2).get(j).setBackground(getDrawable(R.drawable.pegempty));

                            }

                        } else if (oldi + 2 == i && oldj == j) {
                            if (pegs.get(i - 1).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) {
                                //move the pegs
                                MenuActivity.effects.playEffect(R.raw.peg_drop);

                                pegs.get(i).get(j).setBackground(getDrawable(R.drawable.pegunpressed));
                                pegs.get(i - 1).get(j).setBackground(getDrawable(R.drawable.pegempty));
                                pegs.get(i - 2).get(j).setBackground(getDrawable(R.drawable.pegempty));

                            }
                        } else if (oldi == i && oldj - 2 == j) {
                            if (pegs.get(i).get(j + 1).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) {
                                //move the pegs
                                MenuActivity.effects.playEffect(R.raw.peg_drop);

                                pegs.get(i).get(j).setBackground(getDrawable(R.drawable.pegunpressed));
                                pegs.get(i).get(j + 1).setBackground(getDrawable(R.drawable.pegempty));
                                pegs.get(i).get(j + 2).setBackground(getDrawable(R.drawable.pegempty));

                            }
                        } else if (oldi == i && oldj + 2 == j) {
                            if (pegs.get(i).get(j - 1).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) {
                                //move the pegs
                                MenuActivity.effects.playEffect(R.raw.peg_drop);

                                pegs.get(i).get(j).setBackground(getDrawable(R.drawable.pegunpressed));
                                pegs.get(i).get(j - 1).setBackground(getDrawable(R.drawable.pegempty));
                                pegs.get(i).get(j - 2).setBackground(getDrawable(R.drawable.pegempty));
                            }
                        }

                    } else {
                        MenuActivity.effects.playEffect(R.raw.peg_pick);

                        pegs.get(i).get(j).setBackground(getDrawable(R.drawable.pegpressed));
                        pegs.get(oldi).get(oldj).setBackground(getDrawable(R.drawable.pegunpressed));

                    }

                }
            }
        }
    }

    //SEARCH CLICKED BUTTON
    private boolean searchClickedButton() {
        //Loop all the pegs
        for (int i = 0; i < pegs.size(); i++) {
            for (int j = 0; j < pegs.get(i).size(); j++) {
                //If there is an already pressed peg
                if (pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState())) {
                    //set its values and return true
                    oldi = i;
                    oldj = j;
                    return true;
                }
            }
        }

        return false;
    }


    //GET ALL PEGS
    private void setUpNewPegs() {
        //Fill list
        for (int i = 0; i < 7; i++) {
            //Fill with arraylists
            pegs.add(new ArrayList<ImageButton>());
            for (int j = 0; j < 7; j++) {
                //fill with numbers
                int num = j;
                switch (i) {
                    case 1:
                        num += 7;
                        break;
                    case 2:
                        num += 14;
                        break;
                    case 3:
                        num += 21;
                        break;
                    case 4:
                        num += 28;
                        break;
                    case 5:
                        num += 35;
                        break;
                    case 6:
                        num += 42;
                        break;
                    case 7:
                        num += 49;
                        break;

                }
                pegs.get(i).add((ImageButton) gridLayout.getChildAt(num));


            }
        }
    }

    public void setUpOldPegs() {
        canUndo = false;
        //Fill list
        for (int i = 0; i < 7; i++) {
            //Fill with arraylists
            oldPegs.add(new ArrayList<Drawable.ConstantState>());
            for (int j = 0; j < 7; j++) {
                oldPegs.get(i).add(pegs.get(i).get(j).getBackground().getConstantState());

            }

        }


    }


    private void updateOldPegs() {
        canUndo = true;
        //Fill list
        for (int i = 0; i < 7; i++) {
            //Fill with arraylists
            for (int j = 0; j < 7; j++) {
                oldPegs.get(i).set(j, pegs.get(i).get(j).getBackground().getConstantState());

            }
        }
    }

    private void updateNewPegs() {
        canUndo = false;
        //Fill list
        for (int i = 0; i < 7; i++) {
            //Fill with arraylists
            for (int j = 0; j < 7; j++) {
                if (oldPegs.get(i).get(j).equals(getDrawable(R.drawable.pegunpressed).getConstantState())) {
                    pegs.get(i).get(j).setBackground(getDrawable(R.drawable.pegunpressed));

                } else if (oldPegs.get(i).get(j).equals(getDrawable(R.drawable.pegpressed).getConstantState())) {
                    pegs.get(i).get(j).setBackground(getDrawable(R.drawable.pegpressed));
                } else if (oldPegs.get(i).get(j).equals(getDrawable(R.drawable.pegempty).getConstantState())) {
                    pegs.get(i).get(j).setBackground(getDrawable(R.drawable.pegempty));
                } else {
                    pegs.get(i).get(j).setBackground(getDrawable(R.drawable.peginvisible));
                }


            }
        }
    }


    private void insertResults(){
        chronometer.stop();
        ScoreModel actualScore = new ScoreModel();
        actualScore.setUser(MenuActivity.username);
        actualScore.setHighScore(Integer.parseInt(pegsAmount.getText().toString()));
        actualScore.setTime(chronometer.getText().toString());
        Utils utils = new Utils();
        utils.insertResultsPeg(getApplicationContext(), actualScore);


    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Utils utils = new Utils();
            View decorView = getWindow().getDecorView();
            utils.hideSystemUI(decorView);
        }
    }




}