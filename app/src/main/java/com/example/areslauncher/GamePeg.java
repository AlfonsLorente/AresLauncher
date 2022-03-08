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

/**
 * Peg game
 */
public class GamePeg extends AppCompatActivity {
    //VARIABLES
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
    private ImageButton musicButton;

    //OVERRIDE METHODS

    /**
     * Initialize variables, set listeners and set up game
     * @param savedInstanceState Bundle
     */
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
        musicButton = findViewById(R.id.settings_button_peg);

        chronometer = findViewById(R.id.chrono_peg);
        possibleMoves = findViewById(R.id.possibleMoves);
        victorySplash = findViewById(R.id.pegVictory);
        gameOverSplash = findViewById(R.id.pegGameOver);
        //animation
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeIn.setDuration(2500);

        //chrono
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        //sound effects
        MenuActivity.effects.addEffect(R.raw.peg_pick);
        MenuActivity.effects.addEffect(R.raw.peg_drop);

        //set all the listeners
        setListeners();
        //Start the game
        setUpNewPegs();
        setUpOldPegs();
        resumeGame();


    }

    /**
     * Pauses the game and controls if the music has to pause
     */
    @Override
    protected void onPause() {
        if (!activityPressed) {
            MenuActivity.music.pause();
        }
        activityPressed = false;

        super.onPause();
    }

    /**
     * Resumes the app and the music
     */
    @Override
    protected void onResume() {
        MenuActivity.music.resume();


        super.onResume();
    }

    //PRIVATE METHODS

    /**
     * Sets up all the listeners
     */
    private void setListeners() {

        //animation listener - on game over or victory will be triggered and end the activity
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


        //closes this activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPressed = true;
                MenuActivity.effects.playEffect(R.raw.menu_pick);
                GamePeg.this.finish();
            }
        });

        //restarts the activity
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertResults();
                activityPressed = true;
                MenuActivity.effects.playEffect(R.raw.menu_pick);
                GamePeg.this.finish();

                startActivity(new Intent(GamePeg.this, GamePeg.class));
            }
        });

        //undo a movement
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

        //Opens de music menu
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityPressed = true;
                MenuActivity.effects.playEffect(R.raw.menu_pick);
                startActivity(new Intent(GamePeg.this, MusicActivity.class));
            }
        });

    }


    /**
     * updates the score, and looks for victory/game over
     */
    private void resumeGame() {
        updatePunctuation();
        //game will end in victory if the score is 1
        if (pegsAmount.getText().toString().equals("1") ){
            insertResults();
            victorySplash.startAnimation(fadeIn);
            victorySplash.setVisibility(View.VISIBLE);
        } else {
            scanGame();
        }


    }

    /**
     * looks for the game over
     */
    private void scanGame() {
        int moves = 0;
        //loops all the buttons
        for (int i = 0; i < pegs.size(); i++) {
            for (int j = 0; j < pegs.get(i).size(); j++) {

                //tries all the possibilities
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
        //Sets the actual possible moves
        possibleMoves.setText("" + moves);
        //if there is no moves game over
        if (moves == 0) {

            insertResults();
            gameOverSplash.startAnimation(fadeIn);
            gameOverSplash.setVisibility(View.VISIBLE);
        }
    }

    /**
     * updates the punctuation - the lowest the score is the best one
     */
    private void updatePunctuation() {
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

    /**
     * onClick called by the xml, it detects the type of background the button pressed has
     * @param view View
     */
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


    /**
     * calculates if the movement is possible
     * @param view View
     */
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

    /**
     * Finds if there is some button pressed (with the pegpressed background) and save its value
     * @return boolean
     */
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


    /**
     * instantiate all the grid layout child buttons in the array
     */
    private void setUpNewPegs() {
        //Fill list
        for (int i = 0; i < 7; i++) {
            //Fill with arraylists
            pegs.add(new ArrayList<ImageButton>());
            for (int j = 0; j < 7; j++) {
                //fill with numbers
                int num = (i*7)+j;
                pegs.get(i).add((ImageButton) gridLayout.getChildAt(num));


            }
        }
    }

    /**
     * instantiate all the old buttons of constantStates of the new buttons
     */
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


    /**
     * Update the old buttons setting them the new one background state
     */
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

    /**
     * set all the new pegs to its old state with the old buttons array
     */
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


    /**
     * insert the score into the database
     */
    private void insertResults(){
        //stop chronometer
        chronometer.stop();
        //set up score model
        ScoreModel actualScore = new ScoreModel();
        actualScore.setUser(MenuActivity.username);
        actualScore.setHighScore(Integer.parseInt(pegsAmount.getText().toString()));
        actualScore.setTime(chronometer.getText().toString());
        //save score
        Utils utils = new Utils();
        utils.insertResultsPeg(getApplicationContext(), actualScore);


    }


    /**
     * Full window mode
     * @param hasFocus boolean
     */
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