package com.example.areslauncher;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    public static final String PREFS_KEY = "preferences";
    public static final String PREF_UNAME = "username";
    public static final String EXTRAS = "extras";
    public static final String MUSIC = "music";

    private String username;
    private RelativeLayout relativeLayout;
    private ListView menuList;
    private MediaPlayer swipe, sum;
    private int time;
    public static MusicPlayer music;
    public static String actualSong;



    public static boolean esTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        relativeLayout = findViewById(R.id.menu_relative_layout);
        menuList = findViewById(R.id.ListView_Menu);
        setItemsToLV();
        music = new MusicPlayer(this);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View itemClicked,
                                    int position, long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();

                if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_play))) {
                    // Launch the Game Activity
                    startActivity(new Intent(MenuActivity.this, GameChooserActivity.class).putExtra(MUSIC, time));

                   // MenuActivity.this.finish();
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_help))) {
                    // Launch the Help Activity
                    startActivity(new Intent(MenuActivity.this, HelpActivity.class));
                  //  MenuActivity.this.finish();
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_settings))) {
                    // Launch the Settings Activity
                    startActivity(new Intent(MenuActivity.this, SettingsActivity.class));
                 //   MenuActivity.this.finish();
                } else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_scores))) {
                    // Launch the Scores Activity
                    startActivity(new Intent(MenuActivity.this, ScoresActivity.class));
                  //  MenuActivity.this.finish();
                }
            }
        });

        setBackGround();
        music.playMusic(MusicPlayer.Style.EPIC);

    }


    @Override
    protected void onDestroy() {
        music.stop();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        music.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        music.playMusic(MusicPlayer.Style.EPIC);

        super.onResume();
    }







    private void setItemsToLV() {
        String[] items = {getResources().getString(R.string.menu_item_play),
                getResources().getString(R.string.menu_item_scores),
                getResources().getString(R.string.menu_item_settings),
                getResources().getString(R.string.menu_item_help)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.menu_item, items);
        menuList.setAdapter(adapter);

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