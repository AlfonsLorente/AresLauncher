package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GameChooserActivity extends AppCompatActivity {
    private ImageButton button2048, buttonPeg, backButton;
    private RelativeLayout relativeLayout;
    public static boolean esTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_chooser);
        button2048 = findViewById(R.id.button2048);
        buttonPeg = findViewById(R.id.button_peg);
        backButton = findViewById(R.id.back_button);
        relativeLayout = findViewById(R.id.chooser_relative_layout);
        setBackGround();
        button2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameChooserActivity.this, Launcher2048Activity.class));
                GameChooserActivity.this.finish();

            }
        });

        buttonPeg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("aa", "pene");
                startActivity(new Intent(GameChooserActivity.this, LauncherPegActivity.class));
                Log.d("bb", "nepe");

                GameChooserActivity.this.finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameChooserActivity.this, MenuActivity.class));
                GameChooserActivity.this.finish();
            }
        });

    }

    private void setBackGround() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        if (esTablet(this)) {
            relativeLayout.setBackground(getDrawable(R.drawable.menubgtablet));


        } else if (height > 2100 && width == 1080) {
            relativeLayout.setBackground(getDrawable(R.drawable.menubackground));
        } else if (height > 2850 && width == 1440) {
            relativeLayout.setBackground(getDrawable(R.drawable.menubackground));

        } else {
            relativeLayout.setBackground(getDrawable(R.drawable.menubackground1080));
        }

    }

}