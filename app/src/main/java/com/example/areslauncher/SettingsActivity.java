package com.example.areslauncher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;


public class SettingsActivity extends AppCompatActivity {

    private boolean activityPressed = false;
    private ImageButton backButton;
    private AppCompatButton logOutButton, changeUsernameButton, changePasswordButton, deleteUserButton;
    private TextView usernameTextView;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        dbHelper = new DBHelper(this);
        usernameTextView = findViewById(R.id.settings_username);
        backButton = findViewById(R.id.back_button_settings);
        logOutButton = findViewById(R.id.button_log_out);
        changeUsernameButton = findViewById(R.id.button_change_username);
        changePasswordButton = findViewById(R.id.button_change_password);
        deleteUserButton = findViewById(R.id.button_delete_user);

        usernameTextView.setText(MenuActivity.username);

        setUpBackButtonListener();
        setUpLogOutButton();
        setUpChangeUsernameButtonListener();
        setUpChangePasswordButtonListener();
        setUpDeleteUserButtonListener();



    }

    private void setUpLogOutButton() {
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.setResult(RESULT_OK);
                SettingsActivity.this.finish();
            }
        });


    }

    private void setUpDeleteUserButtonListener() {
        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("DELETE USER");
                builder.setMessage("Do you want to delete this user?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHelper.deleteUser(MenuActivity.username);
                        Toast.makeText(SettingsActivity.this, "USER DELETED", Toast.LENGTH_SHORT).show();
                        SettingsActivity.this.setResult(RESULT_OK);
                        SettingsActivity.this.finish();
                    }
                });
                builder.setNegativeButton("No", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void setUpChangePasswordButtonListener() {
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText newPassword = new EditText(SettingsActivity.this);
                newPassword.setHint("NEW PASSWORD");
                newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("CHANGE PASSWORD");
                builder.setMessage("Insert the new password: ");
                builder.setView(newPassword);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //NEW PASSWORD
                        Utils utils = new Utils();
                        String pass = newPassword.getText().toString();
                        if(utils.passwordTest(SettingsActivity.this, pass)){
                            dbHelper.changePassword(MenuActivity.username, pass);
                            Toast.makeText(SettingsActivity.this, "PASSWORD UPDATED", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    private void setUpChangeUsernameButtonListener() {
        changeUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText newUsername = new EditText(SettingsActivity.this);
                newUsername.setHint("NEW USERNAME");
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("CHANGE USERNAME");
                builder.setMessage("Insert the new username: ");
                builder.setView(newUsername);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //NEW USERNAME
                        Utils utils = new Utils();
                        String user = newUsername.getText().toString();
                        if(utils.usernameTest(SettingsActivity.this, user)){
                            dbHelper.changeUsername(MenuActivity.username, user);
                            MenuActivity.username = user;
                            SettingsActivity.this.usernameTextView.setText(user);
                            Toast.makeText(SettingsActivity.this, "USERNAME UPDATED", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    private void setUpBackButtonListener() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPressed = true;
                MenuActivity.effects.playEffect(R.raw.menu_pick);

                SettingsActivity.this.finish();
            }
        });
    }


    @Override
    protected void onPause() {
        if (!activityPressed) {

            MenuActivity.music.pause();
        }
        activityPressed = false;

        super.onPause();
    }

    @Override
    protected void onResume() {
        MenuActivity.music.resume();
        super.onResume();
    }

}