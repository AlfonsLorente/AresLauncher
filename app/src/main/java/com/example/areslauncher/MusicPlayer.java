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

/**
 * Lets you use music and effects
 */
public class MusicPlayer {

    //VARIABLES
    private MediaPlayer music;
    private boolean playing = true;
    int[] songs = new int[9];
    HashMap<Integer, MediaPlayer> effects = new HashMap<>();
    private Context context;
    private int oldRand = -1;
    float volumeEffects = 1;
    float volumeMusic = 1;


    //CONSTRUCTORS
    /**
     * Sets if will be used by music or effects
     * @param context Context
     * @param i int (0 - music, other - effects)
     */
    public MusicPlayer(Context context, int i) {
        //Music play
        if(i == 0) {
            fillSongs();//gets songs
            this.context = context;
            setUpRandomSong();//starts random songs
        //sets only the context
        }else{
            this.context = context;
        }

    }

    //GETTERS SETTERS AND ADDERS
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


    /**
     * Start music
     */
    public void resume(){
        music.start();
    }


    /**
     * Pause music
     */
    public void pause() {
        playing = false;
        music.pause();

    }


    /**
     * Reset all the songs
     */
    public void reset() {
        music.reset();
    }

    /**
     * set the music volume
     * @param volume int
     */
    public void setMusicVolume(int volume) {
        float volFloat = (float) volume / 100;
        volumeMusic = volFloat;
        music.setVolume(volFloat, volFloat);
    }

    /**
     * set the effects volume
     * @param volume
     */
    public void setEffectsVolume(int volume) {
        float volFloat = (float) volume / 100;
        volumeEffects = volFloat;
        for (MediaPlayer effect: effects.values()){
            effect.setVolume(volFloat, volFloat);
        }
    }


    //PRIVATE METHODS
    /**
     * Plays the songs randomly
     */
    private void setUpRandomSong() {
        //do not repeat a song
        int rand = (int) Math.round(Math.random() * 8);
        if (rand != oldRand) {
            if (music != null) {
                //release the music
                music.release();
            }
            //Get a new song
            music = MediaPlayer.create(context, songs[rand]);
            //When the music is ready to play, set its volume and start
            music.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    music.setVolume(volumeMusic, volumeMusic);
                    music.start();
                }
            });
            //When the music ends, set up a new song and start it
            music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    setUpRandomSong();
                }
            });
            //save old song
            oldRand = rand;
        } else {
            setUpRandomSong();//Try again
        }


    }

    /**
     * Store all the songs in the songs array
     */
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



}
