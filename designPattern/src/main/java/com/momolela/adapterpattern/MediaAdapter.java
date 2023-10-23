package com.momolela.adapterpattern;

public class MediaAdapter implements MediaPlayer {

    private AdvancedMediaPlayer advancedMediaPlayer;

    public MediaAdapter(String mediaType) {
        if ("mp4".equals(mediaType)) {
            this.advancedMediaPlayer = new Mp4Player();
        } else if ("vlc".equals(mediaType)) {
            this.advancedMediaPlayer = new VlcPlayer();
        }
    }

    @Override
    public void play(String mediaType) {
        this.advancedMediaPlayer.AdvancedPlay();
    }
}
