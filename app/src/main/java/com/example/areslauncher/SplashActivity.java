package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private TextView topLetters, bottomLetters;
    private Animation lettersAnimation, logoAnimation;

    //TODO: REMINDER - RELATIVELAYOUT CHANGES
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        topLetters = (TextView) findViewById(R.id.splashTopTitle);
        //bottomLetters = (TextView)findViewById(R.id.splashBottomTitle);
        lettersAnimation = (Animation) AnimationUtils.loadAnimation(this, R.anim.fade_in);

        topLetters.startAnimation(lettersAnimation);
        lettersAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("aa", "pene7");
                startActivity(new Intent(SplashActivity.this, MenuActivity.class));
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Log.d("aa", "pene6");

        //bottomLetters.startAnimation(lettersAnimation);
        //Log.d("aa", "pene7");

    }

    @Override
    protected void onPause() {
        super.onPause();
        topLetters.clearAnimation();
        Log.d("aa", "pene7");
        //bottomLetters.clearAnimation();

    }
}