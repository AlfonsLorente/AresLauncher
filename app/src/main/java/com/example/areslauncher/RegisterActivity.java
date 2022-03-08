package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * It registers a new user
 */
public class RegisterActivity extends AppCompatActivity {
    //VARIABLES
    private EditText userName, password, passwordRepeat;
    private Button login, register;
    private DBHelper dbHelper;
    private MusicPlayer effects;

    //OVERRIDE METHODS

    /**
     * Initialize variables and set listeners
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Initialize variables
        userName = findViewById(R.id.username_register);
        password = findViewById(R.id.password_register);
        passwordRepeat = findViewById(R.id.password_repeat_register);
        login = findViewById(R.id.button_login_register);
        register = findViewById(R.id.button_register);
        dbHelper = new DBHelper(this);
        effects = new MusicPlayer(this, 1);
        effects.addEffect(R.raw.menu_pick);

        //Button listener that closes this activity and starts the LogInActivity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effects.playEffect(R.raw.menu_pick);
                dbHelper.close();
                startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
                RegisterActivity.this.finish();

            }
        });

        //Button listener that will get the input of the user, verify it, and create a new user with it.
        //It also starts the menuActivity
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effects.playEffect(R.raw.menu_pick);
                Utils utils = new Utils();
                //Get user input
                String user = userName.getText().toString();
                String pass = password.getText().toString();
                String passRepeat = passwordRepeat.getText().toString();
                //Try if its legit
                if(pass.equals(passRepeat)){
                    if (utils.usernameTest(RegisterActivity.this, user) && utils.passwordTest(RegisterActivity.this, pass)) {
                        if (!dbHelper.isUser(user, pass)) {
                            //Insert the user in the database
                            dbHelper.insertUser(user, pass);
                            Toast.makeText(RegisterActivity.this, "USER CREATED", Toast.LENGTH_SHORT).show();
                            dbHelper.close();
                            //start the menu activity
                            startActivity(new Intent(RegisterActivity.this, MenuActivity.class).putExtra(MenuActivity.USERNAME_TAG, user));
                            RegisterActivity.this.finish();

                        } else {
                            Toast.makeText(RegisterActivity.this, "USER ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(RegisterActivity.this, "PASSWORDS DON'T MATCH", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}