package com.momolela.adapterpattern;

public class MediaAdapter implements MediaPlayer {

    private AdvancedMediaPlayer advancedMediaPlayer;

    public MediaAdapter(AdvancedMediaPlayer advancedMediaPlayer) {
        this.advancedMediaPlayer = advancedMediaPlayer;
    }

    @Override
    public void newPlay(String mediaType) {
        this.advancedMediaPlayer.AdvancedPlay();
    }
}
