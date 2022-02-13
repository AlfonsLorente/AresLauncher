package com.example.areslauncher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class SettingsActivity extends AppCompatActivity {

    private AppCompatSeekBar seekBarMaster;
    private AppCompatSeekBar seekBarMusic;
    private AppCompatSeekBar seekBarEffects;
    private ImageButton backButton;
    private boolean activityPressed;
    int musicVolume;
    int effectVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        seekBarMaster = findViewById(R.id.MasterVolume);
        seekBarMusic = findViewById(R.id.MusicVolume);
        seekBarEffects = findViewById(R.id.EffetsVolume);
        backButton = findViewById(R.id.back_button_settings);

        musicVolume = (int) Math.round(MenuActivity.music.getVolumeMusic() * 100);
        effectVolume = (int) Math.round(MenuActivity.effects.getVolumeEffects() * 100);

        if (musicVolume == effectVolume) {
            seekBarMaster.setProgress(musicVolume);
            seekBarMaster.getThumb().setTint(getResources().getColor(R.color.activeThumb, null));
            seekBarEffects.getThumb().setTint(getResources().getColor(R.color.inactiveThumb, null));
            seekBarMusic.getThumb().setTint(getResources().getColor(R.color.inactiveThumb, null));

        } else {
            seekBarMusic.setProgress(musicVolume);
            seekBarEffects.setProgress(effectVolume);

            seekBarMaster.getThumb().setTint(getResources().getColor(R.color.inactiveThumb, null));
            seekBarEffects.getThumb().setTint(getResources().getColor(R.color.activeThumb, null));
            seekBarMusic.getThumb().setTint(getResources().getColor(R.color.activeThumb, null));
        }

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