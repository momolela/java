package com.momolela.adapterpattern;

public class Mp3Player implements MediaPlayer {

    @Override
    public void play(String mediaType) {
        if ("mp3".equals(mediaType)) {
            System.out.println("old mp3 play");
        } else if ("mp4".equals(mediaType) || "vlc".equals(mediaType)) {
            MediaAdapter mediaAdapter = new MediaAdapter(mediaType);
            mediaAdapter.play(mediaType);
        } else {
            System.out.println("Invalid media. " + mediaType + " format not supported");
        }
    }
}
