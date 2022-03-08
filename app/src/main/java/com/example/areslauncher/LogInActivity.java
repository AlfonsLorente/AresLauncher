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
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {
    //VARIABLES
    private EditText userName, password;
    private Button enterButton, registerButton;
    private DBHelper dbHelper;
    private MusicPlayer effects;

    //OVERRIDE METHODS

    /**
     * Initialize variables and listeners
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //variables
        userName = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        enterButton = findViewById(R.id.button_login);
        registerButton = findViewById(R.id.button_register_login);
        dbHelper = new DBHelper(this);

        //effects
        effects = new MusicPlayer(this, 1);
        effects.addEffect(R.raw.menu_pick);

        //log in button listener - it tries to login with the user inserted
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effects.playEffect(R.raw.menu_pick);
                //user inputs
                String user = userName.getText().toString();
                String pass = password.getText().toString();
                //see if the user with the password exists
                if (dbHelper.isUser(user, pass)) {
                    startActivity(new Intent(LogInActivity.this, MenuActivity.class).putExtra(MenuActivity.USERNAME_TAG, user));
                    dbHelper.close();
                    LogInActivity.this.finish();
                }else{
                    Toast.makeText(LogInActivity.this, "INCORRECT USER OR PASSWORD", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //opens the register activity
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effects.playEffect(R.raw.menu_pick);

                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
                dbHelper.close();
                LogInActivity.this.finish();
            }
        });

    }



}