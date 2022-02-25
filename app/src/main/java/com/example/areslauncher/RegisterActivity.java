package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText userName, password, passwordRepeat;
    private Button login, register;
    private DBHelper dbHelper;
    private MusicPlayer effects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.username_register);
        password = findViewById(R.id.password_register);
        passwordRepeat = findViewById(R.id.password_repeat_register);
        login = findViewById(R.id.button_login_register);
        register = findViewById(R.id.button_register);
        dbHelper = new DBHelper(this);
        effects = new MusicPlayer(this, 1);
        effects.addEffect(R.raw.menu_pick);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effects.playEffect(R.raw.menu_pick);
                dbHelper.close();
                startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
                RegisterActivity.this.finish();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effects.playEffect(R.raw.menu_pick);

                Utils utils = new Utils();
                String user = userName.getText().toString();
                String pass = password.getText().toString();
                String passRepeat = passwordRepeat.getText().toString();
                if(pass.equals(passRepeat)){
                    if (utils.usernameTest(RegisterActivity.this, user) && utils.passwordTest(RegisterActivity.this, pass)) {
                        if (!dbHelper.isUser(user, pass)) {
                            dbHelper.insertUser(user, pass);
                            Toast.makeText(RegisterActivity.this, "USER CREATED", Toast.LENGTH_SHORT).show();
                            dbHelper.close();
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