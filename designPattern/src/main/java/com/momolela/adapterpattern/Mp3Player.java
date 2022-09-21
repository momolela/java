package com.momolela.adapterpattern;

public class Mp3Player implements MediaPlayer {
    @Override
    public void oldPlay() {
        System.out.println("old mp3 play");
    }

    @Override
    public void newPlay(String mediaType) {
        if ("mp3".equals(mediaType)) {
            oldPlay();
        } else {
            AdvancedMediaPlayer advancedMediaPlayer = null;
            if ("mp4".equals(mediaType)) {
                advancedMediaPlayer = new Mp4Player();
            } else if ("vlc".equals(mediaType)) {
                advancedMediaPlayer = new VlcPlayer();
            }
            MediaAdapter mediaAdapter = new MediaAdapter(advancedMediaPlayer);
            mediaAdapter.newPlay(mediaType);
        }
    }
}
