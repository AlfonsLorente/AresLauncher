package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Shows the initial splash of the app
 */
public class SplashActivity extends AppCompatActivity {
    //VARIABLES
    private TextView topLetters, bottomLetters;
    private Animation lettersAnimation, logoAnimation, waitAnimation;
    private ImageView logo;

    //OVERRIDE METHODS
    /**
     * Initialize variables and set listeners
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);//Initialize variables
        topLetters = findViewById(R.id.splashTopTitle);
        bottomLetters = findViewById(R.id.splashBottomTitle);
        logo = findViewById(R.id.splashImage);
        lettersAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);//fade in of letters
        logoAnimation = AnimationUtils.loadAnimation(this, R.anim.custom_anim_logo);//logo main animation
        waitAnimation = AnimationUtils.loadAnimation(this, R.anim.wait);//do nothing animation for logo
        topLetters.startAnimation(lettersAnimation);
        bottomLetters.startAnimation(lettersAnimation);
        logo.startAnimation(logoAnimation);
        //listener that detect when the custom animation of the logo ends
        logoAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.startAnimation(waitAnimation);//makes the logo stay on waiting

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //listener that detect when the wait animation of the logo start and ends
        waitAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                logo.setBackground(getDrawable(R.drawable.logo_eye));//Changes logo image

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //ends activity and starts the LogInActivity
                startActivity(new Intent(SplashActivity.this, LogInActivity.class));
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    /**
     * when the app mis paused the animations are cleared
     */
    @Override
    protected void onPause() {
        super.onPause();
        topLetters.clearAnimation();
        bottomLetters.clearAnimation();
        logo.clearAnimation();


    }


    /**
     * Sets the splashActivity maximized
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