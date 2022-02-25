package com.example.areslauncher;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MusicPlayer {


    private MediaPlayer music;
    private boolean playing = true;
    int[] songs = new int[9];
    HashMap<Integer, MediaPlayer> effects = new HashMap<>();
    private Context context;
    private int oldRand = -1;
    float volumeEffects = 1;
    float volumeMusic = 1;


    public MusicPlayer(Context context, int i) {
        if(i == 0) {
            fillSongs();
            this.context = context;

            setUpRandomSong();
        }else{
            this.context = context;
        }

    }

    public float getVolumeEffects() {
        return volumeEffects;
    }



    public float getVolumeMusic() {
        return volumeMusic;
    }


    public void addEffect(int effect){
        effects.put(effect, MediaPlayer.create(context, effect));
    }

    public void playEffect(int effect){
        effects.get(effect).setVolume(volumeEffects, volumeEffects);
        effects.get(effect).start();


    }

    public void removeEffect(int effect){
        effects.remove(effect);
    }


    private void fillSongs() {
        songs[0] = R.raw.adventureiscalling;
        songs[1] = R.raw.falling;
        songs[2] = R.raw.moonshine;
        songs[3] = R.raw.poem;
        songs[4] = R.raw.soul;
        songs[5] = R.raw.gently;
        songs[6] = R.raw.herostime;
        songs[7] = R.raw.hometown;
        songs[8] = R.raw.homeward;


    }


    public void resume(){
        music.start();
    }

    public void start() {
        setUpRandomSong();


    }

    public void pause() {
        playing = false;
        music.pause();

    }

    public void play(){
        music.start();
    }


    public void reset() {
        music.reset();
    }

    public void setMusicVolume(int volume) {
        float volFloat = (float) volume / 100;
        volumeMusic = volFloat;
        music.setVolume(volFloat, volFloat);
    }

    public void setEffectsVolume(int volume) {
        float volFloat = (float) volume / 100;
        volumeEffects = volFloat;
        for (MediaPlayer effect: effects.values()){
            effect.setVolume(volFloat, volFloat);
        }
    }

    public boolean isPlaying() {
        return music.isPlaying();
    }



    private void setUpRandomSong() {
        int rand = (int) Math.round(Math.random() * 8);
        if (rand != oldRand) {
            if (music != null) {
                music.release();
            }
            music = MediaPlayer.create(context, songs[rand]);
            music.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    music.setVolume(volumeMusic, volumeMusic);
                    music.start();
                }
            });
            music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    setUpRandomSong();
                }
            });
            oldRand = rand;
        } else {
            setUpRandomSong();
        }


    }



}
