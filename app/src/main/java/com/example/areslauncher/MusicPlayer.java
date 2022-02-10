package com.example.areslauncher;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MusicPlayer {


    private MediaPlayer music;
    private boolean playing = true;
    int[] songs = new int[9];

    private Context context;
    private int oldRand = -1;


    public MusicPlayer(Context context) {
        fillSongs();
        this.context = context;

        setUpRandomSong();

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


    public void reset() {
        music.reset();
    }

    public void setVolume(float volume) {
        music.setVolume(volume, volume);
    }

    public boolean isPlaying() {
        return music.isPlaying();
    }



    private void setUpRandomSong() {
        int rand = (int) Math.round(Math.random() * 8);
        if (rand != oldRand) {
            Log.d("Random", "" + rand);
            if (music != null) {
                music.release();
            }
            music = MediaPlayer.create(context, songs[rand]);
            music.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
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
