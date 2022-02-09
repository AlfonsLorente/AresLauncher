package com.example.areslauncher;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

public class MusicPlayer {
    enum Style {
        EPIC,
        BEAT,
        ACUSTIC
    }

    private Style style;
    private MediaPlayer music;
    private final Context context;
    private int position = 0;

    public MusicPlayer(Context context) {
        this.context = context;
    }

    public void playMusic(Style style) {
        this.style = style;
        switch (style) {
            case EPIC:
                playEpicMusic();
                break;
            case BEAT:
                playBeatMusic();
                break;
            case ACUSTIC:
                playAcusticMusic();
                break;
        }

    }

    private void playAcusticMusic() {
    }

    private void playBeatMusic() {
        int rand = (int) Math.round(Math.random() * 2);

        switch (rand) {
            case 1:
                music = MediaPlayer.create(context, R.raw.game2048_moonshine);
                break;
            case 2:
                music = MediaPlayer.create(context, R.raw.game2048_poem);
                break;
            default:
                music = MediaPlayer.create(context, R.raw.game2048_soul);
                break;
        }
        start();

    }

    private void playEpicMusic() {

        int rand = (int) Math.round(Math.random() * 2);

        switch (rand) {
            case 1:
                music = MediaPlayer.create(context, R.raw.menu_adventureiscalling);
                break;
            case 2:
                music = MediaPlayer.create(context, R.raw.menu_falling);
                break;
            default:
                music = MediaPlayer.create(context, R.raw.menu_herostime);
                break;
        }
        start();

    }

    public void start() {

        music.start();

    }

    public void stop() {
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


    //TODO: PAUSE
    public void pause(){
        music.setVolume(0,0);
        position = music.getCurrentPosition();

    }


}
