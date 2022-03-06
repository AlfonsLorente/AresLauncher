package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * Lets the user pick a game
 */
public class GameChooserActivity extends AppCompatActivity {
    //VARIABLES
    private ImageButton button2048, buttonPeg, backButton;
    private RelativeLayout relativeLayout;
    private boolean activityPressed = false;

    //OVERRIDE METHODS

    /**
     * Set up variables and listeners
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_chooser);

        //Variables
        button2048 = findViewById(R.id.button2048);
        buttonPeg = findViewById(R.id.button_peg);
        backButton = findViewById(R.id.back_button_gc);
        relativeLayout = findViewById(R.id.chooser_relative_layout);

        //start game 2048
        button2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPressed = true;//the music will not stop
                MenuActivity.effects.playEffect(R.raw.menu_pick);//button sounds effect

                startActivity(new Intent(GameChooserActivity.this, Game2048.class));
            }
        });

        //start game peg
        buttonPeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPressed = true;//the music will not stop
                MenuActivity.effects.playEffect(R.raw.menu_pick);//button sounds effect

                startActivity(new Intent(GameChooserActivity.this, GamePeg.class));

            }
        });

        //go back to menu
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPressed = true;//the music will not stop
                MenuActivity.effects.playEffect(R.raw.menu_pick);//button sounds effect

                GameChooserActivity.this.finish();
            }
        });

    }

    /**
     * Pause app and music
     */
    @Override
    protected void onPause() {
        //pause if is not starting an activity
        if (!activityPressed) {

            MenuActivity.music.pause();
        }
        activityPressed = false;

        super.onPause();
    }

    /**
     * resume app and music
     */
    @Override
    protected void onResume() {
        MenuActivity.music.resume();
        super.onResume();
    }
}
