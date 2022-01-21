package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class LauncherPegActivity extends AppCompatActivity {
    //Declare variables
    private ArrayList<ArrayList<ImageButton>> pegs = new ArrayList<ArrayList<ImageButton>>();
    private GridLayout gridLayout;
    private TextView pegsAmount, possibleMoves;
    private ImageView victorySplash, gameOverSplash, backButton;
    private int oldi, oldj;
    private Animation fadeIn;

    //ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher_peg);
        Log.d("cc", "pito");

        //initialize variables
        gridLayout = findViewById(R.id.grid_peg);
        pegsAmount = findViewById(R.id.pegs_amount);
        backButton = findViewById(R.id.back_button);
        possibleMoves = findViewById(R.id.possibleMoves);
        victorySplash = findViewById(R.id.pegVictory);
        gameOverSplash = findViewById(R.id.pegGameOver);
        fadeIn = (Animation) AnimationUtils.loadAnimation(this, R.anim.fade_in);


        //Start the game
        getAllPegs();
        resumeGame();

        fadeIn.setDuration(2500);
        fadeIn.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                startActivity(new Intent(LauncherPegActivity.this, MenuActivity.class));
                LauncherPegActivity.this.finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherPegActivity.this, GameChooserActivity.class));
                LauncherPegActivity.this.finish();
            }
        });

    }

    //GET ALL PEGS
    private void getAllPegs() {
        //Fill list
        for (int i = 0; i < 7; i++){
            //Fill with arraylists
            pegs.add(new ArrayList<ImageButton>());
            for(int j = 0; j < 7;j++) {
                //fill with numbers
                int num = j;
                switch (i){
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


    //TODO: RESUME GAME
    private void resumeGame() {
        updatePuntuation();
        if(pegsAmount.getText() == "1"){
            victorySplash.startAnimation(fadeIn);
            victorySplash.setVisibility(View.VISIBLE);
        }else{
            scanGame();
        }


    }

    private void scanGame() {
        int moves = 0;
        for (int i = 0; i < pegs.size(); i++) {
            for (int j = 0; j < pegs.get(i).size(); j++) {

                if (!pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.peginvisible).getConstantState())) {
                    Log.d("aa", "pene" + " - " + i + " - "+ j);

                    if ((pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState()) ||
                            pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState())) &&
                            (i<pegs.size()-2)){

                        if ((pegs.get(i + 1).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) ||
                                (pegs.get(i + 1).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState()))) {
                            if (pegs.get(i + 2).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegempty).getConstantState())) {
                                moves++;

                            }

                        }

                    }

                    if ((pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState()) ||
                            pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState())) &&
                            (i>1)){
                        if ((pegs.get(i - 1).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) ||
                                (pegs.get(i - 1).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState()))) {
                            if (pegs.get(i - 2).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegempty).getConstantState())) {
                                moves++;

                            }

                        }

                    }

                    if ((pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState()) ||
                            pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState())) &&
                            (j<pegs.get(i).size()-2)) {
                        if ((pegs.get(i).get(j + 1).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) ||
                                (pegs.get(i).get(j + 1).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState()))) {
                            if (pegs.get(i).get(j + 2).getBackground().getConstantState().equals(getDrawable(R.drawable.pegempty).getConstantState())) {
                                moves++;
                            }
                        }

                    }

                    if ((pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState()) ||
                            pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState())) &&
                            (j>1)){
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
        possibleMoves.setText(""+moves);
        if (moves == 0) {
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
                if((pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) ||
                        (pegs.get(i).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegpressed).getConstantState()))){
                    amount++;

                }
            }
        }
        //set the text view with the peg amount
        pegsAmount.setText(""+amount);
    }

    //BUTTON CLICKED
    public void buttonClicked(View view) {
        //Ignore if its an invisible peg
        if(!view.getBackground().getConstantState().equals(getDrawable(R.drawable.peginvisible).getConstantState())) {
            //Search if there is already a clicked button
            boolean isClicked = searchClickedButton();
            //If it is try to move it, if not set it pressed
            if (isClicked) {
                calculateMovement(view);
            } else {
                if(!view.getBackground().getConstantState().equals(getDrawable(R.drawable.pegempty).getConstantState())){
                    view.setBackground(getDrawable(R.drawable.pegpressed));
                }
            }
        }
        resumeGame();

    }



    //CALCULATE MOVEMENT
    private void calculateMovement(View view) {
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
                                pegs.get(i).get(j).setBackground(getDrawable(R.drawable.pegunpressed));
                                pegs.get(i + 1).get(j).setBackground(getDrawable(R.drawable.pegempty));
                                pegs.get(i + 2).get(j).setBackground(getDrawable(R.drawable.pegempty));

                            }

                        } else if (oldi + 2 == i && oldj == j) {
                            if (pegs.get(i - 1).get(j).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) {
                                //move the pegs
                                pegs.get(i).get(j).setBackground(getDrawable(R.drawable.pegunpressed));
                                pegs.get(i - 1).get(j).setBackground(getDrawable(R.drawable.pegempty));
                                pegs.get(i - 2).get(j).setBackground(getDrawable(R.drawable.pegempty));

                            }
                        } else if (oldi == i && oldj - 2 == j) {
                            if (pegs.get(i).get(j + 1).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) {
                                //move the pegs
                                pegs.get(i).get(j).setBackground(getDrawable(R.drawable.pegunpressed));
                                pegs.get(i).get(j + 1).setBackground(getDrawable(R.drawable.pegempty));
                                pegs.get(i).get(j + 2).setBackground(getDrawable(R.drawable.pegempty));

                            }
                        } else if (oldi == i && oldj + 2 == j) {
                            if (pegs.get(i).get(j - 1).getBackground().getConstantState().equals(getDrawable(R.drawable.pegunpressed).getConstantState())) {
                                //move the pegs
                                pegs.get(i).get(j).setBackground(getDrawable(R.drawable.pegunpressed));
                                pegs.get(i).get(j - 1).setBackground(getDrawable(R.drawable.pegempty));
                                pegs.get(i).get(j - 2).setBackground(getDrawable(R.drawable.pegempty));
                            }
                        }

                    } else {
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


}