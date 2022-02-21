package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText userName, password, passwordRepeat;
    private Button login, register;
    private DBHepler dbHepler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.username_register);
        password = findViewById(R.id.password_register);
        passwordRepeat = findViewById(R.id.password_repeat_register);
        login = findViewById(R.id.button_login_register);
        register = findViewById(R.id.button_register);
        dbHepler = new DBHepler(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
                RegisterActivity.this.finish();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userName.getText().toString();
                String pass = password.getText().toString();
                String passRepeat = passwordRepeat.getText().toString();
                if(pass.equals(passRepeat)){
                    if (!dbHepler.isUser(user, pass)){
                        dbHepler.insertUser(user, pass);
                        Toast.makeText(RegisterActivity.this, "USER CREATED", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MenuActivity.class).putExtra(MenuActivity.USERNAME_TAG, user));
                        RegisterActivity.this.finish();

                    }else {
                        Toast.makeText(RegisterActivity.this, "USER ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this, "PASSWORDS DON'T MATCH", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}