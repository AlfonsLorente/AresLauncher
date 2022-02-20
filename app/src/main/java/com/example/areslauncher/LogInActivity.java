package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
        relativeLayout = findViewById(R.id.register_rl);
        setBackGround();
        usernameText = findViewById(R.id.username_register);
        enterButton = findViewById(R.id.button_login);

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