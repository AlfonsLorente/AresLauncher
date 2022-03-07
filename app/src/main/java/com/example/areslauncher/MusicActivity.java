package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

/**
 * Activity that lets the user control the music volume
 */
public class MusicActivity extends AppCompatActivity {

    //VARIABLES
    private AppCompatSeekBar seekBarMaster;
    private AppCompatSeekBar seekBarMusic;
    private AppCompatSeekBar seekBarEffects;
    private ImageButton backButton;
    private boolean activityPressed;
    private int musicVolume;
    private int effectVolume;

    //OVERRIDES METHODS
    /**
     * Initialize variables, and set listeners
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        //Instantiate variables
        seekBarMaster = findViewById(R.id.MasterVolume);
        seekBarMusic = findViewById(R.id.MusicVolume);
        seekBarEffects = findViewById(R.id.EffetsVolume);
        backButton = findViewById(R.id.back_button_settings);
        musicVolume = (int) Math.round(MenuActivity.music.getVolumeMusic() * 100);
        effectVolume = (int) Math.round(MenuActivity.effects.getVolumeEffects() * 100);
        //If the music volume and the effect volume is the same the master will be active
        if (musicVolume == effectVolume) {
            seekBarMaster.setProgress(musicVolume);
            seekBarMaster.getThumb().setTint(getResources().getColor(R.color.activeThumb, null));
            seekBarEffects.getThumb().setTint(getResources().getColor(R.color.inactiveThumb, null));
            seekBarMusic.getThumb().setTint(getResources().getColor(R.color.inactiveThumb, null));

        //else set active the music and effect bars
        } else {
            seekBarMusic.setProgress(musicVolume);
            seekBarEffects.setProgress(effectVolume);

            seekBarMaster.getThumb().setTint(getResources().getColor(R.color.inactiveThumb, null));
            seekBarEffects.getThumb().setTint(getResources().getColor(R.color.activeThumb, null));
            seekBarMusic.getThumb().setTint(getResources().getColor(R.color.activeThumb, null));
        }

        //listener for the master seek bar that moves left and right and changes the music and effect volume at the same time.
        seekBarMaster.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MenuActivity.music.setMusicVolume(progress);
                MenuActivity.effects.setEffectsVolume(progress);
                musicVolume = progress;
                effectVolume = progress;
                seekBarMaster.getThumb().setTint(getResources().getColor(R.color.activeThumb, null));
                seekBarEffects.getThumb().setTint(getResources().getColor(R.color.inactiveThumb, null));
                seekBarMusic.getThumb().setTint(getResources().getColor(R.color.inactiveThumb, null));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //listener for the music seek bar that moves left and right and changes only the music volume.
        seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MenuActivity.music.setMusicVolume(progress);
                MenuActivity.effects.setEffectsVolume(effectVolume);
                musicVolume = progress;
                seekBarMaster.getThumb().setTint(getResources().getColor(R.color.inactiveThumb, null));
                seekBarEffects.getThumb().setTint(getResources().getColor(R.color.activeThumb, null));
                seekBarMusic.getThumb().setTint(getResources().getColor(R.color.activeThumb, null));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //listener for the effects seek bar that moves left and right and changes only the effects volume.
        seekBarEffects.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MenuActivity.music.setMusicVolume(musicVolume);
                MenuActivity.effects.setEffectsVolume(progress);
                effectVolume = progress;
                seekBarMaster.getThumb().setTint(getResources().getColor(R.color.inactiveThumb, null));
                seekBarEffects.getThumb().setTint(getResources().getColor(R.color.activeThumb, null));
                seekBarMusic.getThumb().setTint(getResources().getColor(R.color.activeThumb, null));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //listener for the back button that closes this activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityPressed = true;
                MenuActivity.effects.playEffect(R.raw.menu_pick);

                MusicActivity.this.finish();
            }
        });

    }

    /**
     * pauses the app and the music
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
     * Resumes the app and the music
     */
    @Override
    protected void onResume() {
        MenuActivity.music.resume();


        super.onResume();
    }


}