package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    //VARIABLES
    private static final String TAG = MenuActivity.class.getSimpleName();
    private RelativeLayout relativeLayout;
    private ListView menuList;
    public static MusicPlayer effects;
    public static MusicPlayer music;
    private boolean activityPressed = false;
    public static final String USERNAME_TAG = "com.example.areslauncher.USER";
    public static String username = "";
    private int LAUNCH_SETTINGS_ACTIVITY = 1;



    //OVERRIDE METHODS

    /**
     * Initialize variables and prepare activity
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //get username from the intent
        if (getIntent().getExtras() != null){
            username = getIntent().getExtras().getString(USERNAME_TAG, "_NO_USER_FOUND_ERR");

        }
        //initialize variables
        relativeLayout = findViewById(R.id.menu_relative_layout);
        menuList = findViewById(R.id.ListView_Menu);
        menuList.setSoundEffectsEnabled(false);

        //music and effects set up
        music = new MusicPlayer(this, 0);
        effects = new MusicPlayer(this, 1);
        effects.addEffect(R.raw.menu_pick);

        //List view set up
        setItemsToLV();

        //Set the list view menu listener
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();
                if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_play))) {
                    // Launch the Game Activity
                    activityPressed = true;
                    effects.playEffect(R.raw.menu_pick);
                    startActivity(new Intent(MenuActivity.this, GameChooserActivity.class));

                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_scores))) {
                    // Launch the Help Activity
                    activityPressed = true;
                    effects.playEffect(R.raw.menu_pick);
                    startActivity(new Intent(MenuActivity.this, ScoresActivity.class));

                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_music))) {
                    // Launch the Settings Activity
                    activityPressed = true;
                    effects.playEffect(R.raw.menu_pick);
                    startActivity(new Intent(MenuActivity.this, MusicActivity.class));
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_settings))) {
                    // Launch the Scores Activity
                    activityPressed = true;
                    effects.playEffect(R.raw.menu_pick);
                    Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                    startActivityForResult(intent, LAUNCH_SETTINGS_ACTIVITY);//this one may return a result_ok

                }
            }

        });





    }

    /**
     * Will take a result code that if its ok will end this activity and go to loginActivity
     * @param requestCode int
     * @param resultCode int
     * @param data Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if rquest code is 1 and result ok start logInActivity and end this one
        if (requestCode == LAUNCH_SETTINGS_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                startActivity(new Intent(MenuActivity.this, LogInActivity.class) );
                this.finish();
            }
        }
    }


    /**
     * Will destroy the activity
     */
    @Override
    protected void onDestroy() {
        music.reset();
        super.onDestroy();
    }

    /**
     * Will pause the activity and pause the music only on exiting
     */
    @Override
    protected void onPause() {
        if (!activityPressed){
            music.pause();

        }
        activityPressed = false;
        super.onPause();
    }

    /**
     * Will resume the app and the music
     */
    @Override
    protected void onResume() {
            music.resume();


        super.onResume();
    }


    /**
     * Sets all the list view items
     */
    private void setItemsToLV() {
        //All the list view items
        String[] items = {getResources().getString(R.string.menu_item_play),
                getResources().getString(R.string.menu_item_scores),
                getResources().getString(R.string.menu_item_music),
                getResources().getString(R.string.menu_item_settings)};

        //Array adapter set up
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.menu_item, items);
        menuList.setAdapter(adapter);//set the adapter to the list view

    }


}