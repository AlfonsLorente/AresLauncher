package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LogInActivity extends AppCompatActivity {
    private String username;
    private RelativeLayout relativeLayout;
    private EditText usernameText;
    private Button enterButton;

    public static boolean esTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        relativeLayout = findViewById(R.id.login_rl);
        setBackGround();
        usernameText = findViewById(R.id.username_login);
        enterButton = findViewById(R.id.login_button);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //savePreferences();
                startActivity(new Intent(LogInActivity.this, MenuActivity.class));
                LogInActivity.this.finish();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }
/*
    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(MenuActivity.PREFS_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        username = usernameText.getText().toString();

        editor.putString(MenuActivity.PREF_UNAME, username);
        editor.commit();

    }*/


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