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

public class SplashActivity extends AppCompatActivity {
    private TextView topLetters, bottomLetters;
    private Animation lettersAnimation, logoAnimation, waitAnimation;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        topLetters = findViewById(R.id.splashTopTitle);
        bottomLetters = findViewById(R.id.splashBottomTitle);
        logo = findViewById(R.id.splashImage);
        lettersAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoAnimation = AnimationUtils.loadAnimation(this, R.anim.custom_anim_logo);
        waitAnimation = AnimationUtils.loadAnimation(this, R.anim.wait);
        topLetters.startAnimation(lettersAnimation);
        bottomLetters.startAnimation(lettersAnimation);
        logo.startAnimation(logoAnimation);
        logoAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.startAnimation(waitAnimation);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        waitAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                logo.setBackground(getDrawable(R.drawable.logo_eye));

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, LogInActivity.class));
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        topLetters.clearAnimation();
        bottomLetters.clearAnimation();
        logo.clearAnimation();


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