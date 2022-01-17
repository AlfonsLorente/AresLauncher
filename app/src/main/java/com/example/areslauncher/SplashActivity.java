package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private TextView topLetters, bottomLetters;
    private Animation lettersAnimation, logoAnimation;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        topLetters = (TextView) findViewById(R.id.splashTopTitle);
        bottomLetters = (TextView)findViewById(R.id.splashBottomTitle);
        logo = findViewById(R.id.splashImage);
        lettersAnimation = (Animation) AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoAnimation = (Animation) AnimationUtils.loadAnimation(this, R.anim.custom_anim_logo);
        topLetters.startAnimation(lettersAnimation);
        bottomLetters.startAnimation(lettersAnimation);
        logo.startAnimation(logoAnimation);


        logoAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                SystemClock.sleep(500);
                startActivity(new Intent(SplashActivity.this, MenuActivity.class));
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
        logo.setBackground(getDrawable(R.drawable.logo_eye));
        logo.clearAnimation();


    }
}