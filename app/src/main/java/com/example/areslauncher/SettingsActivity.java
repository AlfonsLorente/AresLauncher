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

/**
 * Lets the user modify its name/password, delete itself and log out.
 */
public class SettingsActivity extends AppCompatActivity {

    //VARIABLES
    private boolean activityPressed = false;
    private ImageButton backButton;
    private AppCompatButton logOutButton, changeUsernameButton, changePasswordButton, deleteUserButton;
    private TextView usernameTextView;
    private DBHelper dbHelper;

    //OVERRIDE METHODS
    /**
     * Initialize variables and set listeners
     * @param savedInstanceState Bundle
     */
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

    /**
     * Pause the app and the music
     */
    @Override
    protected void onPause() {
        if (!activityPressed) {

            MenuActivity.music.pause();
        }
        activityPressed = false;

        super.onPause();
    }

    /**
     * Resume the app and the music
     */
    @Override
    protected void onResume() {
        MenuActivity.music.resume();
        super.onResume();
    }

    //PRIVATE METHODS
    /**
     * Log out listener, return RESULT OK to MenuActivity and finish the Activity.
     */
    private void setUpLogOutButton() {
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.setResult(RESULT_OK);
                SettingsActivity.this.finish();
            }
        });


    }

    /**
     * Listener that deletes deletes all the user info, return RESULT OK to MenuActivity and finish the Activity.
     */
    private void setUpDeleteUserButtonListener() {
        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                //set dialog
                builder.setTitle("DELETE USER");
                builder.setMessage("Do you want to delete this user?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {//
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //delete user if yes is pressed
                        dbHelper.deleteUser(MenuActivity.username);
                        Toast.makeText(SettingsActivity.this, "USER DELETED", Toast.LENGTH_SHORT).show();
                        //return
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

    /**
     * Change user password listener
     */
    private void setUpChangePasswordButtonListener() {
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set dialog up
                EditText newPassword = new EditText(SettingsActivity.this);//edit text
                newPassword.setHint("NEW PASSWORD");
                newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);//dialog builder
                builder.setTitle("CHANGE PASSWORD");
                builder.setMessage("Insert the new password: ");
                builder.setView(newPassword);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //change the old password for the new one inserted
                        Utils utils = new Utils();
                        String pass = newPassword.getText().toString();
                        if(utils.passwordTest(SettingsActivity.this, pass)){//password comprobation
                            dbHelper.changePassword(MenuActivity.username, pass);//change password
                            Toast.makeText(SettingsActivity.this, "PASSWORD UPDATED", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                builder.setNegativeButton("Cancel", null);//on cancel do nothing
                //show dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    /**
     * change user name listener
     */
    private void setUpChangeUsernameButtonListener() {
        changeUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set dialog up
                EditText newUsername = new EditText(SettingsActivity.this);
                newUsername.setHint("NEW USERNAME");
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("CHANGE USERNAME");
                builder.setMessage("Insert the new username: ");
                builder.setView(newUsername);
                //on change pressed
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //change the old username for the new one
                        Utils utils = new Utils();
                        String user = newUsername.getText().toString();
                        if(utils.usernameTest(SettingsActivity.this, user)){//username comprobation
                            dbHelper.changeUsername(MenuActivity.username, user);//change username
                            MenuActivity.username = user;//change menu username
                            SettingsActivity.this.usernameTextView.setText(user);//change username display for this activity
                            Toast.makeText(SettingsActivity.this, "USERNAME UPDATED", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                //show dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    /**
     * back button listener, closes this activity
     */
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



}