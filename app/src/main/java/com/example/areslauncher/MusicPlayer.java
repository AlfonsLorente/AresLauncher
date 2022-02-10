package com.example.areslauncher;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

public class MusicPlayer implements Runnable {


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


    public void start() {
        playing = true;

    }

    public void stop() {
        playing = false;
        music.stop();
    }


    public void reset() {
        music.reset();
    }

    public void setVolume(float volume) {
        music.setVolume(volume, volume);
    }

    public boolean isPlaying(){
        return music.isPlaying();
    }




    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (playing) {


                if (!this.isPlaying()) {
                    music.reset();
                    setUpRandomSong();
                }


            }
        }

    }

    private void setUpRandomSong() {
        int rand = (int) Math.round(Math.random()*8);
        if(rand != oldRand){
            music = MediaPlayer.create(context, songs[rand]);
            music.start();
            oldRand = rand;
        }else{
            setUpRandomSong();
        }



    }
}
